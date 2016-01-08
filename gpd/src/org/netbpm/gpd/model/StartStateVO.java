package org.netbpm.gpd.model;

import java.util.LinkedList;
import java.util.List;
import java.awt.Color;

public class StartStateVO extends AbstractVO implements StateVO{
	private String description ="insert Description here";
	private String name ="startstate";
	private String role = null;
	private List fieldList=new LinkedList();
	private List actionList= new LinkedList();
	private ProcessDefinition processDefinition=null;
        private Color backColor=Color.black;

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public List getFieldList() {
		return fieldList;
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
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @param list
	 */
	public void setFieldList(List list) {
		fieldList = list;
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
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @return
	 */
	public ProcessDefinition getProcessDefinition() {
		if (processDefinition==null){
			processDefinition= new ProcessDefinition();
		}
		return processDefinition;
	}

	/**
	 * @param definition
	 */
	public void setProcessDefinition(ProcessDefinition definition) {
		processDefinition = definition;
	}

	/**
	 * @return
	 */
	public List getActionList() {
		return actionList;
	}

	/**
	 * @param list
	 */
	public void setActionList(List list) {
		actionList = list;
	}

        /**
         * Getter for property backColor.
         * @return Value of property backColor.
         */
        public Color getBackColor() {
            return backColor;
        }

        /**
         * Setter for property backgroundColor.
         * @param backColor New value of property backColor.
         */
        public void setBackColor(Color backColor) {
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

	public String toString(){
		return "Startstate";
	}

}