package org.netbpm.gpd.dialog.util;

import java.awt.Component;

import javax.swing.JSplitPane;


/**
 * One Layer between the JSplitPane 
 * and our implementation. 
 * Currently we add a load and store 
 * management for the divider position. 
 * 
 * 
 * @author sven_luzar
 * @version 1.0
 * 
 */
public class GpdSplitPane extends JSplitPane {

	/** Calls the super constructor
	 *  and adds the instance to the position manager
	 * 
	 */
	public GpdSplitPane() {
		super();
		PositionManager.addComponent(this);
	}

	/** Calls the super constructor
	 *  and adds the instance to the position manager
	 * 
	 * @param newOrientation
	 */
	public GpdSplitPane(int newOrientation) {
		super(newOrientation);
		PositionManager.addComponent(this);
	}

	/** Calls the super constructor
	 *  and adds the instance to the position manager
	 * 
	 * @param newOrientation
	 * @param newContinuousLayout
	 */
	public GpdSplitPane(int newOrientation, boolean newContinuousLayout) {
		super(newOrientation, newContinuousLayout);
		PositionManager.addComponent(this);
	}

	/** Calls the super constructor
	 *  and adds the instance to the position manager
	 * 
	 * @param newOrientation
	 * @param newLeftComponent
	 * @param newRightComponent
	 */
	public GpdSplitPane(
		int newOrientation,
		Component newLeftComponent,
		Component newRightComponent) {
		super(newOrientation, newLeftComponent, newRightComponent);
		PositionManager.addComponent(this);
	}

	/** Calls the super constructor
	 *  and adds the instance to the position manager
	 * 
	 * @param newOrientation
	 * @param newContinuousLayout
	 * @param newLeftComponent
	 * @param newRightComponent
	 */
	public GpdSplitPane(
		int newOrientation,
		boolean newContinuousLayout,
		Component newLeftComponent,
		Component newRightComponent) {
		super(
			newOrientation,
			newContinuousLayout,
			newLeftComponent,
			newRightComponent);
			PositionManager.addComponent(this);
	}

	/** Removes the Split Pane from the 
	 *  position manager and calls
	 *  the super implementation. 
	 *  
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		PositionManager.removeComponent(this);
		super.finalize();
	}

	/** Calls the super implementation
	 *  and makes an update for the
	 *  component by using the locale
	 *  change adapter and the 
	 *  position manager.
	 *  
	 *  @param name the new name
	 *  @see PositionManager#updateComponent(Component)
	 *  @see LocaleChangeAdapter#updateComponent(Component)
	 *  @see java.awt.Component#setName(java.lang.String)
	 * 
	 */
	public void setName(String name) {
		super.setName(name);
		PositionManager.updateComponent(this);
	}
}
