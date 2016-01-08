package org.netbpm.gpd.dialog.valuedialog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public	class OnExceptionDialog extends JDialog
					   implements PropertyChangeListener{
		private String typedText = null;
		private JComboBox accessComboBox;

		private JOptionPane optionPane;

		private String btnEnter = "Enter";
		private String btnCancel = "Cancel";
		private Object[] accessOptions={"rollback","log","ignore"};
		private String selectedValue=null;
		/**
		 * Returns null if the typed string was invalid;
		 * otherwise, returns the string as the user entered it.
		 */
		public String getValidatedText() {
			return typedText;
		}

		/** Creates the reusable dialog. */
		public OnExceptionDialog() {

			setTitle("on Exception");
			setSize(350,150);
			setModal(true);
			accessComboBox = new JComboBox(accessOptions);

			//Create an array of the text and components to be displayed.
			String msgString1 = "Should be one of the following:";
			Object[] array = {msgString1,accessComboBox};

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
					
					selectedValue=(String)accessComboBox.getSelectedItem();
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
