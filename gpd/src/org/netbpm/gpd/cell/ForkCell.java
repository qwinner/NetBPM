package org.netbpm.gpd.cell;

import org.netbpm.gpd.model.ForkJoinVO;

public class ForkCell extends DefaultGpdCell {
	private ForkJoinVO model=new ForkJoinVO();

	//	Empty Constructor
	public ForkCell() {
		this(null);
	}
	//	Construct Cell for Userobject
	public ForkCell(Object userObject) {
		super(userObject);
		setUserObject(model);
	}
	/**
	 * @return
	 */
	public ForkJoinVO getModel() {
		return model;
	}

	/**
	 * @param joinVO
	 */
	public void setModel(ForkJoinVO joinVO) {
		model = joinVO;
		super.userObject=joinVO;
//		setUserObject(joinVO);
	}

	public String toString(){
		return model.getName();
	}

	public Object getUserObject(){
		return model;
	}

}
