package org.netbpm.gpd.model;


public class ParameterVO extends AbstractVO{
	private String name=null;
	private String value=null;
	private String clazz=null;

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}
	
	/**
	 * @return
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * @param string
	 */
	public void setClazz(String string) {
		clazz = string;
	}

	public String toString(){
		if (value!=null) {
			int index = value.length();
			if (index<10){
				return name+"="+value;
			} else {
				return name+"="+value.substring(0,9)+"..";
			}
		}
		return name;
	}

}
