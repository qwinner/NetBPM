package org.netbpm.gpd.model;

import java.util.LinkedList;
import java.util.List;

public class AssignmentVO extends AbstractVO {
	private String handler=null;
	private List parameterList= new LinkedList();
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
	public void setHandler(String string) {
		handler = string;
	}

	/**
	 * @param parameterVO
	 */
	public void setParameterList(List parameterVO) {
		parameterList = parameterVO;
	}

	public String toString(){
		return getShortClassname(handler);
	}
}
