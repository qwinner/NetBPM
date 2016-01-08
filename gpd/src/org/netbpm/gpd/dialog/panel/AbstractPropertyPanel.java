package org.netbpm.gpd.dialog.panel;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.controller.MasterDetailCellController;
import org.netbpm.gpd.model.ActionVO;
import org.netbpm.gpd.model.ActivityStateVO;
import org.netbpm.gpd.model.AssignmentVO;
import org.netbpm.gpd.model.AttributeVO;
import org.netbpm.gpd.model.DecisionVO;
import org.netbpm.gpd.model.FieldVO;
import org.netbpm.gpd.model.FormatterVO;
import org.netbpm.gpd.model.ParameterVO;
import org.netbpm.gpd.model.ProcessDefinition;
import org.netbpm.gpd.model.StartStateVO;
import org.netbpm.gpd.model.TransitionVO;

public abstract class AbstractPropertyPanel extends MasterDetailCellPanel{

	public JTree propertytree;
	private JPopupMenu popAttributeMenu;
	private JMenuItem popAttributeMenuItemDelete;

	private JPopupMenu popProcessMenu;
	private JMenuItem popAttributeMenuItemCreate;
	private JMenuItem popActionMenuItemCreate4;

	private JPopupMenu popStartStateMenu;
	private JMenuItem popFieldMenuItemCreate;
	private JMenuItem popActionMenuItemCreate3;

	private JPopupMenu popFieldMenu;
	private JMenuItem popFieldMenuItemDelete;
	private JMenuItem popFormaterMenuItemCreate;

	private JPopupMenu popActivityStateMenu;
	private JMenuItem popActionMenuItemCreate;
	private JMenuItem popFieldMenuItemCreate2;
	private JMenuItem popAssignmentMenuItemCreate;
	
	private JPopupMenu popActionMenu;
	private JMenuItem popActionMenuItemDelete;
	private JMenuItem popParameterMenuItemCreate;
	private JMenuItem popMoveUpMenuItem;
	private JMenuItem popMoveDownMenuItem;

	private JPopupMenu popTransitionMenu;
	private JMenuItem popActionMenuItemCreate2;

	private JMenuItem popParameterMenuItemDelete;
	private JPopupMenu popParameterMenu;

	private JPopupMenu popAssignmentMenu;
	private JMenuItem popParameterMenuItemCreate1;
	private JMenuItem popAssignmentMenuItemDelete;

	private JPopupMenu popDecisionMenu;
	private JMenuItem popParameterMenuItemCreate2;

	private JPopupMenu popFormatterMenu;
	private JMenuItem popFormaterMenuItemDelete;
	private JMenuItem popParameterMenuItemCreate3;
	
	private JMenuItem popFieldMenuItemUp;
	private JMenuItem popFieldMenuItemDown;

	public static final String ATTRIBUTE_DELETE="delete Attribute";
	public static final String ATTRIBUTE_CREATE="create Attribute";
	public static final String FIELD_CREATE="create Field";
	public static final String FIELD_DELETE="delete Field";
	public static final String FORMATER_CREATE="create Formater";
	public static final String FORMATER_DELETE="delete Formater";

	public static final String ACTION_CREATE="create Action";
	public static final String ACTION_DELETE="delete Action";
	public static final String PARAMETER_CREATE="create Parameter";
	public static final String PARAMETER_DELETE="delete Parameter";
	public static final String ASSIGNMENT_CREATE="create Assignment";
	public static final String ASSIGNMENT_DELETE="delete Assignment";

	public static final String MOVE_UP_ACTION="move up action";
	public static final String MOVE_DOWN_ACTION="move down action";

