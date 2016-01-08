package org.netbpm.gpd.dialog.panel;

import java.awt.Component;

import org.netbpm.gpd.dialog.controller.Controller;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


public abstract class AbstractFormPanel extends AbstractPanel{
	private FormLayout layout = new FormLayout(
		"3px ,pref, 6px, pref:grow, 6px,pref, 3px",   
		"pref, 12px, pref, 12px, pref, 12px, pref, 12px, pref, 12px, pref, 12px, pref"); 

	private CellConstraints cc = new CellConstraints();
	private int pos=1;
			
	/**
	 * @param controller
	 */
	public AbstractFormPanel(Controller controller) {
		super(controller);
		setLayout(layout);
		buildGUI();
	}
	protected void toButtonPanel(Component comp1) {
		add(comp1,  cc.xy(4, pos*2-1));
		pos++;
	}

	protected void toButtonPanel(Component comp1,Component comp2) {
		add(comp1,  cc.xy(2, pos*2-1));
		add(comp2,  cc.xy(4, pos*2-1));
		pos++;
	}

	protected void toButtonPanel(Component comp1,Component comp2,Component comp3) {
		add(comp1,  cc.xy(2, pos*2-1));
		add(comp2,  cc.xy(4, pos*2-1));
		add(comp3,  cc.xy(6, pos*2-1));
		pos++;
	}
}
