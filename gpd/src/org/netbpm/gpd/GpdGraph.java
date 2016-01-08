package org.netbpm.gpd;

import java.util.ArrayList;

import org.jgraph.JGraph;
import org.jgraph.graph.*;
import org.netbpm.gpd.cell.*;
import org.netbpm.gpd.renderer.TransitionView;
import org.netbpm.gpd.view.*;

public class GpdGraph extends JGraph {

	public GpdGraph(GraphModel model, GraphLayoutCache view) {
		super(model, view, null);

	}
	public Object[] getAll() {
		return getDescendants(getRoots());
	}

	public Object[] getEdges(Object[] cells) {
		if (cells != null) {
			ArrayList result = new ArrayList();
			for (int i = 0; i < cells.length; i++)
				if (isEdge(cells[i]))
					result.add(cells[i]);
			return result.toArray();
		}
		return null;
	}

	public boolean isEdge(Object object) {
		return (object instanceof Edge);
	}

	public Object getSourceVertex(Object edge) {
		Object sourcePort = graphModel.getSource(edge);
		return graphModel.getParent(sourcePort);
	}

	public Object getTargetVertex(Object edge) {
		Object targetPort = graphModel.getTarget(edge);
		return graphModel.getParent(targetPort);
	}

	public CellView getSourceView(Object edge) {
		Object source = getSourceVertex(edge);
		return getGraphLayoutCache().getMapping(source, false);
	}

	public CellView getTargetView(Object edge) {
		Object target = getTargetVertex(edge);
		return getGraphLayoutCache().getMapping(target, false);
	}

	public Object[] getVertices(Object[] cells) {
		if (cells != null) {
			ArrayList result = new ArrayList();
			for (int i = 0; i < cells.length; i++)
				if (isVertex(cells[i]))
					result.add(cells[i]);
			return result.toArray();
		}
		return null;
	}
	/**
	 * Returns true if <code>object</code> is a vertex, that is, if it
	 * is not an instance of Port or Edge, and all of its children are
	 * ports, or it has no children.
	 */
	public boolean isVertex(Object object) {
		if (!(object instanceof Port) && !(object instanceof Edge))
			return !isGroup(object) && object != null;
		return false;
	}

	/**
	* Returns true if <code>object</code> is a vertex, that is, if it
	* is not an instance of Port or Edge, and all of its children are
	* ports, or it has no children.
	*/
   public boolean isGroup(Object cell) {
	   // Map the Cell to its View
	   CellView view = getGraphLayoutCache().getMapping(cell, false);
	   if (view != null)
		   return !view.isLeaf();
	   return false;
   }

	// Construct the Graph using the Model as its Data Source
	public GpdGraph(GraphModel model) {
		super(model);
		// Use a Custom Marquee Handler
		setMarqueeHandler(new GpdMarqueeHandler(this));
		// Tell the Graph to Select new Cells upon Insertion
		setSelectNewCells(true);
		// Make Ports Visible by Default
		setPortsVisible(true);
		// Use the Grid (but don't make it Visible)
		setGridEnabled(true);
		// Set the Grid Size to 10 Pixel
		setGridSize(6);
		// Set the Tolerance to 2 Pixel
		setTolerance(2);
		setGridVisible(true);
			
	}

	protected EdgeView createEdgeView(Object e, CellMapper cm) 
	{
		if (e instanceof Transition) {
			return (new TransitionView( e, this, cm ));
		} else {
			return (super.createEdgeView( e, cm ));
		} // end of else
	}
	
	//	Overrides JGraph.createVertexView
	protected VertexView createVertexView(Object v, CellMapper cm) {
		//	Return an EllipseView for EllipseCells
		if (v instanceof StartCell)
			return new StartView(v, this, cm);
		else if (v instanceof EndCell)
			return new EndView(v, this, cm);
		else if (v instanceof ActivityCell)
			return new ActivityView(v, this, cm);
		else if (v instanceof ForkCell)
			return new ForkView(v, this, cm);
		else if(v instanceof JoinCell)
				return new JoinView(v, this, cm);			
		else if(v instanceof DecisionCell)
				return new DecisionView(v, this, cm);					
		//	Else Call Superclass
		return super.createVertexView(v, cm);
	}

}
