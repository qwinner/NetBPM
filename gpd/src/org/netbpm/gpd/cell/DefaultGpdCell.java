package org.netbpm.gpd.cell;

import org.apache.commons.lang.SerializationUtils;
import org.jgraph.graph.DefaultGraphCell;


public abstract class DefaultGpdCell extends DefaultGraphCell {

	 public DefaultGpdCell(Object userObject){
		super(userObject);
	 }
	 
	public Object clone() {
		DefaultGpdCell newCell=(DefaultGpdCell)SerializationUtils.clone(this);
		newCell.children.clear();
		return newCell;
	}
 
}
