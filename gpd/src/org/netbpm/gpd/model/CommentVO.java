package org.netbpm.gpd.model;

import java.awt.Color;
import org.jgraph.graph.DefaultGraphCell.ValueChangeHandler;

public class CommentVO extends AbstractVO implements ValueChangeHandler{
	private String name = "Comment";
	private Color backColor = new Color(255,255,192);
	
	public CommentVO(){
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