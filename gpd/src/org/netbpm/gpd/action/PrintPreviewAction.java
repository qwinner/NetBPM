package org.netbpm.gpd.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.netbpm.gpd.GpdGraph;
import org.netbpm.gpd.dialog.util.PrintPreview;
import org.netbpm.gpd.dialog.util.PrintUtilities;

/**
 * Brings up a print preview frame
 *
 * @author Ross Paul
 * @version $Revision: 1.1 $
 */

public class PrintPreviewAction extends AbstractAction
{
    /** The short descriptive name = "Print Preview  ..." */
    public static final String NAME_VALUE = "Print Preview ...";
    
    /** The mnemonic keycode = KeyEvent.VK_W */
    public static final int MNEMONIC_CODE = KeyEvent.VK_W;

   /** The long description text of this action */
    public static final String LONG_DESCRIPTION_VALUE = 
        "View the Print Preview";

    /** The icon for the toolbar */
    public final Icon SMALL_ICON_VALUE = 
	new ImageIcon(getClass().getClassLoader().getResource
		       ( "gif/printPreview16.gif" ));

    /** Short description for tool-tip */
    public static final String SHORT_DESCRIPTION_VALUE = "Print Preview";

    private GpdGraph graph;

    /** 
     * Constructor takes an instance of JDBV.  Action is disabled whenever a 
     * diagram is not visible.
     */
    public PrintPreviewAction( GpdGraph graph )
    {
        super( NAME_VALUE );
        
        // Setup action parameters
        putValue( MNEMONIC_KEY, new Integer( MNEMONIC_CODE ));
        putValue( LONG_DESCRIPTION, LONG_DESCRIPTION_VALUE );
        putValue( SMALL_ICON, SMALL_ICON_VALUE );
        putValue( SHORT_DESCRIPTION, SHORT_DESCRIPTION_VALUE );

		this.graph = graph;
		setEnabled( true );
    }

    public void actionPerformed( ActionEvent e )
    {
        boolean gridVisible = graph.isGridVisible();
		graph.setGridVisible( false );

        new PrintPreview( new PrintUtilities( graph ),
                          "Print Preview: " + graph.getName(), 
                          PrintAction.pageFormat);

		graph.setGridVisible( gridVisible );
    }
}
