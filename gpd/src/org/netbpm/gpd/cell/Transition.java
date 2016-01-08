package org.netbpm.gpd.cell;

import org.jgraph.graph.DefaultEdge;
import org.netbpm.gpd.model.TransitionVO;

public class Transition extends DefaultEdge {
	private TransitionVO model= new TransitionVO();

	public Transition() {};

	/**
	 * Constructs an edge that holds a reference to the specified user object.
	 *
	 * @param userObject reference to the user object
	 */
	public Transition(Object userObject) {
		super(userObject);
	}
	/**
	 * @return
	 */
	public TransitionVO getModel() {
		return model;
	}

	/**
	 * @param transitionVO
	 */
	public void setModel(TransitionVO transitionVO) {
		model = transitionVO;
		setUserObject(model);
	}

	/**
	 * Sets the source of the edge.
	 */
	public void setSource(Object port) {
		if (port!=null){
			super.setSource(port);
		}
	}

	/**
	 * Returns the target of <code>edge</code>.
	 */
	public void setTarget(Object port) {
		if (port!=null){
			super.setTarget(port);
		}
	}

	public String toString(){
		return model.getName();
	}

	public Object getUserObject(){
		return model;
	}

}
