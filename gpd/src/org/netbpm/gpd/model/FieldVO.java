package org.netbpm.gpd.model;


public class FieldVO extends AbstractVO{

	public static final String NOT_ACCESSIBLE="not-accessible";
	public static final String READ_ONLY="read-only";
	public static final String WRITE_ONLY="write-only";
	public static final String WRITE_ONLY_REQUIRED="write-only-required";	
	public static final String READ_WRITE="read-write";	
	public static final String READ_WRITE_REQUIRED="read-write-required";	

	private String attribute="";
	private String access="";
	private String name="";
	private String description="";
	private FormatterVO formatter=null;

	/**
	 * @return
	 */
	public String getAccess() {
		return access;
	}

	/**
	 * @return
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @param string
	 */
	public void setAccess(String string) {
		access = string;
	}

	/**
	 * @param string
	 */
	public void setAttribute(String string) {
		attribute = string;
	}
	
	public String toString(){
		return attribute;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public FormatterVO getFormatter() {
		return formatter;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @param string
	 */
	public void setFormatter(FormatterVO formater) {
		formatter = formater;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	public boolean equals(Object obj){
		if (obj instanceof FieldVO){
			return ((FieldVO)obj).getAttribute().equals(getAttribute());
		}
		return false;
	}
}
