package org.netbpm.gpd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.TransferHandler;
import javax.swing.event.UndoableEditEvent;

import org.jgraph.JGraph;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphUndoManager;
import org.jgraph.graph.ParentMap;
import org.jgraph.graph.Port;
import org.netbpm.gpd.action.ExportNetBPMAction;
import org.netbpm.gpd.action.ExportNetBPMFile;
import org.netbpm.gpd.action.ExportNetBPMWebAction;
import org.netbpm.gpd.action.ExportJpegAction;
import org.netbpm.gpd.action.FormatFillColor;
import org.netbpm.gpd.action.ImportNetBPMAction;
import org.netbpm.gpd.action.ImportNetBPMWebAction;
import org.netbpm.gpd.action.NewAction;
import org.netbpm.gpd.action.OpenAction;
import org.netbpm.gpd.action.PageSetupAction;
import org.netbpm.gpd.action.PrintAction;
import org.netbpm.gpd.action.PrintPreviewAction;
import org.netbpm.gpd.action.SaveAction;
import org.netbpm.gpd.action.TiltAction;
import org.netbpm.gpd.cell.ActivityCell;
import org.netbpm.gpd.cell.DecisionCell;
import org.netbpm.gpd.cell.EndCell;
import org.netbpm.gpd.cell.ForkCell;
import org.netbpm.gpd.cell.JoinCell;
import org.netbpm.gpd.cell.StartCell;
import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.controller.MasterDetailCellController;
import org.netbpm.gpd.dialog.util.GpdSplitPane;

