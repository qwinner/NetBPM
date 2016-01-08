package org.netbpm.gpd.dialog.panel.detailpanel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.event.EventInitializer;
import org.netbpm.gpd.dialog.panel.AbstractFormPanel;

public class FieldPanel extends AbstractFormPanel {

	public JTextField attributeTextField;
	private JLabel attributeLabel;
//	private JButton attributeButton;
	public JTextField accessTextField;
	private JLabel accessLabel;
	private JButton accessButton;
	public JTextField nameTextField;
	private JLabel nameLabel;
	public JTextField descriptionTextField;
	private JLabel descriptionLabel;
	public JButton saveFieldDataButton;

	/**
	 * @param controller
	 */
	public FieldPanel(Controller controller) {
		super(controller);
		EventInitializer.addEventListeners(this,controller);
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#createComponents()
	 */
	protected void createComponents() {
		
		attributeLabel=wFac.createLabel("attribute:");
		attributeTextField=wFac.createTextField();
//		attributeButton=wFac.createButtonPopdown();
		accessLabel=wFac.createLabel("access:");
		accessTextField=wFac.createTextField();
		accessButton=wFac.createButtonPopdown();

		nameTextField=wFac.createTextField();
		nameLabel=wFac.createLabel("name:");
		descriptionTextField=wFac.createTextField();
		descriptionLabel=wFac.createLabel("description:");
		saveFieldDataButton=wFac.createSavePanelButton();
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#arrangeComponents()
	 */
	protected void arrangeComponents() {
		toButtonPanel(attributeLabel,attributeTextField);
		toButtonPanel(accessLabel,accessTextField,accessButton);
		toButtonPanel(nameLabel,nameTextField);
		toButtonPanel(descriptionLabel,descriptionTextField);
		toButtonPanel(saveFieldDataButton);
	}

}
