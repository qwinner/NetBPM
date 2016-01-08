package org.netbpm.gpd.dialog.panel.detailpanel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.event.EventInitializer;
import org.netbpm.gpd.dialog.panel.AbstractFormPanel;

public class TransitionPanel extends AbstractFormPanel {

	public JTextField nameTextField;
	private JLabel nameLabel;
	public JButton saveTransitionDataButton;

	/**
	 * @param controller
	 */
	public TransitionPanel(Controller controller) {
		super(controller);
		EventInitializer.addEventListeners(this,controller);
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#createComponents()
	 */
	protected void createComponents() {
		
		nameLabel=wFac.createLabel("name:");
		nameTextField=wFac.createTextField();
		saveTransitionDataButton=wFac.createSavePanelButton();
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#arrangeComponents()
	 */
	protected void arrangeComponents() {
		toButtonPanel(nameLabel, nameTextField);
		toButtonPanel(saveTransitionDataButton);
	}

}
