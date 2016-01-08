package org.netbpm.gpd.cell;

import org.netbpm.gpd.model.StartStateVO;

public class StartCell extends DefaultGpdCell {
	private StartStateVO model=new StartStateVO(); 

	//	Empty Constructor
	public StartCell() {
		this(null);
	}
	//	Construct Cell for Userobject
	public StartCell(Object userObject) {
		super(userObject);
	}
	
	/**
	 * @return
	 */
	public StartStateVO getModel() {
		return model;
	}

	/**
	 * @param stateVO
	 */
	public void setModel(StartStateVO stateVO) {
		model = stateVO;
	}

	public String toString(){
		return model.getName();
	}

}
