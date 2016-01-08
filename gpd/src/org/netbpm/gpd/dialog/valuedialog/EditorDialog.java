package org.netbpm.gpd.dialog.valuedialog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.netbpm.gpd.dialog.syntax.JEditTextArea;
import org.netbpm.gpd.dialog.syntax.JavaTokenMarker;

public	class EditorDialog extends JDialog
					   implements PropertyChangeListener {//, ValueChangeListener {
		private String typedText = null;

		private JOptionPane optionPane;

		private String btnEnter = "Enter";
		private String btnCancel = "Cancel";
		private String selectedValue=null;
		private JEditTextArea textarea;
		private JComboBox javasyntaxComboBox;
		private Object[] syntaxOptions={"Batch file","C++","C","Eiffel",
										"HTML","IDL","JavaScript","Java","Perl","PHP","properties/DOS INI","Python",
										"ShellScript","SQL","Transact-SQL","XML"};

		/**
		 * Returns null if the typed string was invalid;
		 * otherwise, returns the string as the user entered it.
		 */
		public String getValidatedText() {
			return typedText;
		}

		/** Creates the reusable dialog. */
		public EditorDialog(String text) {

			setTitle("Access");
			setSize(650,650);
			setModal(true);
			textarea = new JEditTextArea();
			textarea.setTokenMarker(new JavaTokenMarker());
			textarea.setText(text);
			javasyntaxComboBox=new JComboBox(syntaxOptions);
			//Create an array of the text and components to be displayed.
			String msgString1 = "select syntax";
			Object[] array = {msgString1,javasyntaxComboBox,textarea};

			//Create an array specifying the number of dialog buttons
			//and their text.
			Object[] options = {btnEnter, btnCancel};

			//Create the JOptionPane.
			optionPane = new JOptionPane(array,
										JOptionPane.QUESTION_MESSAGE,
										JOptionPane.YES_NO_OPTION,
										null,
										options,
										options[0]);

			//Make this dialog display it.
			setContentPane(optionPane);
			//Register an event handler that reacts to option pane state changes.
			optionPane.addPropertyChangeListener(this);
		}
		
		/** This method reacts to state changes in the option pane. */
		public void propertyChange(PropertyChangeEvent e) {
			String prop = e.getPropertyName();

			if (isVisible()
			 && (e.getSource() == optionPane)
			 && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
				 JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
				Object value = optionPane.getValue();

				if (value == JOptionPane.UNINITIALIZED_VALUE) {
					//ignore reset
					return;
				}

				if (btnEnter.equals(value)) {
					
					selectedValue=textarea.getText();
					clearAndHide();
				} else { //user closed dialog or clicked cancel
					clearAndHide();
				}
			}
		}

		/** This method clears the dialog and hides it. */
		public void clearAndHide() {
			setVisible(false);
		}
		/**
		 * @return
		 */
		public String getSelectedValue() {
			return selectedValue;
		}

	}
