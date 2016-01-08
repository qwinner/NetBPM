package org.netbpm.gpd.renderer;


import org.jgraph.JGraph;

import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphConstants;
import org.netbpm.gpd.cell.ActivityCell;
import org.netbpm.gpd.model.FieldVO;
import org.netbpm.gpd.view.ActivityView;

import java.awt.*;

import java.util.Iterator;
import java.util.Map;

import javax.swing.*;


/**
 * A JGraph Vertex renderer for Tables
 *
 * @author Brian Sidharta
 * @version $Revision: 1.1 $
 */
public class ActivityRenderer extends JPanel implements CellViewRenderer {
    private ActivityCell activityCell;
	/** The default foreground color = Color.black */
    public static final Color DEFAULT_FOREGROUND = Color.black;
    public static final Color DEFAULT_BACKGROUND = Color.white;
    
    private Color backColor=DEFAULT_BACKGROUND;

	//gif const
	private static final String NOT_ACCESSIBLE_FILENAME = "gif/field/notaccessible.gif";
	private final Icon NOT_ACCESSIBLE = new ImageIcon
	( getClass().getClassLoader().getResource( NOT_ACCESSIBLE_FILENAME ));

	private static final String READ_ONLY_FILENAME = "gif/field/readonly.gif";
	private final Icon READ_ONLY = new ImageIcon
	( getClass().getClassLoader().getResource( READ_ONLY_FILENAME ));

	private static final String WRITE_ONLY_FILENAME = "gif/field/writeonly.gif";
	private final Icon WRITE_ONLY = new ImageIcon
	( getClass().getClassLoader().getResource( WRITE_ONLY_FILENAME ));

	private static final String WRITE_ONLY_REQUIRED_FILENAME = "gif/field/writeonlyrequired.gif";
	private final Icon WRITE_ONLY_REQUIRED = new ImageIcon
	( getClass().getClassLoader().getResource( WRITE_ONLY_REQUIRED_FILENAME ));

	private static final String READ_WRITE_FILENAME = "gif/field/readwrite.gif";
	private final Icon READ_WRITE = new ImageIcon
	( getClass().getClassLoader().getResource( READ_WRITE_FILENAME ));

	private static final String READ_WRITE_REQUIRED_FILENAME = "gif/field/readwriterequired.gif";
	private final Icon READ_WRITE_REQUIRED = new ImageIcon
	( getClass().getClassLoader().getResource( READ_WRITE_REQUIRED_FILENAME ));

    /** Vertical padding around title */
    private static final int TITLE_PADDING = 3;

    /** Left margin for column icon */
	private static final int COLUMN_ICON_LEFT = 2;

    /** Left margin for column name text */
	private final int COLUMN_LEFT = NOT_ACCESSIBLE.getIconWidth() + 2 + COLUMN_ICON_LEFT;

    /** The padding between text rows in pixels */
	private static final int ROW_SPACING = 3;

    /** The padding around the more-content indicator arrow */
	private static final int MORE_ARROW_PADDING = 4;

    /** The width of the more-content indicator arrow */
	private static final int MORE_ARROW_WIDTH = 8;

    /** The height of the more-content indicator arrow */
	private static final int MORE_ARROW_HEIGHT = 8;

    /** Cache the current graph for drawing. */
    transient private JGraph graph;

    /** Cached hasFocus and selected value. */
    transient private boolean hasFocus;

    /** Cached hasFocus and selected value. */
    transient private boolean selected;

    /** Cached hasFocus and selected value. */
//    transient private boolean preview;

    /** Cached default foreground and default background. */
    transient private Color defaultForeground;

    /** Cached default foreground and default background. */
    transient private Color defaultBackground;

    /** Cached default foreground and default background. */
    transient private Color bordercolor;

    /** Cached borderwidth. */
    transient private int borderWidth;

    /** Default constructor */
    public ActivityRenderer() {
        defaultForeground = DEFAULT_FOREGROUND;
        defaultBackground = DEFAULT_BACKGROUND;
    }