	public static final String MOVE_UP_FIELD="move up field";
	public static final String MOVE_DOWN_FIELD="move down field";
	/**
	 * @param controller
	 */
	public AbstractPropertyPanel(Controller controller) {
		super(controller);
		// create popupmenu
		popAttributeMenu = new JPopupMenu();
		popAttributeMenuItemDelete = new JMenuItem(ATTRIBUTE_DELETE);
		popAttributeMenu.add(popAttributeMenuItemDelete);

		popProcessMenu =  new JPopupMenu();
		popAttributeMenuItemCreate = new JMenuItem(ATTRIBUTE_CREATE);
		popActionMenuItemCreate4 = new JMenuItem(ACTION_CREATE);
		popProcessMenu.add(popAttributeMenuItemCreate);
		popProcessMenu.add(popActionMenuItemCreate4);
		
		popStartStateMenu =  new JPopupMenu();
		popFieldMenuItemCreate = new JMenuItem(FIELD_CREATE);
		popActionMenuItemCreate3 = new JMenuItem(ACTION_CREATE);
		popStartStateMenu.add(popFieldMenuItemCreate);
		popStartStateMenu.add(popActionMenuItemCreate3);

		popFieldMenu =  new JPopupMenu();
		popFieldMenuItemDelete = new JMenuItem(FIELD_DELETE);
		popFormaterMenuItemCreate = new JMenuItem(FORMATER_CREATE);
		popFieldMenuItemUp = new JMenuItem(MOVE_UP_FIELD);
		popFieldMenuItemDown = new JMenuItem(MOVE_DOWN_FIELD);
		popFieldMenu.add(popFieldMenuItemDelete);
		popFieldMenu.add(popFormaterMenuItemCreate);
		popFieldMenu.add(popFieldMenuItemUp);
		popFieldMenu.add(popFieldMenuItemDown);
		
		popActivityStateMenu =  new JPopupMenu();
		popActionMenuItemCreate = new JMenuItem(ACTION_CREATE);
		popActivityStateMenu.add(popActionMenuItemCreate);
		popFieldMenuItemCreate2 = new JMenuItem(FIELD_CREATE);
		popActivityStateMenu.add(popFieldMenuItemCreate2);
		popAssignmentMenuItemCreate = new JMenuItem(ASSIGNMENT_CREATE);
		popActivityStateMenu.add(popAssignmentMenuItemCreate);

		popActionMenu =  new JPopupMenu();
		popActionMenuItemDelete = new JMenuItem(ACTION_DELETE);
		popParameterMenuItemCreate = new JMenuItem(PARAMETER_CREATE);
		popMoveUpMenuItem = new JMenuItem(MOVE_UP_ACTION);
		popMoveDownMenuItem = new JMenuItem(MOVE_DOWN_ACTION);
		popActionMenu.add(popActionMenuItemDelete);
		popActionMenu.add(popParameterMenuItemCreate);
		popActionMenu.add(popMoveUpMenuItem);
		popActionMenu.add(popMoveDownMenuItem);
		
		popTransitionMenu = new JPopupMenu();
		popActionMenuItemCreate2 = new JMenuItem(ACTION_CREATE);
		popTransitionMenu.add(popActionMenuItemCreate2);
		
		popParameterMenu =  new JPopupMenu();
		popParameterMenuItemDelete = new JMenuItem(PARAMETER_DELETE);
		popParameterMenu.add(popParameterMenuItemDelete);

		popAssignmentMenu =  new JPopupMenu();
		popParameterMenuItemCreate1 = new JMenuItem(PARAMETER_CREATE);
		popAssignmentMenu.add(popParameterMenuItemCreate1);
		popAssignmentMenuItemDelete = new JMenuItem(ASSIGNMENT_DELETE);
		popAssignmentMenu.add(popAssignmentMenuItemDelete);

		popDecisionMenu =  new JPopupMenu();
		popParameterMenuItemCreate2 = new JMenuItem(PARAMETER_CREATE);
		popDecisionMenu.add(popParameterMenuItemCreate2);
		
		popFormatterMenu =  new JPopupMenu();
		popFormaterMenuItemDelete = new JMenuItem(FORMATER_DELETE);
		popParameterMenuItemCreate3 = new JMenuItem(PARAMETER_CREATE);
		popFormatterMenu.add(popFormaterMenuItemDelete);
		popFormatterMenu.add(popParameterMenuItemCreate3);

		if (controller instanceof MasterDetailCellController){
			popAttributeMenuItemDelete.addActionListener((MasterDetailCellController)controller);
			popAttributeMenuItemCreate.addActionListener((MasterDetailCellController)controller);
			popFieldMenuItemCreate.addActionListener((MasterDetailCellController)controller);
			popFieldMenuItemCreate2.addActionListener((MasterDetailCellController)controller);
			popFieldMenuItemDelete.addActionListener((MasterDetailCellController)controller);
			popFormaterMenuItemCreate.addActionListener((MasterDetailCellController)controller);
			popActionMenuItemCreate.addActionListener((MasterDetailCellController)controller);
			popActionMenuItemCreate2.addActionListener((MasterDetailCellController)controller);
			popActionMenuItemCreate3.addActionListener((MasterDetailCellController)controller);
			popActionMenuItemCreate4.addActionListener((MasterDetailCellController)controller);
			popActionMenuItemDelete.addActionListener((MasterDetailCellController)controller);
			popParameterMenuItemCreate.addActionListener((MasterDetailCellController)controller);
			popParameterMenuItemDelete.addActionListener((MasterDetailCellController)controller);
			popAssignmentMenuItemCreate.addActionListener((MasterDetailCellController)controller);
			popParameterMenuItemCreate1.addActionListener((MasterDetailCellController)controller);
			popMoveUpMenuItem.addActionListener((MasterDetailCellController)controller);
			popMoveDownMenuItem.addActionListener((MasterDetailCellController)controller);
			popParameterMenuItemCreate2.addActionListener((MasterDetailCellController)controller);
			popAssignmentMenuItemDelete.addActionListener((MasterDetailCellController)controller);
			popFormaterMenuItemDelete.addActionListener((MasterDetailCellController)controller);
			popParameterMenuItemCreate3.addActionListener((MasterDetailCellController)controller);
			popFieldMenuItemUp.addActionListener((MasterDetailCellController)controller);
			popFieldMenuItemDown.addActionListener((MasterDetailCellController)controller);
		}

	}
	
