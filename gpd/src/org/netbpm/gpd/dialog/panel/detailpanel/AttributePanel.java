package org.netbpm.gpd.dialog.panel.detailpanel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.event.EventInitializer;
import org.netbpm.gpd.dialog.panel.AbstractFormPanel;

public class AttributePanel extends AbstractFormPanel {

	private JButton typeButton;
	public JTextField typeTextField;
	private JLabel typeLabel;
	public JTextField nameTextField;
	private JLabel nameLabel;
	public JTextField initialValueTextField;
	private JLabel initialValueLabel;
	public JTextField serializerTextField;
	private JLabel serializerLabel;
	private JButton serializerhandlerButton;
	public JButton saveAttributeDataButton;

	/**
	 * @param controller
	 */
	public AttributePanel(Controller controller) {
		super(controller);
		EventInitializer.addEventListeners(this,controller);
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#createComponents()
	 */
	protected void createComponents() {
		
		nameLabel=wFac.createLabel("name:");
		nameTextField=wFac.createTextField();
		typeLabel=wFac.createLabel("type:");
		typeTextField=wFac.createTextField();
		typeButton=wFac.createButtonPopdown();
		initialValueLabel=wFac.createLabel("initial value:");
		initialValueTextField=wFac.createTextField();
		serializerLabel=wFac.createLabel("serializer:");
		serializerTextField=wFac.createTextField();
		serializerhandlerButton=wFac.createButtonPopdown();
		saveAttributeDataButton=wFac.createSavePanelButton();
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#arrangeComponents()
	 */
	protected void arrangeComponents() {
		toButtonPanel(nameLabel,nameTextField);
		toButtonPanel(typeLabel,typeTextField,typeButton);
		toButtonPanel(initialValueLabel,initialValueTextField);
		toButtonPanel(serializerLabel,serializerTextField,serializerhandlerButton);
		toButtonPanel(saveAttributeDataButton);
	}

}
