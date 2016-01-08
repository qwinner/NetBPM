package org.netbpm.gpd.io;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;
import org.netbpm.gpd.GpdGraph;
import org.netbpm.gpd.GpdMarqueeHandler;
import org.netbpm.gpd.cell.ActivityCell;
import org.netbpm.gpd.cell.DecisionCell;
import org.netbpm.gpd.cell.DefaultGpdCell;
import org.netbpm.gpd.cell.EndCell;
import org.netbpm.gpd.cell.ForkCell;
import org.netbpm.gpd.cell.JoinCell;
import org.netbpm.gpd.cell.StartCell;
import org.netbpm.gpd.cell.Transition;

public abstract class AbstractExport implements GraphModelFileFormat {
	private Map stateMap=new HashMap();
	
	protected GpdGraph graph=null;


	protected void init(GpdGraph gpGraph){
		graph=gpGraph;
		Object[] cellArray = gpGraph.getAll();
		for (int i=0;i<cellArray.length;i++){
			if (!(cellArray[i] instanceof Transition || cellArray[i] instanceof DefaultPort)){
				stateMap.put(cellArray[i].toString(),cellArray[i]);
			}
		}
	}

	// Insert a new Vertex at point
	public void insert(Point point, DefaultGraphCell vertex,Dimension size) {
		if (stateMap.get(getCellName(vertex))==null){
			// Add one Floating Port
			vertex.add(new DefaultPort());
			// Snap the Point to the Grid
			point = graph.snap(new Point(point));
			// Default Size for the new Vertex
			if (size==null){
				size=new Dimension(25,25);
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
			//add to stateMap
			stateMap.put(getCellName(vertex),vertex);
		}
	}


	protected DefaultGpdCell getCellByName(String name){
		Object  cell=stateMap.get(name);
		if (cell==null){
			return null;
		}
		return (DefaultGpdCell)cell;
	}
	/**
	 * @param vertex
	 * @return
	 */
	protected String getCellName(Object vertex) {
		if (vertex instanceof ActivityCell){
			return ((ActivityCell)vertex).getModel().getName();
		} else if (vertex instanceof DecisionCell){
			return((DecisionCell)vertex).getModel().getName();
		} else if (vertex instanceof EndCell){
			return((EndCell)vertex).getModel().getName();
		} else if (vertex instanceof ForkCell){
			return((ForkCell)vertex).getModel().getName();
		} else if (vertex instanceof StartCell){
			return((StartCell)vertex).getModel().getName();
		} else if (vertex instanceof JoinCell){
			return((JoinCell)vertex).getModel().getName();
		}		
		return null;
	}


	protected GraphModel getGraphModel(){
		return graph.getModel();
	}
	/**
	 * Return a list of all transition that has the same source
	 * @param model
	 * @param startCell
	 * @return
	 */
	//((GpdMarqueeHandler)graph.getMarqueeHandler())
	protected List findTransitionBySource(DefaultGpdCell sourceCell) {
		return ((GpdMarqueeHandler)graph.getMarqueeHandler()).findTransitionBySource(sourceCell);
	}
	
	protected DefaultEdge findConnection(DefaultGpdCell source, DefaultGpdCell target) {
		return ((GpdMarqueeHandler)graph.getMarqueeHandler()).findConnection(source,target);
	}
}
