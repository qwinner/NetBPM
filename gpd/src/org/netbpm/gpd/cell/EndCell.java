package org.netbpm.gpd.cell;

import org.netbpm.gpd.model.EndVO;

public class EndCell extends DefaultGpdCell {
	private EndVO model = new EndVO();

	//	Empty Constructor
	public EndCell() {
		this(null);
	}

	//	Construct Cell for Userobject
	public EndCell(Object userObject) {
		super(userObject);
	}
	/**
	 * @return
	 */
	public EndVO getModel() {
		return model;
	}

	/**
	 * @param endVO
	 */
	public void setModel(EndVO endVO) {
		model = endVO;
	}
	
	public String toString(){
		return model.getName();
	}

}