    /**
     * Configure and return the renderer based on the passed in
     * components. The value is typically set from messaging the
     * graph with <code>convertValueToString</code>.
     * We recommend you check the value's class and throw an
     * illegal argument exception if it's not correct.
     *
     * @param   graph the graph that that defines the rendering context.
     * @param   value the object that should be rendered.
     * @param   selected whether the object is selected.
     * @param   hasFocus whether the object has the focus.
     * @param   isPreview whether we are drawing a preview.
     * @return        the component used to render the value.
     */
    public Component getRendererComponent(JGraph graph, CellView cellview,
        boolean sel, boolean focus, boolean preview) {
        if (cellview instanceof ActivityView) {
            ActivityView view = (ActivityView) cellview;

            activityCell = (ActivityCell) view.getCell();

            this.graph = graph;
            this.hasFocus = focus;
            this.selected = sel;
//            this.preview = preview;

            setComponentOrientation(graph.getComponentOrientation());

            if (view.isLeaf()) {
                installAttributes(view);
            } else {
                setBorder(null);
                setOpaque(false);
            }

            return this;
        }

        return null;
    }

    /**
     * Install the attributes of specified cell in this
     * renderer instance. This means, retrieve every published
     * key from the cells hashtable and set global variables
     * or superclass properties accordingly.
     *
     * @param   cell to retrieve the attribute values from.
     */
    protected void installAttributes(CellView view) {
        Map map = view.getAttributes();
        setOpaque(GraphConstants.isOpaque(map));
        setBorder(GraphConstants.getBorder(map));
        bordercolor = GraphConstants.getBorderColor(map);
        borderWidth = Math.max(1, Math.round(GraphConstants.getLineWidth(map)));

        if ((getBorder() == null) && (bordercolor != null)) {
            setBorder(BorderFactory.createLineBorder(bordercolor, borderWidth));
        }

        Color foreground = GraphConstants.getForeground(map);
        setForeground((foreground != null) ? foreground : defaultForeground);

        Color background = GraphConstants.getBackground(map);
        setBackground((background != null) ? background : defaultBackground);
        setFont(GraphConstants.getFont(map));
    }

    /**
     * Paint the renderer. Overrides superclass paint
     * to add specific painting.
     */
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        Dimension dim = getSize();
        int width = dim.width;
        int height = dim.height;

        g.setColor(getBackColor());
        g.fillRoundRect(borderWidth-1, borderWidth-1, dim.width, dim.height, 10, 10);
        g.setColor(getForeground());
        
        FontMetrics fm = g.getFontMetrics();
        Font normalFont = g.getFont();
        Font titleFont = normalFont.deriveFont(Font.BOLD);
        int colTextHeight = fm.getMaxAscent() - fm.getMaxDescent() +
            ROW_SPACING;
        int yCursor = colTextHeight + TITLE_PADDING;

        String title = activityCell.getModel().getName();
        int xCursor = (width - fm.stringWidth(title)) / 2;

        Stroke lineStroke = new BasicStroke((float) borderWidth);
        Stroke textStroke = new BasicStroke(1.0f);

        // Draw the title box with name centered
        g2.setStroke(textStroke);
        g.setFont(titleFont);
        g.drawString(title, xCursor, yCursor);

        g2.setStroke(lineStroke);
        yCursor += (TITLE_PADDING + borderWidth);
        g.drawLine(0, yCursor, width, yCursor);

        // Draw the columns
//        g.setColor(getForeground());
        g2.setStroke(textStroke);
        g.setFont(normalFont);

