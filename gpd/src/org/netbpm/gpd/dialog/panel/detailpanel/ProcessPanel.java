package org.netbpm.gpd.dialog.panel.detailpanel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.event.EventInitializer;
import org.netbpm.gpd.dialog.panel.AbstractFormPanel;

public class ProcessPanel extends AbstractFormPanel {

	public JTextField descriptionTextField;
	private JLabel descriptionLabel;
	public JTextField nameTextField;
	private JLabel nameLabel;
	public JTextField responsibleTextField;
	private JLabel responsibleLabel;
	public JButton saveProcessDataButton;

	/**
	 * @param controller
	 */
	public ProcessPanel(Controller controller) {
		super(controller);
		EventInitializer.addEventListeners(this,controller);
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#createComponents()
	 */
	protected void createComponents() {
		nameLabel=wFac.createLabel("name:");
		nameTextField=wFac.createTextField();
		descriptionLabel=wFac.createLabel("description:");
		descriptionTextField=wFac.createTextField();
		responsibleLabel=wFac.createLabel("responsible:");
		responsibleTextField=wFac.createTextField();
		saveProcessDataButton=wFac.createSavePanelButton();
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#arrangeComponents()
	 */
	protected void arrangeComponents() {
		toButtonPanel(nameLabel,nameTextField);
		toButtonPanel(descriptionLabel,descriptionTextField);
		toButtonPanel(responsibleLabel,responsibleTextField);
		toButtonPanel(saveProcessDataButton);
	}

}
