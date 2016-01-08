package org.netbpm.gpd.cell;

import org.netbpm.gpd.model.CommentVO;

public class Comment extends DefaultGpdCell {
	//	Empty Constructor
	private CommentVO model = new CommentVO(); 

	public Comment() {
		this(null);
	}

	//	Construct Cell for Userobject
	public Comment(Object userObject) {
		super(userObject);
		super.setUserObject(model);
	}

	public void setModel(CommentVO properties) {
		this.model = properties;
		super.userObject=properties;
	}

	public CommentVO getModel() {
		return model;
	}
	
	public String toString(){
		return model.getName();
	}
	
	
	public Object getUserObject(){
		return model;
	}

}