		Iterator it=activityCell.getModel().getFieldList().iterator();
		while (it.hasNext()){
			FieldVO field = (FieldVO)it.next();
            String col = field.getAttribute();
            yCursor += (ROW_SPACING + colTextHeight);

				
			if (field.getFormatter()==null){
				g.setColor(Color.RED);
			} else {
				g.setColor(getForeground());
			}
            g.drawString(col, COLUMN_LEFT, yCursor);
			int keyIconYOffset = -(NOT_ACCESSIBLE.getIconHeight() + fm.getMaxAscent() -
								   fm.getMaxDescent()) / 2;

			if (field.getAccess().equals(FieldVO.NOT_ACCESSIBLE)){
				NOT_ACCESSIBLE.paintIcon( this, g, COLUMN_ICON_LEFT, yCursor + keyIconYOffset );
			} else if (field.getAccess().equals(FieldVO.READ_ONLY)){
				READ_ONLY.paintIcon( this, g, COLUMN_ICON_LEFT, yCursor + keyIconYOffset );
			} else if (field.getAccess().equals(FieldVO.WRITE_ONLY)){
				WRITE_ONLY.paintIcon( this, g, COLUMN_ICON_LEFT, yCursor + keyIconYOffset );
			} else if (field.getAccess().equals(FieldVO.WRITE_ONLY_REQUIRED)){
				WRITE_ONLY_REQUIRED.paintIcon( this, g, COLUMN_ICON_LEFT, yCursor + keyIconYOffset );
			} else if (field.getAccess().equals(FieldVO.READ_WRITE)){
				READ_WRITE.paintIcon( this, g, COLUMN_ICON_LEFT, yCursor + keyIconYOffset );
			} else if (field.getAccess().equals(FieldVO.READ_WRITE_REQUIRED)){
				READ_WRITE_REQUIRED.paintIcon( this, g, COLUMN_ICON_LEFT, yCursor + keyIconYOffset );
			}
			
			g.setColor(getForeground());
            // If we're below the the bottom of the box
            if (yCursor > height) {
                // Draw the more-content arrow
                int right = width - MORE_ARROW_PADDING;
                int left = right - MORE_ARROW_WIDTH;
                int center = right - (MORE_ARROW_WIDTH / 2);
                int bottom = height - MORE_ARROW_PADDING;
                int top = bottom - MORE_ARROW_HEIGHT;
                int[] xPts = { center, left, right };
                int[] yPts = { bottom, top, top };
                g.fillPolygon(xPts, yPts, 3);

                break;
            }
        }

        if (selected || hasFocus) {
            // Draw selection/highlight border
            ((Graphics2D) g).setStroke(GraphConstants.SELECTION_STROKE);

            if (hasFocus) {
                g.setColor(graph.getGridColor());
            } else if (selected) {
                g.setColor(graph.getHighlightColor());
            }

            Dimension d = getSize();
            g.drawRect(0, 0, d.width - 1, d.height - 1);
        }

            g2.setStroke(new BasicStroke(borderWidth));
            g.drawRoundRect(borderWidth - 1, borderWidth - 1, dim.width, dim.height, 10, 10);

    }

    /**
     * Returns <code>true</code> if <code>key</code> is a supported
     * attribute in the renderer. Supported attributes affect the
     * visual appearance of the renderer.
     *
     * @param   key the key that defines the attribute to be checked.
     * @return        true if <code>key</code> is supported by this renderer.
     */
    public boolean supportsAttribute(Object key) {
        return key.equals(GraphConstants.BOUNDS) ||
        key.equals(GraphConstants.BACKGROUND) ||
        key.equals(GraphConstants.BORDER) ||
        key.equals(GraphConstants.FOREGROUND) ||
        key.equals(GraphConstants.BORDERCOLOR) ||
        key.equals(GraphConstants.LINEWIDTH) ||
        key.equals(GraphConstants.OPAQUE);
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void validate() {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void revalidate() {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void repaint(long tm, int x, int y, int width, int height) {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void repaint(Rectangle r) {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    protected void firePropertyChange(String propertyName, Object oldValue,
        Object newValue) {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void firePropertyChange(String propertyName, byte oldValue,
        byte newValue) {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void firePropertyChange(String propertyName, char oldValue,
        char newValue) {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void firePropertyChange(String propertyName, short oldValue,
        short newValue) {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void firePropertyChange(String propertyName, int oldValue,
        int newValue) {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void firePropertyChange(String propertyName, long oldValue,
        long newValue) {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void firePropertyChange(String propertyName, float oldValue,
        float newValue) {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void firePropertyChange(String propertyName, double oldValue,
        double newValue) {
    }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void firePropertyChange(String propertyName, boolean oldValue,
        boolean newValue) {
    }
    
    /**
     * Getter for property backColor.
     * @return Value of property backColor.
     */
    public java.awt.Color getBackColor() {
        return backColor;
    }
    
    /**
     * Setter for property backColor.
     * @param backColor New value of property backColor.
     */
    public void setBackColor(java.awt.Color backColor) {
        this.backColor = backColor;
    }
    
}
