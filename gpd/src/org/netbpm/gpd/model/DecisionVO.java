package org.netbpm.gpd.model;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class DecisionVO extends AbstractVO {
	private String name=null;
	private String handler=null;
	private List parameterList=new LinkedList();
	private Color backColor=Color.white;

	/**
	 * @return
	 */
	public String getHandler() {
		return handler;
	}

	/**
	 * @param string
	 */
	public void setHandler(String string) {
		handler = string;
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
	public List getParameterList() {
		return parameterList;
	}

	/**
	 * @param list
	 */
	public void setParameterList(List list) {
		parameterList = list;
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

        public String toString(){
		return name;
	}

}
