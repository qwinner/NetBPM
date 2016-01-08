package org.netbpm.gpd.dialog.controller;

import javax.swing.JPanel;

public interface Controller {

	JPanel getView();
	public Object getModel();
	public void setModel(Object cell);
}
