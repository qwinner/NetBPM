package org.netbpm.gpd.dialog.panel.detailpanel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.event.EventInitializer;
import org.netbpm.gpd.dialog.panel.AbstractFormPanel;

public class ActionPanel extends AbstractFormPanel {

	public JTextField eventTextField;
	private JLabel eventLabel;
	public JTextField handlerTextField;
	private JLabel handlerLabel;
	public JTextField onExceptionTextField;
	private JLabel onExceptionLabel;
	private JButton eventButton;
	private JButton onExcepitonButton;
	private JButton actionhandlerButton;
	public JButton saveActionDataButton;

	/**
	 * @param controller
	 */
	public ActionPanel(Controller controller) {
		super(controller);
		EventInitializer.addEventListeners(this,controller);
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#createComponents()
	 */
	protected void createComponents() {
		eventLabel=wFac.createLabel("event:");
		eventTextField=wFac.createTextField();
		handlerLabel=wFac.createLabel("handler:");
		handlerTextField=wFac.createTextField();
		onExceptionLabel=wFac.createLabel("on Exception:");
		onExceptionTextField=wFac.createTextField();
		eventButton=wFac.createButtonPopdown();
		onExcepitonButton=wFac.createButtonPopdown();
		actionhandlerButton=wFac.createButtonPopdown();
		saveActionDataButton=wFac.createSavePanelButton();
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#arrangeComponents()
	 */
	protected void arrangeComponents() {
		toButtonPanel(eventLabel,eventTextField,eventButton);
		toButtonPanel(handlerLabel,handlerTextField,actionhandlerButton);
		toButtonPanel(onExceptionLabel,onExceptionTextField,onExcepitonButton);
		toButtonPanel(saveActionDataButton);
	}

}
