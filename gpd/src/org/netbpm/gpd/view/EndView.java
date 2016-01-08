
package org.netbpm.gpd.view;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.*;
import java.awt.Graphics2D;
import org.jgraph.JGraph;
import org.jgraph.graph.CellMapper;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

public class EndView extends VertexView {

	public static EndRenderer renderer = new EndRenderer();

	public EndView(Object cell, JGraph graph, CellMapper cm) {
		super(cell, graph, cm);
	}

	public CellViewRenderer getRenderer() {
		return renderer;
	}
	
	public static class EndRenderer extends VertexRenderer {

		public void paint(Graphics g) {
			int b = borderWidth;
			Graphics2D g2 = (Graphics2D) g;
			Dimension d = getSize();
			boolean tmp = selected;
			g.setColor(Color.BLACK);
			g2.draw(new Ellipse2D.Double( b + 1 , b + 1 , d.width -2, d.height -2));
			g2.fill(new Ellipse2D.Double(( d.width/4) , (d.height/4)  , (d.width*2/3 )- b, (d.height*2/3) -b ));
			
			try {
				setBorder(null);
				setOpaque(false);
				selected = false;
				super.paint(g);
			} finally {
				selected = tmp;
			}
		}

	}	

	public static class EndRenderer1 extends VertexRenderer {

		public void paint(Graphics g) {
			int b = borderWidth;
			Graphics2D g2 = (Graphics2D) g;
			Dimension d = getSize();
			boolean tmp = selected;
			super.setOpaque(true);
			if (super.isOpaque()) {
				g.setColor(super.getBackground());
				g.drawOval(b - 1, b - 1, d.width - b + 10, d.height - b + 10);
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
				g.drawOval(b - 1, b - 1, d.width - b, d.height - b);
			}
			if (selected) {
				g2.setStroke(GraphConstants.SELECTION_STROKE);
				g.setColor(graph.getHighlightColor());
				//g.drawOval(b - 1, b - 1, d.width - b, d.height - b);
				g.drawOval(b - 1, b - 1, d.width - b + 100, d.height - b + 100);
				g.fillOval(b - 1, b - 1, d.width - b, d.height - b);
				g.drawOval(b - 1, b - 1, d.width - b + 100, d.height - b + 100);
			}
		}
	}

}
