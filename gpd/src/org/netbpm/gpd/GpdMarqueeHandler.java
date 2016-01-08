package org.netbpm.gpd;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import org.jgraph.JGraph;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.CellView;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphUndoManager;
import org.jgraph.graph.ParentMap;
import org.jgraph.graph.Port;
import org.jgraph.graph.PortView;
import org.netbpm.gpd.cell.DefaultGpdCell;
import org.netbpm.gpd.cell.Transition;


public class GpdMarqueeHandler extends BasicMarqueeHandler {
	
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
	// Custom MarqueeHandler

	// MarqueeHandler that Connects Vertices and Displays PopupMenus

	// Holds the Start and the Current Point
	protected Point start, current;
	
	// Holds the First and the Current Port
	protected PortView port, firstPort;

	protected JGraph graph;
	public GpdMarqueeHandler(JGraph graph) {
		this.graph = graph;
	}
	
	// Override to Gain Control (for PopupMenu and ConnectMode)
	public boolean isForceMarqueeEvent(MouseEvent e) {
		// If Right Mouse Button we want to Display the PopupMenu
		if (SwingUtilities.isRightMouseButton(e))
			// Return Immediately
			return true;
		// Find and Remember Port
		port = getSourcePortAt(e.getPoint());
		// If Port Found and in ConnectMode (=Ports Visible)
		if (port != null && graph.isPortsVisible())
			return true;
		// Else Call Superclass
		return super.isForceMarqueeEvent(e);
	}

	// Display PopupMenu or Remember Start Location and First Port
	public void mousePressed(final MouseEvent e) {
		if (port != null && !e.isConsumed() && graph.isPortsVisible()) {
			// Remember Start Location
			start = graph.toScreen(port.getLocation(null));
			// Remember First Port
			firstPort = port;
			// Consume Event
			e.consume();
		} else
			// Call Superclass
			super.mousePressed(e);
	}

	// Find Port under Mouse and Repaint Connector
	public void mouseDragged(MouseEvent e) {
		// If remembered Start Point is Valid
		if (start != null && !e.isConsumed()) {
			// Fetch Graphics from Graph
			Graphics g = graph.getGraphics();
			// Xor-Paint the old Connector (Hide old Connector)
			paintConnector(Color.black, graph.getBackground(), g);
			// Reset Remembered Port
			port = getTargetPortAt(e.getPoint());
			// If Port was found then Point to Port Location
			if (port != null)
				current = graph.toScreen(port.getLocation(null));
			// Else If no Port was found then Point to Mouse Location
			else
				current = graph.snap(e.getPoint());
			// Xor-Paint the new Connector
			paintConnector(graph.getBackground(), Color.black, g);
			// Consume Event
			e.consume();
		}
		// Call Superclass
		super.mouseDragged(e);
	}

	public PortView getSourcePortAt(Point point) {
		// Scale from Screen to Model
		Point tmp = graph.fromScreen(new Point(point));
		// Find a Port View in Model Coordinates and Remember
		return graph.getPortViewAt(tmp.x, tmp.y);
	}

	// Find a Cell at point and Return its first Port as a PortView
	protected PortView getTargetPortAt(Point point) {
		// Find Cell at point (No scaling needed here)
		Object cell = graph.getFirstCellForLocation(point.x, point.y);
		// Loop Children to find PortView
		for (int i = 0; i < graph.getModel().getChildCount(cell); i++) {
			// Get Child from Model
			Object tmp = graph.getModel().getChild(cell, i);
			// Get View for Child using the Graph's View as a Cell Mapper
			tmp = graph.getGraphLayoutCache().getMapping(tmp, false);
			// If Child View is a Port View and not equal to First Port
			if (tmp instanceof PortView && tmp != firstPort)
				// Return as PortView
				return (PortView) tmp;
		}
		// No Port View found
		return getSourcePortAt(point);
	}

	// Connect the First Port and the Current Port in the Graph or Repaint
	public void mouseReleased(MouseEvent e) {
		// If Valid Event, Current and First Port
		if (e != null
			&& !e.isConsumed()
			&& port != null
			&& firstPort != null
			&& firstPort != port) {
			// Then Establish Connection
			connect((Port) firstPort.getCell(), (Port) port.getCell());
			// Consume Event
			e.consume();
			// Else Repaint the Graph
		} else
			graph.repaint();
		// Reset Global Vars
		firstPort = port = null;
		start = current = null;
		// Call Superclass
		super.mouseReleased(e);
	}

	// Show Special Cursor if Over Port
	public void mouseMoved(MouseEvent e) {
		// Check Mode and Find Port
		if (e != null
			&& getSourcePortAt(e.getPoint()) != null
			&& !e.isConsumed()
			&& graph.isPortsVisible()) {
			// Set Cusor on Graph (Automatically Reset)
			graph.setCursor(new Cursor(Cursor.HAND_CURSOR));
			// Consume Event
			e.consume();
		}
		// Call Superclass
		super.mouseReleased(e);
	}

