package org.netbpm.gpd;

import org.apache.commons.configuration.HierarchicalDOMConfiguration;

public class Configuration {
	public static final String LAST_SELECTED_GPD_FILE="config.file.gpd";
	public static final String LAST_SELECTED_WEBEXPORT_FILE="config.file.webexport";
	public static final String LAST_SELECTED_PROCESSEXPORT_FILE="config.file.processexport";
	public static final String LAST_SELECTED_JPEG_FILE="config.file.jpegexport";

	public static final String FORMATTER_PACKAGE="config.formatter.package";
	public static final String ACTION_PACKAGE="config.action.package";
	public static final String DECISION_PACKAGE="config.decision.package";
	public static final String ASSIGNMENT_PACKAGE="config.assignment.package";
	public static final String SERIALIZER_PACKAGE="config.serializer.package";

	private HierarchicalDOMConfiguration config;

	private static Configuration myInstance=null;	

	private Configuration(){
		config = new HierarchicalDOMConfiguration();
		try {
			config.load(getClass().getClassLoader().getResource("config.xml"));
		} catch (javax.naming.ConfigurationException e) {
			ExceptionHandler.getInstance().handleException(e);
		}
	}

	public static Configuration getInstance(){
		if (myInstance==null){
			myInstance=new Configuration();
		}
		return myInstance;
	}

	public Object getProperty(String name){
		return config.getProperty(name);
	}

	public void setProperty(String name,String value){
		config.setProperty(name,value);
	}
}
