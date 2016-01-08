package org.netbpm.gpd.action;

import javax.swing.*;

import org.netbpm.gpd.ExceptionHandler;
import org.netbpm.gpd.GpdGraph;
import org.netbpm.gpd.dialog.util.PrintUtilities;

import java.awt.*;
import java.awt.print.*;
import java.awt.event.*;

/**
 * Prints the current Diagram
 *
 * @author Ross Paul
 * @version $Revision: 1.1 $
 */

public class PrintAction extends AbstractAction
{

    /** The short descriptive name = "Print ..." */
    public static final String NAME_VALUE = "Print ...";
    
    /** The mnemonic keycode = KeyEvent.VK_P */
    public static final int MNEMONIC_CODE = KeyEvent.VK_P;

   /** The long description text of this action */
    public static final String LONG_DESCRIPTION_VALUE = 
        "Print Current Diagram";

    /** The keystroke key-code = java.awt.event.KeyEevnt.VK_P */
    public static final int KEY_STROKE_CODE = KeyEvent.VK_P;

    /** The keystroke modifier mask = Event.CTRL_MASK */
    public static final int KEY_STROKE_MODIFIERS = Event.CTRL_MASK;

    /** The icon for the toolbar */
    public final Icon SMALL_ICON_VALUE = 
	new ImageIcon(getClass().getClassLoader().getResource
		       ( "gif/print16.gif" ));

    /** Short description for tool-tip */
    public static final String SHORT_DESCRIPTION_VALUE = "Print";

    /** Page format to use for printing */
    public static PageFormat pageFormat;

    /** Handle to the singleton JDBV */
    private GpdGraph graph;

    /** 
     * Constructor takes an instance of JDBV.  Action is disabled whenever a 
     * diagram is not visible.
     */
    public PrintAction( GpdGraph graph )
    {
        super( NAME_VALUE );
        
        // Setup action parameters
        KeyStroke keyStroke = 
            KeyStroke.getKeyStroke( KEY_STROKE_CODE, KEY_STROKE_MODIFIERS );
        putValue( ACCELERATOR_KEY, keyStroke );
        putValue( MNEMONIC_KEY, new Integer( MNEMONIC_CODE ));
        putValue( LONG_DESCRIPTION, LONG_DESCRIPTION_VALUE );
        putValue( SMALL_ICON, SMALL_ICON_VALUE );
        putValue( SHORT_DESCRIPTION, SHORT_DESCRIPTION_VALUE );

		this.graph = graph;
		setEnabled( true );
    }

    /** 
     * Attempts to print when selected.
     */
    public void actionPerformed( ActionEvent e )
    {
		pageFormat = PrinterJob.getPrinterJob().defaultPage();
        PrinterJob printJob = PrinterJob.getPrinterJob();
        boolean gridVisible = graph.isGridVisible();
		graph.setGridVisible( false );
   
        double zoomScale = graph.getScale();
		graph.setScale( 1.0 );

        printJob.setPrintable( new PrintUtilities( graph ),
                               pageFormat );

        if (printJob.printDialog()){
            try { 
                printJob.print();
            } catch(PrinterException pe) {
				ExceptionHandler.getInstance().handleException(pe);
            } 
        }
		graph.setGridVisible( gridVisible );
		graph.setScale( zoomScale );
    }
}
