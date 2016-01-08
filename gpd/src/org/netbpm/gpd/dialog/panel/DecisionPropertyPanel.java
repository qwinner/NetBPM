package org.netbpm.gpd.dialog.panel;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.netbpm.gpd.cell.DecisionCell;
import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.controller.MasterDetailCellController;
import org.netbpm.gpd.model.DecisionVO;

public class DecisionPropertyPanel extends AbstractPropertyPanel {
	private JLabel headline;
	
	/**
	 * @param cell
	 */
	public DecisionPropertyPanel(Controller controller) {
		super(controller);
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#createComponents()
	 */
	protected void createComponents() {
		headline=new JLabel("Decision Properties");
		//build tree
		DecisionVO model = ((DecisionCell)getController().getModel()).getModel();
		((MasterDetailCellController)getController()).activateDetailPanel(this,model);
		DefaultMutableTreeNode root =  new DefaultMutableTreeNode(model);

		propertytree = new JTree(root);	
		//add parameter Tree
		createParameterSubTree(root,model.getParameterList());

		//select the node
		propertytree.addSelectionRow(0);
		((MasterDetailCellController)getController()).setLastSelection(root);
	}

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#arrangeComponents()
	 */
	protected void arrangeComponents() {
		expandJTree(propertytree,3);
		setMasterHeadline(headline);
		setMaster(new JScrollPane(propertytree));
	}

}
