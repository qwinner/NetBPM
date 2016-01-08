package org.netbpm.gpd.cell;

import org.netbpm.gpd.model.ActivityStateVO;

public class ActivityCell extends DefaultGpdCell {
	//	Empty Constructor
	private ActivityStateVO model = new ActivityStateVO(); 

	public ActivityCell() {
		this(null);
	}

	//	Construct Cell for Userobject
	public ActivityCell(Object userObject) {
		super(userObject);
		super.setUserObject(model);
	}

	public void setModel(ActivityStateVO properties) {
		this.model = properties;
		super.userObject=properties;
	}

	public ActivityStateVO getModel() {
		return model;
	}
	
	public String toString(){
		return model.getName();
	}
	
	
	public Object getUserObject(){
		return model;
	}

}
