package org.netbpm.gpd.model;

import java.util.LinkedList;
import java.util.List;

public class FormatterVO extends AbstractVO{
	private String formaterclass="formaterclass";
	private List parameterList=new LinkedList();
	/**
	 * @return
	 */
	public String getFormaterclass() {
		return formaterclass;
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
	public void setFormaterclass(String string) {
		formaterclass = string;
	}

	/**
	 * @param list
	 */
	public void setParameterList(List list) {
		parameterList = list;
	}

	public String toString (){
		return getShortClassname(formaterclass);
	}
}
