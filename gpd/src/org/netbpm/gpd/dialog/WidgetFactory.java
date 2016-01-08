package org.netbpm.gpd.dialog;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * Beschreibung:    Factory zur Bereitstellung von Standardfunktionalitaeten.
 * the Controller. Additionally permissions depend on the state of the control.
 * <BR><BR><B>History:</B>
 * <PRE>--------------------------------------------------------------------------------
 */
public final class WidgetFactory {
	//TODO translate comments 
	// Singleton-Instanz
	private static WidgetFactory widgetFactory;
	public static int defaultSize = 10;

	/**
	 * Default Constructor wurde wegen Singleton auf privat gesetzt
	 */
	private WidgetFactory() {
	}

	/**
	 * Liefert die einzige verfügbare Instanz der WidgetFactory.
	 * @return com.cimt.dialog.WidgetFactory
	 */
	public static WidgetFactory instance() {
		if (widgetFactory == null) {
			widgetFactory = new WidgetFactory();
		}

		return widgetFactory;
	}

	/**
	 * Diese Methode erstellt die Beschriftungen fuer die GUI-Elemente
	 * in den Dialogen.
	 * @param name Bezeichnung für das jeweilige Label
	 *
	 * @return javax.swing.JLabel
	*/
	public final JLabel createLabel(String name) {
		JLabel label = new JLabel(name);

		return label;
	}

	public final JButton createButtonPopdown(){
		URL popdownUrl = getClass().getClassLoader().getResource("gif/popdown.png");
		ImageIcon popdownIcon = new ImageIcon(popdownUrl);
		JButton button = new JButton();
		button.setIcon(popdownIcon);
		return button;
	}

	/**
	 * Diese Methode erstellt einen RadioButton mit Text
	 * in den Dialogen.
	 *
	 * @return javax.swing.JRadioButton
	 */
	public final JRadioButton createRadioButton(String name) {
		return createRadioButton(name, true);
	}

	/**
	 * Diese Methode erstellt einen RadioButton mit Text
	 * in den Dialogen.
	 *
	 * @return javax.swing.JRadioButton
	 */
	public final JRadioButton createRadioButton(String name, boolean editable) {
		JRadioButton radioButton = new JRadioButton(name);
		radioButton.setEnabled(editable);
		radioButton.setHorizontalTextPosition(SwingConstants.RIGHT);
	
		return radioButton;
	}

	public JTextArea createTextArea() {
		JTextArea textArea=new JTextArea(3,20);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		return  textArea;
	}

	public JTextField createTextField() {
		return createTestField(20);		
	}
	/**
	 * @param i
	 * @return
	 */
	public JTextField createTestField(int i) {
		return  new JTextField(i);
	}

	/**
	 * @return
	 */
	public JButton createSavePanelButton() {
		return new JButton("save Panel");
	}

}