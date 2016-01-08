
package org.netbpm.gpd.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import org.jgraph.JGraph;
import org.jgraph.graph.CellMapper;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;
import org.netbpm.gpd.cell.StartCell;

public class StartView extends VertexView {

    public static final Color DEFAULT_BACKGROUND = Color.black;
    public static StartRenderer renderer = new StartRenderer();

	public StartView(Object cell, JGraph graph, CellMapper cm) {
                super(cell, graph, cm);
	}

	public CellViewRenderer getRenderer() {
		getAttributes().put("backColor",renderer.getBackColor());
                renderer.setBackColor( ((StartCell)cell).getModel().getBackColor() );
//System.out.println(" START VIEW get renderer "+renderer.getBackColor());
                return renderer;
	}
	public static class StartRenderer extends VertexRenderer implements CellViewRenderer {
		private Color backColor=Color.black;
                
                public void paint(Graphics g) {
			int b = borderWidth;
			Dimension d = getSize();
			boolean tmp = selected;

                        g.setColor(getBackColor());
                        g.fillOval(b - 1 , b - 1 , d.width - b + 1 , d.height - b + 1);
			g.setColor(getForeground());
			g.drawOval(b - 1, b - 1, d.width - b + 1 , d.height - b + 1);
			try {
				setBorder(null);
				setOpaque(false);
				selected = false;
				super.paint(g);
			} finally {
				selected = tmp;
			}
		}
                
                /**
                 * Getter for property backgroundColor.
                 * @return Value of property backgroundColor.
                 */
                public java.awt.Color getBackColor() {
                    return backColor;
                }
                
                /**
                 * Setter for property backgroundColor.
                 * @param backgroundColor New value of property backgroundColor.
                 */
                public void setBackColor(java.awt.Color backColor) {
                    this.backColor = backColor;
                }
                
	}

/*	public static class StartRenderer1 extends VertexRenderer {

		public void paint(Graphics g) {
			int b = borderWidth;
			Graphics2D g2 = (Graphics2D) g;
			Dimension d = getSize();
			boolean tmp = selected;
			if (super.isOpaque()) {
				g.setColor(super.getBackground());
				g.fillOval(b - 1, b - 1, d.width - b, d.height - b);
			}
			try {
				setBorder(null);
				setOpaque(false);
				selected = false;
				super.paint(g);
			} finally {
				selected = tmp;
			}
			if (bordercolor != null) {
				g.setColor(bordercolor);
				g2.setStroke(new BasicStroke(b));
				g.fillOval(b - 1, b - 1, d.width - b, d.height - b);
			}
			if (selected) {
				g2.setStroke(GraphConstants.SELECTION_STROKE);
				g.setColor(graph.getHighlightColor());
				g.drawOval(b - 1, b - 1, d.width - b, d.height - b);
			}
		}
	}
*/
}
