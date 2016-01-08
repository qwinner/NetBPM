package org.netbpm.gpd.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.netbpm.gpd.GpdGraph;
import org.netbpm.gpd.GpdGraphModel;

public class NewAction extends AbstractAction{
	/** The short descriptive name = "tilt graph" */
	public static final String NAME_VALUE = "new";

	/** The long description text of this action */
	 public static final String LONG_DESCRIPTION_VALUE = 
		 "new graph";

	/** Short description for tool-tip */
	public static final String SHORT_DESCRIPTION_VALUE = "new graph";

	private GpdGraph graph;
	
	public NewAction( GpdGraph graph )
	{
		super( NAME_VALUE );
        
		// Setup action parameters
		putValue( LONG_DESCRIPTION, LONG_DESCRIPTION_VALUE );
		putValue( SHORT_DESCRIPTION, SHORT_DESCRIPTION_VALUE );

		this.graph = graph;
		setEnabled( true );
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		graph.setModel(new GpdGraphModel());
	}
}