public class ProcessDesigner
	extends JPanel
	implements GraphSelectionListener, KeyListener {
	//const
	public static final String ACTIVITY_CELL="ActivityCell";
	public static final String DECISION_CELL="DecisionCell";
	public static final String END_CELL="EndCell";
	public static final String FORK_CELL="ForkCell";
	public static final String JOIN_CELL="JoinCell";
	public static final String START_CELL="StartCell";

	// JGraph instance
	protected GpdGraph graph;
	private JScrollPane mainPane = null;
	private JSplitPane librarySplit = null;
	private JPanel		propertyPanel= new JPanel();
	private Controller propertyController=null;
	private ButtonGroup buttongroup = new ButtonGroup();


	// Undo Manager
	protected GraphUndoManager undoManager;

	// Actions which Change State
	protected Action undo,
		redo,
		remove,
		group,
		ungroup,
		tofront,
		toback,
		cut,
		copy,
		paste;

	//
	// Main
	//

	// Main Method
	public static void main(String[] args) {
		String vers = System.getProperty("java.version");
		double version = Double.parseDouble(vers.substring(0,3));
		if (version < 1.4) {
			System.out.println(
				"WARNING: Gpd must be run with a "
					+ "1.4 or higher version VM. you are using "+vers);
			return ;
		}
		Configuration.getInstance();

		// Construct Frame
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		JFrame frame = new JFrame("Business Process Designer");

		// Set Close Operation to Exit
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ProcessDesigner pDesigner=new ProcessDesigner();
		frame.setJMenuBar(pDesigner.createMenuBar());
		frame.getContentPane().add(pDesigner);
		// Fetch URL to Icon Resource
		URL jgraphUrl =
			ProcessDesigner.class.getClassLoader().getResource("gif/jgraph.gif");
		// If Valid URL
		if (jgraphUrl != null) {
			// Load Icon
			ImageIcon jgraphIcon = new ImageIcon(jgraphUrl);
			// Use in Window
			frame.setIconImage(jgraphIcon.getImage());
		}
		// Set Default Size
		frame.pack();
		frame.setSize(720, 480);
		// Show Frame
		frame.show();
	}

	//
	// ProcessDesigner Panel
	//

	// Construct an ProcessDesigner Panel
	public ProcessDesigner() {
		//init ExceptionHandler
		ExceptionHandler.getInstance().init(this);
		// Use Border Layout
		setLayout(new BorderLayout());
		// Construct the Graph
		graph = new GpdGraph(new GpdGraphModel());

		graph.setScale(2 * graph.getScale());
		// Create a GraphUndoManager which also Updates the ToolBar
		undoManager = new GraphUndoManager() {
				// Override Superclass
				public void undoableEditHappened(UndoableEditEvent e) {
					// First Invoke Superclass
				super.undoableEditHappened(e);
				// Then Update Undo/Redo Buttons
				updateHistoryButtons();
			}
		};

		// Add Listeners to Graph
		//
		// Register UndoManager with the Model
		graph.getModel().addUndoableEditListener(undoManager);
		// Update ToolBar based on Selection Changes
		graph.getSelectionModel().addGraphSelectionListener(this);
		// Listen for Delete Keystroke when the Graph has Focus
		graph.addKeyListener(this);
		// Construct Panel
		//
		// Add a ToolBar
		add(createToolBar(), BorderLayout.NORTH);
		// Add the Graph as Center Component

		mainPane = new JScrollPane(graph);
		JPanel overviewPane = GpdOverviewPanel.createOverviewPanel(graph, this);
		overviewPane.setMinimumSize(new Dimension(100,100));
		librarySplit =
			new GpdSplitPane(
				JSplitPane.VERTICAL_SPLIT,
				overviewPane,
				propertyPanel);
		librarySplit.setName("DocumentLibrary");

		GpdSplitPane splitPane =
			new GpdSplitPane(
				GpdSplitPane.HORIZONTAL_SPLIT,
				librarySplit,
				mainPane);
		splitPane.setName("DocumentMain");
		splitPane.setOneTouchExpandable(true);
		add(splitPane, BorderLayout.CENTER);

		graph.addMouseListener(new MouseAdapter() {
			private Object lastCell=null;
			public void mousePressed(MouseEvent e) {
					//		Get Cell under Mousepointer
					int x = e.getX(), y = e.getY();
					String command=buttongroup.getSelection().getActionCommand();
					if (command==null){
						Object cell = graph.getFirstCellForLocation(x, y);
						//		Print Cell Label
						if (cell != null && cell!=lastCell) {
							if (propertyController==null){
								propertyController = new MasterDetailCellController();
							}
							propertyController.setModel(cell);
							librarySplit.setRightComponent(propertyController.getView());
						
							lastCell=cell;
						}
					} else {
						x=new Double(x/graph.getScale()).intValue();
						y=new Double(y/graph.getScale()).intValue();
						if (command.equals(ACTIVITY_CELL)){
							insert(new Point(x, y), new ActivityCell(),new Dimension(100,25));
						} else if (command.equals(DECISION_CELL)){
							insert(new Point(x, y), new DecisionCell());
						} else if (command.equals(END_CELL)){
							insert(new Point(x, y), new EndCell());
						} else if (command.equals(FORK_CELL)){
							insert(new Point(x, y), new ForkCell());
						} else if (command.equals(JOIN_CELL)){
							insert(new Point(x, y), new JoinCell());
						} else if (command.equals(START_CELL)){
							insert(new Point(x, y), new StartCell());
						}
					}
			}
		});
	}

	public void insert(Point point, DefaultGraphCell vertex) {
		insert(point,vertex,null);
	}
	// Insert a new Vertex at point
	public void insert(Point point, DefaultGraphCell vertex,Dimension size) {
		// Construct Vertex with no Label
		//DefaultGraphCell vertex = new DefaultGraphCell();
		// Add one Floating Port
		vertex.add(new DefaultPort());
		// Snap the Point to the Grid
		point = graph.snap(new Point(point));
		// Default Size for the new Vertex
		if (size==null){
			size = new Dimension(25, 25);
		}
		// Create a Map that holds the attributes for the Vertex
		Map map = GraphConstants.createMap();
		// Add a Bounds Attribute to the Map
		GraphConstants.setBounds(map, new Rectangle(point, size));
		// Add a Border Color Attribute to the Map
		GraphConstants.setBorderColor(map, Color.black);
		// Add a White Background
		GraphConstants.setBackground(map, Color.white);
		// Make Vertex Opaque
		GraphConstants.setOpaque(map, true);
		// Construct a Map from cells to Maps (for insert)
		Hashtable attributes = new Hashtable();
		// Associate the Vertex with its Attributes
		attributes.put(vertex, map);
		// Insert the Vertex and its Attributes (can also use model)
		graph.getGraphLayoutCache().insert(
			new Object[] { vertex },
			attributes,
			null,
			null,
			null);
	}
	// Create a Group that Contains the Cells
	public void group(Object[] cells) {
		// Order Cells by View Layering
		cells = graph.getGraphLayoutCache().order(cells);
		// If Any Cells in View
		if (cells != null && cells.length > 0) {
			// Create Group Cell
			int count = getCellCount(graph);
			DefaultGraphCell group =
				new DefaultGraphCell(new Integer(count - 1));
			// Create Change Information
			ParentMap map = new ParentMap();
			// Insert Child Parent Entries
			for (int i = 0; i < cells.length; i++)
				map.addEntry(cells[i], group);
			// Insert into model
			graph.getGraphLayoutCache().insert(
				new Object[] { group },
				null,
				null,
				map,
				null);
		}
	}

	// Returns the total number of cells in a graph
	protected int getCellCount(JGraph graph) {
		Object[] cells = graph.getDescendants(graph.getRoots());
		return cells.length;
	}

	// Ungroup the Groups in Cells and Select the Children
	public void ungroup(Object[] cells) {
		// If any Cells
		if (cells != null && cells.length > 0) {
			// List that Holds the Groups
			ArrayList groups = new ArrayList();
			// List that Holds the Children
			ArrayList children = new ArrayList();
			// Loop Cells
			for (int i = 0; i < cells.length; i++) {
				// If Cell is a Group
				if (isGroup(cells[i])) {
					// Add to List of Groups
					groups.add(cells[i]);
					// Loop Children of Cell
					for (int j = 0;
						j < graph.getModel().getChildCount(cells[i]);
						j++) {
						// Get Child from Model
						Object child = graph.getModel().getChild(cells[i], j);
						// If Not Port
						if (!(child instanceof Port))
							// Add to Children List
							children.add(child);
					}
				}
			}
			// Remove Groups from Model (Without Children)
			graph.getGraphLayoutCache().remove(groups.toArray());
			// Select Children
			graph.setSelectionCells(children.toArray());
		}
	}

	// Determines if a Cell is a Group
	public boolean isGroup(Object cell) {
		// Map the Cell to its View
		CellView view = graph.getGraphLayoutCache().getMapping(cell, false);
		if (view != null)
			return !view.isLeaf();
		return false;
	}

	// Brings the Specified Cells to Front
	public void toFront(Object[] c) {
		graph.getGraphLayoutCache().toFront(c);
	}

	// Sends the Specified Cells to Back
	public void toBack(Object[] c) {
		graph.getGraphLayoutCache().toBack(c);
	}

	// Undo the last Change to the Model or the View
	public void undo() {
		try {
			undoManager.undo(graph.getGraphLayoutCache());
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			updateHistoryButtons();
		}
	}

	// Redo the last Change to the Model or the View
	public void redo() {
		try {
			undoManager.redo(graph.getGraphLayoutCache());
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			updateHistoryButtons();
		}
	}

	// Update Undo/Redo Button State based on Undo Manager
	protected void updateHistoryButtons() {
		// The View Argument Defines the Context
		undo.setEnabled(undoManager.canUndo(graph.getGraphLayoutCache()));
		redo.setEnabled(undoManager.canRedo(graph.getGraphLayoutCache()));
	}

	//
	// Listeners
	//

	// From GraphSelectionListener Interface
	public void valueChanged(GraphSelectionEvent e) {
		// Group Button only Enabled if more than One Cell Selected
		group.setEnabled(graph.getSelectionCount() > 1);
		// Update Button States based on Current Selection
		boolean enabled = !graph.isSelectionEmpty();
		remove.setEnabled(enabled);
		ungroup.setEnabled(enabled);
		tofront.setEnabled(enabled);
		toback.setEnabled(enabled);
		copy.setEnabled(enabled);
		cut.setEnabled(enabled);
	}

	//
	// KeyListener for Delete KeyStroke
	//
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		// Listen for Delete Key Press
		if (e.getKeyCode() == KeyEvent.VK_DELETE)
			// Execute Remove Action on Delete Key Press
			remove.actionPerformed(null);
	}

	public JMenuBar createMenuBar(){
		JMenuBar menuBar=new JMenuBar();
		JMenu filemenu=new JMenu("File");
		filemenu.add(new OpenAction(graph));
		filemenu.add(new SaveAction(graph));
		filemenu.add(new NewAction(graph));
		filemenu.addSeparator();
		filemenu.add(new PrintAction(graph));
		filemenu.add(new PrintPreviewAction(graph));
		filemenu.add(new PageSetupAction(graph));
		filemenu.addSeparator();
		JMenu export=new JMenu("export");
		export.add(new ExportNetBPMFile(graph));
                export.addSeparator();
                export.add(new ExportNetBPMAction(graph));
		export.add(new ExportNetBPMWebAction(graph));
                export.add(new ExportJpegAction(graph));
		filemenu.add(export);
		JMenu importMenu=new JMenu("import");
		importMenu.add(new ImportNetBPMAction(graph));
		importMenu.add(new ImportNetBPMWebAction(graph));
		filemenu.add(importMenu);
		menuBar.add(filemenu);
                JMenu formatmenu=new JMenu("Format");
                formatmenu.add(new FormatFillColor(graph));
		menuBar.add(formatmenu);
		return menuBar;
	}
	//
	// ToolBar
	//
	public JToolBar createToolBar() {

		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);

		URL startUrl = getClass().getClassLoader().getResource("gif/start.gif");
		ImageIcon startIcon = new ImageIcon(startUrl);
		JToggleButton startButton=new JToggleButton(startIcon);
		startButton.setActionCommand(START_CELL);

		URL stopUrl = getClass().getClassLoader().getResource("gif/stop.gif");
		ImageIcon stopIcon = new ImageIcon(stopUrl);
		JToggleButton stopButton=new JToggleButton(stopIcon);
		stopButton.setActionCommand(END_CELL);

		URL forkUrl = getClass().getClassLoader().getResource("gif/fork.gif");
		ImageIcon forkIcon = new ImageIcon(forkUrl);
		JToggleButton forkButton=new JToggleButton(forkIcon);
		forkButton.setActionCommand(FORK_CELL);

		URL joinUrl = getClass().getClassLoader().getResource("gif/join.gif");
		ImageIcon joinIcon = new ImageIcon(joinUrl);
		JToggleButton joinButton=new JToggleButton(joinIcon);
		joinButton.setActionCommand(JOIN_CELL);

		URL activityUrl =
			getClass().getClassLoader().getResource("gif/activity.gif");
		ImageIcon activityIcon = new ImageIcon(activityUrl);
		JToggleButton activityButton=new JToggleButton(activityIcon);
		activityButton.setActionCommand(ACTIVITY_CELL);

		URL decisionUrl =
			getClass().getClassLoader().getResource("gif/decision.gif");
		ImageIcon decisionIcon = new ImageIcon(decisionUrl);
		JToggleButton decisionButton=new JToggleButton(decisionIcon);
		decisionButton.setActionCommand(DECISION_CELL);


		// Toggle Connect Mode
		URL connectUrl = getClass().getClassLoader().getResource("gif/connecton.gif");
		ImageIcon connectIcon = new ImageIcon(connectUrl);
		JToggleButton connectonButton=new JToggleButton(connectIcon);

		buttongroup.add(startButton);
		buttongroup.add(stopButton);
		buttongroup.add(forkButton);
		buttongroup.add(joinButton);
		buttongroup.add(activityButton);
		buttongroup.add(decisionButton);
		buttongroup.add(connectonButton);
		connectonButton.setSelected(true);

		toolbar.add(startButton);
		toolbar.add(stopButton);
		toolbar.add(forkButton);
		toolbar.add(joinButton);
		toolbar.add(activityButton);
		toolbar.add(decisionButton);
		toolbar.add(connectonButton);


		// Undo
		toolbar.addSeparator();
		URL undoUrl = getClass().getClassLoader().getResource("gif/undo.gif");
		ImageIcon undoIcon = new ImageIcon(undoUrl);
		undo = new AbstractAction("", undoIcon) {
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		};
		undo.setEnabled(false);
		toolbar.add(undo);

		// Redo
		URL redoUrl = getClass().getClassLoader().getResource("gif/redo.gif");
		ImageIcon redoIcon = new ImageIcon(redoUrl);
		redo = new AbstractAction("", redoIcon) {
			public void actionPerformed(ActionEvent e) {
				redo();
			}
		};
		redo.setEnabled(false);
		toolbar.add(redo);

		//
		// Edit Block
		//
		toolbar.addSeparator();
		Action action;
		URL url;

		// Copy
		action = TransferHandler.getCopyAction();
		url = getClass().getClassLoader().getResource("gif/copy.gif");
		action.putValue(Action.SMALL_ICON, new ImageIcon(url));
		toolbar.add(copy = new EventRedirector(action));

		// Paste
		action = TransferHandler.getPasteAction();
		url = getClass().getClassLoader().getResource("gif/paste.gif");
		action.putValue(Action.SMALL_ICON, new ImageIcon(url));
		toolbar.add(paste = new EventRedirector(action));

		// Cut
		action = TransferHandler.getCutAction();
		url = getClass().getClassLoader().getResource("gif/cut.gif");
		action.putValue(Action.SMALL_ICON, new ImageIcon(url));
		toolbar.add(cut = new EventRedirector(action));

		// Remove
		URL removeUrl =
			getClass().getClassLoader().getResource("gif/delete.gif");
		ImageIcon removeIcon = new ImageIcon(removeUrl);
		remove = new AbstractAction("", removeIcon) {
			public void actionPerformed(ActionEvent e) {
				if (!graph.isSelectionEmpty()) {
					Object[] cells = graph.getSelectionCells();
					cells = graph.getDescendants(cells);
					graph.getModel().remove(cells);
				}
			}
		};
		remove.setEnabled(false);
		toolbar.add(remove);

		// Zoom Std
		toolbar.addSeparator();
		URL zoomUrl = getClass().getClassLoader().getResource("gif/zoom.gif");
		ImageIcon zoomIcon = new ImageIcon(zoomUrl);
		toolbar.add(new AbstractAction("", zoomIcon) {
			public void actionPerformed(ActionEvent e) {
				graph.setScale(1);
			}
		});
		// Zoom In
		URL zoomInUrl =
			getClass().getClassLoader().getResource("gif/zoomin.gif");
		ImageIcon zoomInIcon = new ImageIcon(zoomInUrl);
		toolbar.add(new AbstractAction("", zoomInIcon) {
			public void actionPerformed(ActionEvent e) {
				graph.setScale(2 * graph.getScale());
			}
		});
		// Zoom Out
		URL zoomOutUrl =
			getClass().getClassLoader().getResource("gif/zoomout.gif");
		ImageIcon zoomOutIcon = new ImageIcon(zoomOutUrl);
		toolbar.add(new AbstractAction("", zoomOutIcon) {
			public void actionPerformed(ActionEvent e) {
				graph.setScale(graph.getScale() / 2);
			}
		});

		// Group
		toolbar.addSeparator();
		URL groupUrl = getClass().getClassLoader().getResource("gif/group.gif");
		ImageIcon groupIcon = new ImageIcon(groupUrl);
		group = new AbstractAction("", groupIcon) {
			public void actionPerformed(ActionEvent e) {
				group(graph.getSelectionCells());
			}
		};
		group.setEnabled(false);
		toolbar.add(group);

		// Ungroup
		URL ungroupUrl =
			getClass().getClassLoader().getResource("gif/ungroup.gif");
		ImageIcon ungroupIcon = new ImageIcon(ungroupUrl);
		ungroup = new AbstractAction("", ungroupIcon) {
			public void actionPerformed(ActionEvent e) {
				ungroup(graph.getSelectionCells());
			}
		};
		ungroup.setEnabled(false);
		toolbar.add(ungroup);

		// To Front
		toolbar.addSeparator();
		URL toFrontUrl =
			getClass().getClassLoader().getResource("gif/tofront.gif");
		ImageIcon toFrontIcon = new ImageIcon(toFrontUrl);
		tofront = new AbstractAction("", toFrontIcon) {
			public void actionPerformed(ActionEvent e) {
				if (!graph.isSelectionEmpty())
					toFront(graph.getSelectionCells());
			}
		};
		tofront.setEnabled(false);
		toolbar.add(tofront);

		// To Back
		URL toBackUrl =
			getClass().getClassLoader().getResource("gif/toback.gif");
		ImageIcon toBackIcon = new ImageIcon(toBackUrl);
		toback = new AbstractAction("", toBackIcon) {
			public void actionPerformed(ActionEvent e) {
				if (!graph.isSelectionEmpty())
					toBack(graph.getSelectionCells());
			}
		};
		toback.setEnabled(false);
		toolbar.add(toback);

		toolbar.addSeparator();
		toolbar.add(new TiltAction(graph));
		return toolbar;
	}

	//	This will change the source of the actionevent to graph.
	protected class EventRedirector extends AbstractAction {

		protected Action action;

		// Construct the "Wrapper" Action
		public EventRedirector(Action a) {
			super("", (ImageIcon) a.getValue(Action.SMALL_ICON));
			this.action = a;
		}

		// Redirect the Actionevent
		public void actionPerformed(ActionEvent e) {
			e =
				new ActionEvent(
					graph,
					e.getID(),
					e.getActionCommand(),
					e.getModifiers());
			action.actionPerformed(e);
		}
	}
	/**
	 * 
	 */
	public JScrollPane getScrollPane() {
		return mainPane;
	}
}
