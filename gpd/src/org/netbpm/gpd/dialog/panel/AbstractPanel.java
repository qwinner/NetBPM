package org.netbpm.gpd.dialog.panel;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;

import org.netbpm.gpd.dialog.WidgetFactory;
import org.netbpm.gpd.dialog.controller.Controller;


public abstract class AbstractPanel extends JPanel {
	protected JPanel invisiblePanel = new JPanel();
	protected WidgetFactory wFac;
	protected int modus;
	
	private Controller controller;

	/**
	 * @param controller
	 */
	public AbstractPanel(Controller controller) {
		this(0,controller);
	}

	/**
	 * Add all listener automatically to the abstract controller. 
	 * If you like to handle events over write this method in our Panel.
	 */
	public final void addChangeListener(Controller listener, Container cont) {
		Component[] cmpAll = cont.getComponents();
		for (int i = 0; i < cmpAll.length; i++) {
			Component cmpCurrent = cmpAll[i];

			if (cmpCurrent instanceof JTree) {
				if (listener instanceof TreeSelectionListener){
					((JTree)cmpCurrent).addTreeSelectionListener((TreeSelectionListener)listener);
				}
				if (listener instanceof MouseListener){
					((JTree)cmpCurrent).addMouseListener((MouseListener)listener);
				}
			} else if (cmpCurrent instanceof Container) {
				addChangeListener(listener, (Container)cmpCurrent);
			}
		}
	}

	public AbstractPanel(int mode,Controller controller) {
		this.controller=controller;
		modus = mode;
		wFac = WidgetFactory.instance();
		
		invisiblePanel.setVisible(false);
		add(invisiblePanel);
	}
	
	public void buildGUI() {
		createComponents();
		arrangeComponents();
		addChangeListener(controller,this);		
	}
	
	protected abstract void createComponents();
	protected abstract void arrangeComponents();
	
	/**
	 * @return
	 */
	public Controller getController() {
		return controller;
	}

}
