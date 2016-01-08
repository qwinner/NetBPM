
package org.netbpm.gpd.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.*;
import java.awt.Graphics2D;
import org.jgraph.JGraph;
import org.jgraph.graph.CellMapper;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;
import org.netbpm.gpd.cell.DecisionCell;

public class DecisionView extends VertexView {

	public static EndRenderer renderer = new EndRenderer();

	public DecisionView(Object cell, JGraph graph, CellMapper cm) {
		super(cell, graph, cm);
	}

	public CellViewRenderer getRenderer() {
		renderer.setBackColor( ((DecisionCell)cell).getModel().getBackColor() );
                return renderer;
	}
       
	public static class EndRenderer extends VertexRenderer implements CellViewRenderer {
//            private DecisionCell decisionCell;
            private Color backColor=Color.white;
	
            public java.awt.Component getRendererComponent(JGraph graph, CellView cellview, boolean sel, boolean focus, boolean preview) {
                if (cellview instanceof DecisionView) {
                    DecisionView view = (DecisionView) cellview;
 //                   decisionCell = (DecisionCell) view.getCell();
                    this.graph = graph;
                    this.hasFocus = focus;
                    this.selected = sel;
                    this.preview = preview;
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

		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			Dimension d = getSize();
			boolean tmp = selected;
                        
			g.setColor(getBackColor());
                        int max = ((d.width+d.height)/4);
                        for ( double i=0; i<=max; i+=.5 )
                            g2.draw(new Line2D.Double(0+i, max+i, max+i, 0+i ));
			g.setColor(getForeground());
			g2.draw(new Line2D.Double(0, d.height/2, d.width/2, 0 ));
			g2.draw(new Line2D.Double(d.width/2, 0 , d.width, d.height/2));
			g2.draw(new Line2D.Double(d.width, d.height/2, d.width/2, d.height));
			g2.draw(new Line2D.Double(d.width/2, d.height,0, d.height/2 ));
			
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

}
