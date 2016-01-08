package org.netbpm.gpd.dialog.panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.netbpm.gpd.cell.EndCell;
import org.netbpm.gpd.cell.ForkCell;
import org.netbpm.gpd.cell.JoinCell;
import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.event.EventInitializer;

public class NamePanel extends AbstractFormPanel {

	public JTextField nameTextField;
	private JLabel nameLabel;
	public JButton saveNamePanelDataButton;
	/**
	 * @param controller
	 */
	public NamePanel(Controller controller) {
		super(controller);
		EventInitializer.addEventListeners(this,controller);
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#createComponents()
	 */
	protected void createComponents() {
		nameLabel=wFac.createLabel("name:");
		nameTextField=wFac.createTextField();
		Object model = getController().getModel();
		if (model instanceof EndCell){
			nameTextField.setText(((EndCell)model).getModel().getName());
		} else if (model instanceof ForkCell){
			nameTextField.setText(((ForkCell)model).getModel().getName());
		} else if (model instanceof JoinCell){
			nameTextField.setText(((JoinCell)model).getModel().getName());
		}
		saveNamePanelDataButton=wFac.createSavePanelButton();
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#arrangeComponents()
	 */
	protected void arrangeComponents() {
		toButtonPanel(nameLabel,nameTextField);
		toButtonPanel(saveNamePanelDataButton);
	}

}