	/**
	 * @param filds
	 * @param list
	 */
	protected void createFieldSubTree(DefaultMutableTreeNode filds, List list) {
		Iterator it=list.iterator();
		while (it.hasNext()){
			FieldVO fild = (FieldVO)it.next();
			DefaultMutableTreeNode action = new DefaultMutableTreeNode(fild);
			filds.add(action);
			if (fild.getFormatter()!=null){
				createFormaterSubTree(action,fild.getFormatter());
			}
		}
	}

	/**
	 * @param action
	 * @param formaterVO
	 */
	private void createFormaterSubTree(DefaultMutableTreeNode action, FormatterVO formaterVO) {
		DefaultMutableTreeNode formater = new DefaultMutableTreeNode(formaterVO);
		action.add(formater);
		createParameterSubTree(formater,formaterVO.getParameterList());
		
	}

	/**
	 * @param actions
	 * @param list
	 */
	protected void createActionSubTree(DefaultMutableTreeNode actions, List list) {
		Iterator it = list.iterator();
		while (it.hasNext()){
			ActionVO actionVO = (ActionVO)it.next();
			DefaultMutableTreeNode action = new DefaultMutableTreeNode(actionVO);
			createParameterSubTree(action, actionVO.getParameterList());
			actions.add(action);
		}
	}

	/**
	 * @param list
	 * @param action
	 */
	protected void createParameterSubTree(DefaultMutableTreeNode action,List list) {
		Iterator it = list.iterator();
		while (it.hasNext()){
			ParameterVO parameterVO=(ParameterVO)it.next();
			DefaultMutableTreeNode parameter = new DefaultMutableTreeNode(parameterVO);
			action.add(parameter);
		}		
	}
	
	public void showPopMenu(Component c, int x, int y) {
		DefaultMutableTreeNode node =
			(DefaultMutableTreeNode) propertytree.getLastSelectedPathComponent();

		if (node != null) {
			if (node.getUserObject() instanceof AttributeVO){
				popAttributeMenu.show(c, x, y);
			} else 	if (node.getUserObject() instanceof ProcessDefinition){
				popProcessMenu.show(c, x, y);
			} else 	if (node.getUserObject() instanceof StartStateVO){
				popStartStateMenu.show(c, x, y);
			} else 	if (node.getUserObject() instanceof FieldVO){
				popFieldMenu.show(c, x, y);
			} else 	if (node.getUserObject() instanceof ActivityStateVO){
				popActivityStateMenu.show(c, x, y);
			} else 	if (node.getUserObject() instanceof ActionVO){
				popActionMenu.show(c, x, y);
			} else 	if (node.getUserObject() instanceof TransitionVO){
				popTransitionMenu.show(c, x, y);
			} else 	if (node.getUserObject() instanceof ParameterVO){
				popParameterMenu.show(c, x, y);
			} else 	if (node.getUserObject() instanceof AssignmentVO){
				popAssignmentMenu.show(c, x, y);
			} else 	if (node.getUserObject() instanceof DecisionVO){
				popDecisionMenu.show(c, x, y);				
			} else 	if (node.getUserObject() instanceof FormatterVO){
				popFormatterMenu.show(c, x, y);				
			}
		}
	}
	/**
	 * Expands all nodes in a JTree.
	 *
	 * @param tree      The JTree to expand.
	 * @param depth     The depth to which the tree should be expanded.  Zero
	 *                  will just expand the root node, a negative value will
	 *                  fully expand the tree, and a positive value will
	 *                  recursively expand the tree to that depth.
	 */
	protected void expandJTree (javax.swing.JTree tree, int depth)
	{
		javax.swing.tree.TreeModel model = tree.getModel();
		expandJTreeNode(tree, model, model.getRoot(), 0, depth);
	}


	/**
	 * Expands a given node in a JTree.
	 *
	 * @param tree      The JTree to expand.
	 * @param model     The TreeModel for tree.     
	 * @param node      The node within tree to expand.     
	 * @param row       The displayed row in tree that represents
	 *                  node.     
	 * @param depth     The depth to which the tree should be expanded. 
	 *                  Zero will just expand node, a negative
	 *                  value will fully expand the tree, and a positive
	 *                  value will recursively expand the tree to that
	 *                  depth relative to node.
	 */
	private int expandJTreeNode (javax.swing.JTree tree,
									   javax.swing.tree.TreeModel model,
									   Object node, int row, int depth)
	{
		if (node != null  &&  !model.isLeaf(node)) {
			tree.expandRow(row);
			if (depth != 0)
			{
				for (int index = 0;
					 row + 1 < tree.getRowCount()  &&  
								index < model.getChildCount(node);
					 index++)
				{
					row++;
					Object child = model.getChild(node, index);
					if (child == null)
						break;
					javax.swing.tree.TreePath path;
					while ((path = tree.getPathForRow(row)) != null  &&
							path.getLastPathComponent() != child)
						row++;
					if (path == null)
						break;
					row = expandJTreeNode(tree, model, child, row, depth - 1);
				}
			}
		}
		return row;
	} // expandJTreeNode()

}
