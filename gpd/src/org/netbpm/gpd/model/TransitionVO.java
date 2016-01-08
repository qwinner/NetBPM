package org.netbpm.gpd.model;

import java.util.LinkedList;
import java.util.List;
import org.jgraph.graph.DefaultGraphCell.ValueChangeHandler;

public class TransitionVO extends AbstractVO implements ValueChangeHandler {
	private String name =null;
	private List actionList= new LinkedList();

	/**
	 * @return
	 */
	public List getActionList() {
		return actionList;
	}

	/**
	 * @param list
	 */
	public void setActionList(List list) {
		actionList = list;
	}

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
		if (name==null){
			return "";
		}
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
