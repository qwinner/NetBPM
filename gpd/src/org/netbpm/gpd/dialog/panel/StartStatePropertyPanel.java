package org.netbpm.gpd.dialog.panel;

import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.netbpm.gpd.cell.StartCell;
import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.controller.MasterDetailCellController;
import org.netbpm.gpd.model.AttributeVO;
import org.netbpm.gpd.model.ProcessDefinition;
import org.netbpm.gpd.model.StartStateVO;

public class StartStatePropertyPanel extends AbstractPropertyPanel {
	
	/**
	 * @param controller
	 */
	public StartStatePropertyPanel(Controller controller) {
		super(controller);
	}

	public JPanel currentSelection;
	private JLabel headline;

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#createComponents()
	 */
	protected void createComponents() {
		headline=new JLabel("Process and Start Properties");

		//build tree
		StartStateVO model = ((StartCell)getController().getModel()).getModel();
		((MasterDetailCellController)getController()).activateDetailPanel(this,model.getProcessDefinition());
		DefaultMutableTreeNode root =  new DefaultMutableTreeNode(model.getProcessDefinition());
		
		createProcessSubTree(root,model.getProcessDefinition());
		createStartSubTree(root,model);
		currentSelection = new JPanel();

		propertytree = new JTree(root);

		//select the node
		propertytree.addSelectionRow(0);
		((MasterDetailCellController)getController()).setLastSelection(root);
	}

	/**
	 * @param root
	 * @param model
	 */
	private void createStartSubTree(DefaultMutableTreeNode root, StartStateVO model) {
		DefaultMutableTreeNode start= new DefaultMutableTreeNode(model);
		root.add(start);
		DefaultMutableTreeNode filds = new DefaultMutableTreeNode("fields");
		start.add(filds);
		createFieldSubTree(filds,model.getFieldList());

		DefaultMutableTreeNode actions =  new DefaultMutableTreeNode("actions");
		start.add(actions);
		createActionSubTree(actions,model.getActionList());
	}

	/**
	 * @param root
	 * @param list
	 */
	private void createProcessSubTree(DefaultMutableTreeNode attributes, ProcessDefinition definition) {

		DefaultMutableTreeNode actions =  new DefaultMutableTreeNode("actions");
		attributes.add(actions);
		Iterator it = definition.getAttributeList().iterator();
		while (it.hasNext()){
			AttributeVO attributeVO = (AttributeVO)it.next();
			DefaultMutableTreeNode attribute = new DefaultMutableTreeNode(attributeVO);
			attributes.add(attribute);
		}
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#arrangeComponents()
	 */
	protected void arrangeComponents() {
		expandJTree(propertytree,4);
		setMasterHeadline(headline);
		setMaster(new JScrollPane(propertytree));
	}

}
