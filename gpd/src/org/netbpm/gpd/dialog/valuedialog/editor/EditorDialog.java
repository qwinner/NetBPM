package org.netbpm.gpd.dialog.valuedialog.editor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import org.netbpm.gpd.dialog.syntax.BatchFileTokenMarker;
import org.netbpm.gpd.dialog.syntax.CCTokenMarker;
import org.netbpm.gpd.dialog.syntax.CTokenMarker;
import org.netbpm.gpd.dialog.syntax.EiffelTokenMarker;
import org.netbpm.gpd.dialog.syntax.HTMLTokenMarker;
import org.netbpm.gpd.dialog.syntax.IDLTokenMarker;
import org.netbpm.gpd.dialog.syntax.JEditTextArea;
import org.netbpm.gpd.dialog.syntax.JavaScriptTokenMarker;
import org.netbpm.gpd.dialog.syntax.JavaTokenMarker;
import org.netbpm.gpd.dialog.syntax.PHPTokenMarker;
import org.netbpm.gpd.dialog.syntax.PatchTokenMarker;
import org.netbpm.gpd.dialog.syntax.PerlTokenMarker;
import org.netbpm.gpd.dialog.syntax.PropsTokenMarker;
import org.netbpm.gpd.dialog.syntax.PythonTokenMarker;
import org.netbpm.gpd.dialog.syntax.ShellScriptTokenMarker;
import org.netbpm.gpd.dialog.syntax.TSQLTokenMarker;
import org.netbpm.gpd.dialog.syntax.TokenMarker;
import org.netbpm.gpd.dialog.syntax.XMLTokenMarker;

public	class EditorDialog extends JDialog
					   implements PropertyChangeListener,ItemListener {//, ValueChangeListener {
		private String typedText = null;

		private JOptionPane optionPane;

		private String btnEnter = "Enter";
		private String btnCancel = "Cancel";
		private String selectedValue=null;
		private JEditTextArea textarea;
		private JComboBox javasyntaxComboBox;
		private Object[] syntaxOptions={new JavaTokenMarker(),new BatchFileTokenMarker(), 
										new CCTokenMarker(),new CTokenMarker(),new EiffelTokenMarker(),
										new HTMLTokenMarker(),new IDLTokenMarker(),new JavaScriptTokenMarker(),
										new PatchTokenMarker(),new PerlTokenMarker(),new PHPTokenMarker(),
										new PropsTokenMarker(),new PythonTokenMarker(),	new ShellScriptTokenMarker(), 
										new TSQLTokenMarker(),new XMLTokenMarker()};

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

			setJMenuBar(createMenuBar());
			javasyntaxComboBox=new JComboBox(syntaxOptions);
			javasyntaxComboBox.addItemListener(this);
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

		public JMenuBar createMenuBar(){
			JMenuBar menuBar=new JMenuBar();
			JMenu editmenu=new JMenu("Edit");
			editmenu.add(new CutAction(textarea));
			editmenu.add(new CopyAction(textarea));
			editmenu.add(new PasteAction(textarea));
			menuBar.add(editmenu);
			return menuBar;
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

		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent event) {
			textarea.setTokenMarker((TokenMarker) event.getItem());
		}


	}
