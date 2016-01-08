package org.netbpm.gpd.action;

import java.awt.print.*;
import java.awt.event.*;
import javax.swing.*;

import org.netbpm.gpd.GpdGraph;

/**
 * Brings up a dialog for setting the page format
 *
 * @author Ross Paul
 * @version $Revision: 1.1 $
 */

public class PageSetupAction extends AbstractAction
{
    /** The short descriptive name = "Page Setup ..." */
    public static final String NAME_VALUE = "Page Setup ...";
    
    /** The mnemonic keycode = KeyEvent.VK_G */
    public static final int MNEMONIC_CODE = KeyEvent.VK_G;

    /** The icon for the toolbar */
    public final Icon SMALL_ICON_VALUE = 
	new ImageIcon(getClass().getClassLoader().getResource
		       ( "gif/pageSetup16.gif" ));


   /** The long description text of this action */
    public static final String LONG_DESCRIPTION_VALUE = 
        "Set the page properties for printing";

    /** Short description for tool-tip */
    public static final String SHORT_DESCRIPTION_VALUE = "Page Setup";

    /** Handle to the singleton JDBV */
//    private GpdGraph graph;

    /** 
     * Constructor takes an instance of JDBV.  Action is disabled whenever a 
     * diagram is not visible.
     */
    public PageSetupAction( GpdGraph graph )
    {
        super( NAME_VALUE );
        
        putValue( LONG_DESCRIPTION, LONG_DESCRIPTION_VALUE );
        putValue( MNEMONIC_KEY, new Integer( MNEMONIC_CODE ));
        putValue( SMALL_ICON, SMALL_ICON_VALUE );
        putValue( SHORT_DESCRIPTION, SHORT_DESCRIPTION_VALUE );

//        this.graph = graph;
        setEnabled( true );
    }

    /** Brings up the print dialog */
    public void actionPerformed( ActionEvent e )
    {
        PrintAction.pageFormat = 
            PrinterJob.getPrinterJob().pageDialog( PrintAction.pageFormat );
    }
}
