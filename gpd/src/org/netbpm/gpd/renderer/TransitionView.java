package org.netbpm.gpd.renderer;

import java.awt.Point;

import org.jgraph.JGraph;
import org.jgraph.graph.CellMapper;
import org.jgraph.graph.CellView;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.PortView;
import org.jgraph.graph.VertexView;
import org.netbpm.gpd.cell.ActivityCell;
import org.netbpm.gpd.cell.Transition;

/**
 * An EdgeView that shows a Connection between tables
 *
 * @version $Revision: 1.1 $
 * @author Brian Sidharta
 */

public class TransitionView extends EdgeView 
{
    /**
     * Constructs an edge view for the specified model object.
     *
     * @param cell reference to the model object
     */
    public TransitionView( Object cell, JGraph graph, CellMapper mapper ) 
    {
        super(cell, graph, mapper);
        update();
    }

    /**
     * Returns the cached points for this edge.
     */
    public Point getPoint(int index) 
    {
    	// 0=source 1=target
		Point centerPoint=null;
		CellView activityview=null;
		Transition transition = ((Transition)cell);
		GraphModel jGraphModel = graph.getModel();
		Point point = super.getPoint( index );
		Object sourceCell = jGraphModel.getParent(jGraphModel.getSource(transition));
		Object targetCell = jGraphModel.getParent(jGraphModel.getTarget(transition));
		if (sourceCell instanceof ActivityCell && index==0 && points.get(index) instanceof PortView){
			centerPoint = simpleGetPoint(index);
			PortView pview=(PortView)points.get(index);
			activityview = pview.getParentView();
			if (centerPoint.y>point.y){
				//point.y=centerPoint.y;
				if (point.x<centerPoint.x){
					point.x=centerPoint.x-(activityview.getBounds().width/2);
				}else {
					point.x=centerPoint.x+(activityview.getBounds().width/2);
				}
			}
		} else if (targetCell instanceof ActivityCell && index==1  && points.get(index) instanceof PortView){
			centerPoint = simpleGetPoint(index);
			PortView pview=(PortView)points.get(index);
			activityview = pview.getParentView();
			if (centerPoint.y<point.y){
				//point.y=centerPoint.y;
				if (point.x<centerPoint.x){
					point.x=centerPoint.x-(activityview.getBounds().width/2);
				}else {
					point.x=centerPoint.x+(activityview.getBounds().width/2);
				}
			}
		} 
		return (point);
    }
	/**
	 * Lightweight version of getPoint that isn't recursive but doesn't
	 * return points that should be drawn
	 */
	private Point simpleGetPoint( int index )
	{
		Object obj = points.get(index);
		if (obj instanceof Point)
			return (Point) obj;
		else if (obj instanceof PortView) {
			VertexView vertex = (VertexView) ((CellView) obj).getParentView();
			return vertex.getCenterPoint();
		}
		return null;
        
	}
 
}
