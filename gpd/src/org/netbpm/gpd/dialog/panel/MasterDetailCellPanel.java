
package org.netbpm.gpd.dialog.panel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.netbpm.gpd.dialog.controller.Controller;
import org.netbpm.gpd.dialog.util.GpdSplitPane;

public abstract class MasterDetailCellPanel extends AbstractPanel {

	private JPanel south;
	private JPanel north;
	private GpdSplitPane mainPanel;

	/**
	 * @param controller
	 */
	public MasterDetailCellPanel(Controller controller) {
		super(controller);
		setLayout(new BorderLayout());
		north=new JPanel();
		north.setLayout(new BorderLayout());
		south=new JPanel();
		south.setLayout(new BorderLayout());
		mainPanel = new GpdSplitPane(JSplitPane.VERTICAL_SPLIT,north,new JScrollPane(south));
		add(mainPanel, BorderLayout.CENTER);
		buildGUI();
	}

	public void setMasterHeadline(Component c){
		north.add(c, BorderLayout.NORTH);
	}

	public void setMaster(Component c){
		north.add(c, BorderLayout.CENTER);
	}

	public void setDetail(Component c){
//		if (mainPanel.getRightComponent() instanceof AbstractPanel){
//			Controller southController = ((AbstractPanel)mainPanel.getRightComponent()).getController();
//			if (southController instanceof DataManipulationController){
//				((DataManipulationController)southController).saveChanges();	
//			}
//		}
		mainPanel.setRightComponent(c);
	}
	public Component getDetail(){
		return mainPanel.getRightComponent();
	}
}
