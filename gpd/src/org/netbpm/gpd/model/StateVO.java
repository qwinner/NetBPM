package org.netbpm.gpd.model;

import java.util.List;

public interface StateVO {
	public abstract void setDescription(String description);
	public abstract String getDescription();
	/**
	 * @return
	 */
	public abstract String getName();
	/**
	 * @return
	 */
	public abstract String getRole();
	/**
	 * @param string
	 */
	public abstract void setName(String string);
	/**
	 * @param string
	 */
	public abstract void setRole(String string);
	/**
	 * @return
	 */
	public abstract List getActionList();
	/**
	 * @return
	 */
	public abstract List getFieldList();
	/**
	 * @param actionVO
	 */
	public abstract void setActionList(List actionVOlist);
	/**
	 * @param fieldVO
	 */
	public abstract void setFieldList(List fieldVOlist);
}