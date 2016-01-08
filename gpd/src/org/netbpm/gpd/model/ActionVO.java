package org.netbpm.gpd.model;

import java.util.LinkedList;
import java.util.List;

public class ActionVO extends AbstractVO {
	private String event=null;
	private String handler=null;
	private String onException=null;
	private List parameterList=new LinkedList();

	/**
	 * @return
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @return
	 */
	public String getHandler() {
		return handler;
	}

	/**
	 * @return
	 */
	public List getParameterList() {
		return parameterList;
	}

	/**
	 * @param string
	 */
	public void setEvent(String string) {
		event = string;
	}

	/**
	 * @param string
	 */
	public void setHandler(String string) {
		handler = string;
	}

	/**
	 * @param list
	 */
	public void setParameterList(List list) {
		parameterList = list;
	}

	/**
	 * @return
	 */
	public String getOnException() {
		return onException;
	}

	/**
	 * @param string
	 */
	public void setOnException(String string) {
		onException = string;
	}
	
	public String toString(){
		if (handler!=null){
			return getShortClassname(handler);
		}
		return "action";
	}
}
