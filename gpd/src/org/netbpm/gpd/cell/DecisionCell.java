package org.netbpm.gpd.cell;

import org.netbpm.gpd.model.DecisionVO;

public class DecisionCell extends DefaultGpdCell {
	private DecisionVO model=new DecisionVO();
	//	Empty Constructor
	public DecisionCell() {
		this(null);
	}
	//	Construct Cell for Userobject
	public DecisionCell(Object userObject) {
		super(userObject);
	}
	/**
	 * @return
	 */
	public DecisionVO getModel() {
		return model;
	}

	/**
	 * @param decisionVO
	 */
	public void setModel(DecisionVO decisionVO) {
		model = decisionVO;
	}

	public String toString(){
		return model.getName();
	}

}
