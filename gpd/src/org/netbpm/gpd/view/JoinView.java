
package org.netbpm.gpd.view;


import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.*;
import org.jgraph.JGraph;
import org.jgraph.graph.CellMapper;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;
import java.awt.geom.*;

public class JoinView extends VertexView {

	public static JoinRenderer renderer = new JoinRenderer();

	public JoinView(Object cell, JGraph graph, CellMapper cm) {
		super(cell, graph, cm);
	}

	public CellViewRenderer getRenderer() {
		return renderer;
	}

	public static class JoinRenderer extends VertexRenderer {

		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			Dimension d = getSize();
			boolean tmp = selected;
			if (super.isOpaque()) {
				g.setColor(super.getBackground());
				g2.draw(new Line2D.Double((d.width), 0, d.width, d.height ));
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
				g2.setStroke(new BasicStroke(4));
				g2.draw(new Line2D.Double(0, d.height, d.width, d.height ));
			}
			if (selected) {
				g2.setStroke(GraphConstants.SELECTION_STROKE);
				g.setColor(graph.getHighlightColor());
				g2.draw(new Line2D.Double((d.width), 0, 0, d.height ));
			}
		}
	}

}
