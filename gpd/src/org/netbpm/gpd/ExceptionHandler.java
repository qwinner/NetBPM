package org.netbpm.gpd;

import java.awt.Component;

public class ExceptionHandler {
	private static ExceptionHandler instance = null;
	private Component component=null;
	private ExceptionHandler(){
	}
	
	public static ExceptionHandler getInstance(){
		if (instance==null){
			instance= new ExceptionHandler();
		}
		return instance;
	}
	
	public void handleException(Throwable throwable){
		throwable.printStackTrace();
		javax.swing.JOptionPane.showMessageDialog(component, throwable.getMessage());
	}
	
	public void showMessage(String message){
		javax.swing.JOptionPane.showMessageDialog(component, message);
	}

	public void init(Component component){
		this.component=component;
	}
}
