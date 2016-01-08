package org.netbpm.gpd.model;


public class AttributeVO extends AbstractVO {
	private String name=null;
	private String type=null;
	private String initialValue=null;
	private String serializer=null;

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
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
	public void setType(String string) {
		type = string;
	}
	
	public String toString(){
		return name;
	}
	/**
	 * @return
	 */
	public String getInitialValue() {
		return initialValue;
	}

	/**
	 * @param string
	 */
	public void setInitialValue(String string) {
		initialValue = string;
	}

	/**
	 * @return
	 */
	public String getSerializer() {
		return serializer;
	}

	/**
	 * @param string
	 */
	public void setSerializer(String string) {
		serializer = string;
	}

}
