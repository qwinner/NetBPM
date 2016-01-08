package org.netbpm.gpd.model;

import org.jgraph.graph.DefaultGraphCell.ValueChangeHandler;

public class ForkJoinVO extends AbstractVO implements ValueChangeHandler {
	private String name="forkjoin";

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}
	
	public String toString(){
		return name;
	}

	/**
	 * @see org.jgraph.graph.DefaultGraphCell.ValueChangeHandler#valueChanged(java.lang.Object)
	 */
	public Object valueChanged(Object arg0) {
		if (arg0 instanceof String){
			name=(String)arg0;
			return this;
		}
		return arg0;
	}

	/**
	 * @see org.jgraph.graph.DefaultGraphCell.ValueChangeHandler#clone()
	 */
	public Object clone() {
		return null;
	}
}
