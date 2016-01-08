package org.netbpm.gpd.dialog.panel;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.netbpm.gpd.cell.Transition;
import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.model.TransitionVO;

public class TransitionPropertyPanel extends AbstractPropertyPanel {
	
	/**
	 * @param controller
	 */
	public TransitionPropertyPanel(Controller controller) {
		super(controller);
	}

	private JLabel headline;

	/**
	 * @see org.netbpm.gpd.dialog.panel.AbstractPanel#createComponents()
	 */
	protected void createComponents() {
		headline=new JLabel("Transiton Properties");
		//build tree
		TransitionVO model = ((Transition)getController().getModel()).getModel();
		DefaultMutableTreeNode root =  new DefaultMutableTreeNode(model);
		DefaultMutableTreeNode actions =  new DefaultMutableTreeNode("actions");
		root.add(actions);
		createActionSubTree(actions,model.getActionList());

		propertytree = new JTree(root);	
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