	// Use Xor-Mode on Graphics to Paint Connector
	protected void paintConnector(Color fg, Color bg, Graphics g) {
		// Set Foreground
		g.setColor(fg);
		// Set Xor-Mode Color
		g.setXORMode(bg);
		// Highlight the Current Port
		paintPort(graph.getGraphics());
		// If Valid First Port, Start and Current Point
		if (firstPort != null && start != null && current != null)
			// Then Draw A Line From Start to Current Point
			g.drawLine(start.x, start.y, current.x, current.y);
	}

	// Use the Preview Flag to Draw a Highlighted Port
	protected void paintPort(Graphics g) {
		// If Current Port is Valid
		if (port != null) {
			// If Not Floating Port...
			boolean o =
				(GraphConstants.getOffset(port.getAttributes()) != null);
			// ...Then use Parent's Bounds
			Rectangle r =
				(o) ? port.getBounds() : port.getParentView().getBounds();
			// Scale from Model to Screen
			r = graph.toScreen(new Rectangle(r));
			// Add Space For the Highlight Border
			r.setBounds(r.x - 3, r.y - 3, r.width + 6, r.height + 6);
			// Paint Port in Preview (=Highlight) Mode
			graph.getUI().paintCell(g, port, r, true);
		}
	}
	

	// Insert a new Edge between source and target
	public void connect(Port source, Port target) {
		// TODO Validate source and target 
		// Connections that will be inserted into the Model
		//check if transition exist
		DefaultGpdCell sourceCell = (DefaultGpdCell)((DefaultPort)source).getParent();
		DefaultGpdCell targetCell= (DefaultGpdCell)((DefaultPort)target).getParent();
		if (findConnection(sourceCell,targetCell)==null){
			ConnectionSet cs = new ConnectionSet();
			// Construct Edge with no label
			DefaultEdge edge = new Transition();
			// Create Connection between source and target using edge
			cs.connect(edge, source, target);
			// Create a Map thath holds the attributes for the edge
			Map map = GraphConstants.createMap();
			// Add a Line End Attribute
			GraphConstants.setLineEnd(map, GraphConstants.ARROW_SIMPLE);
			// Construct a Map from cells to Maps (for insert)
			Hashtable attributes = new Hashtable();
			// Associate the Edge with its Attributes
			attributes.put(edge, map);
			// Insert the Edge and its Attributes
			graph.getGraphLayoutCache().insert(
				new Object[] { edge },
				attributes,
				cs,
				null,
				null);
		}
	}

	/**
	 * @param sourceList
	 * @param targetList
	 */
	public DefaultEdge findConnection(DefaultGpdCell source, DefaultGpdCell target) {
		List sourceList = findTransitionBySource(source);
		List targetList = findTransitionByTarget(target);
		Iterator sourceIt = sourceList.iterator();
		while (sourceIt.hasNext()){
			Object sourceTransition = sourceIt.next();
			Iterator targetIt = targetList.iterator();
			while (targetIt.hasNext()){
				Object targetTranistion = targetIt.next();
				if (targetTranistion.equals(sourceTransition)){
					return (DefaultEdge)targetTranistion;
				}
			}

		}
		return null;		
	}

	/**
	 * Return a list of all transition that has the same source
	 * @param model
	 * @param startCell
	 * @return
	 */
	public List findTransitionBySource(DefaultGpdCell sourceCell) {
		List result=new LinkedList();
		int max =graph.getModel().getRootCount();
		for (int i = 0; i < max; i++) {
			Object cell = graph.getModel().getRootAt(i);
			if (cell instanceof Transition){
				Transition transitionCell = (Transition)cell;
				if (transitionCell.getSource()==sourceCell.getFirstChild()){
					result.add(transitionCell);
				}
			} 
		}

		return result;
	}

	/**
	 * Return a list of all transition that has the same target
	 * @param model
	 * @param startCell
	 * @return
	 */
	public List findTransitionByTarget(DefaultGpdCell sourceCell) {
		List result=new LinkedList();
		int max =graph.getModel().getRootCount();
		for (int i = 0; i < max; i++) {
			Object cell = graph.getModel().getRootAt(i);
			if (cell instanceof Transition){
				Transition transitionCell = (Transition)cell;
				if (transitionCell.getTarget()==sourceCell.getFirstChild()){
					result.add(transitionCell);
				}
			} 
		}

		return result;
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
	protected int getCellCount(JGraph graph) {
		Object[] cells = graph.getDescendants(graph.getRoots());
		return cells.length;
	}
	
	// Insert a new Vertex at point
	public void insert(Point point, DefaultGraphCell vertex) {
		// Construct Vertex with no Label
		//DefaultGraphCell vertex = new DefaultGraphCell();
		// Add one Floating Port
		vertex.add(new DefaultPort());
		// Snap the Point to the Grid
		point = graph.snap(new Point(point));
		// Default Size for the new Vertex
		Dimension size = new Dimension(25, 25);
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
	// Update Undo/Redo Button State based on Undo Manager
	protected void updateHistoryButtons() {
		// The View Argument Defines the Context
		undo.setEnabled(undoManager.canUndo(graph.getGraphLayoutCache()));
		redo.setEnabled(undoManager.canRedo(graph.getGraphLayoutCache()));
	}

}

