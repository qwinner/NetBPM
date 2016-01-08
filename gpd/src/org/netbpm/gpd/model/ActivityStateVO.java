package org.netbpm.gpd.model;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import org.jgraph.graph.DefaultGraphCell.ValueChangeHandler;

public class ActivityStateVO extends AbstractVO implements StateVO ,ValueChangeHandler{
	// <activity-state name="">
	private String name ="activitystate";
	//<description/>
	private String description ="insert Description here";
	private String role = null;
	private AssignmentVO assignment= null;
	private List actionList= new LinkedList();
	private List fieldList=new LinkedList();
	private Color backColor=Color.white;
	
	public ActivityStateVO(){
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getRole() {
		return role;
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
	public void setRole(String string) {
		role = string;
	}

	/**
	 * @return
	 */
	public List getActionList() {
		return actionList;
	}

	/**
	 * @return
	 */
	public AssignmentVO getAssignment() {
		return assignment;
	}

	/**
	 * @return
	 */
	public List getFieldList() {
		return fieldList;
	}

	/**
	 * @param actionVO
	 */
	public void setActionList(List actionVOlist) {
		actionList = actionVOlist;
	}

	/**
	 * @param assignmentVO
	 */
	public void setAssignment(AssignmentVO assignmentVO) {
		assignment = assignmentVO;
	}

	/**
	 * @param fieldVO
	 */
	public void setFieldList(List fieldVOlist) {
		fieldList = fieldVOlist;
	}

        /**
         * Getter for property backColor.
         * @return Value of property backColor.
         */
        public java.awt.Color getBackColor() {
            return backColor;
        }
        
        /**
         * Setter for property backColor.
         * @param backColor New value of property backColor.
         */
        public void setBackColor(java.awt.Color backColor) {
            this.backColor = backColor;
        }

        /**
         * Getter for property backColor.
         * @return Value of property backColor.
         */
        public int getRGBBackColor() {
            return backColor.getRGB();
        } // Marshaller for .gpd not support Color

        /**
         * Setter for property backColor.
         * @param bColor New value of property backColor.
         */
        public void setRGBBackColor(int rgbColor) {
            this.backColor = new Color(rgbColor);
        } // Marshaller for .gpd not support Color

	/**
	 * @see org.jgraph.graph.DefaultGraphCell.ValueChangeHandler#valueChanged(java.lang.Object)
	 */
	public Object valueChanged(Object arg0) {
		if (arg0 instanceof String){
			name=(String)arg0;
			return this;
		}
		return arg0;
	}

	/**
	 * @see org.jgraph.graph.DefaultGraphCell.ValueChangeHandler#clone()
	 */
	public Object clone() {
		return null;
	}

	public String toString(){
		return name;
	}

}
