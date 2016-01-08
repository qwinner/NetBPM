package org.netbpm.gpd.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.netbpm.gpd.GpdGraph;
import org.netbpm.gpd.tools.GpdGraphTools;

public class TiltAction extends AbstractAction{
	/** The short descriptive name = "tilt graph" */
	public static final String NAME_VALUE = "tilt";

	/** The long description text of this action */
	 public static final String LONG_DESCRIPTION_VALUE = 
		 "tilt graph";

	/** The icon for the toolbar */
	public final Icon SMALL_ICON_VALUE = 
		new ImageIcon(getClass().getClassLoader().getResource
			   ( "gif/tilt.gif" ));

	/** Short description for tool-tip */
	public static final String SHORT_DESCRIPTION_VALUE = "tilt graph";

	private GpdGraph graph;
	
	public TiltAction( GpdGraph graph )
	{
		super( NAME_VALUE );
        
		// Setup action parameters
		putValue( LONG_DESCRIPTION, LONG_DESCRIPTION_VALUE );
		putValue( SMALL_ICON, SMALL_ICON_VALUE );
		putValue( SHORT_DESCRIPTION, SHORT_DESCRIPTION_VALUE );

		this.graph = graph;
		setEnabled( true );
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		GpdGraphTools.layout(graph);
	}
}
