package org.netbpm.gpd.dialog.controller;



public abstract class AbstractController implements Controller {
	private Object cell;

	public AbstractController(){
		
	}

	/**
	 * @return
	 */
	public Object getModel() {
		return cell;
	}

	/**
	 * @param cell
	 */
	public void setModel(Object cell) {
		this.cell = cell;
	}

}
