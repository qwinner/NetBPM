package org.netbpm.gpd.dialog.valuedialog.editor;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import org.netbpm.gpd.dialog.syntax.JEditTextArea;

public class CopyAction extends AbstractAction
{

    /** The short descriptive name = "Print ..." */
    public static final String NAME_VALUE = "Copy";
    
    /** The mnemonic keycode = KeyEvent.VK_P */
    public static final int MNEMONIC_CODE = KeyEvent.VK_C;

    /** The keystroke key-code = java.awt.event.KeyEevnt.VK_P */
    public static final int KEY_STROKE_CODE = KeyEvent.VK_C;

    /** The keystroke modifier mask = Event.CTRL_MASK */
    public static final int KEY_STROKE_MODIFIERS = Event.CTRL_MASK;

    /** The icon for the toolbar */
    public static final Icon SMALL_ICON_VALUE = 
        new ImageIcon( ClassLoader.getSystemResource
		       ( "gif/copy.gif" ));

    /** Short description for tool-tip */
    public static final String SHORT_DESCRIPTION_VALUE = "Copy";

    /** Page format to use for printing */
    public static PageFormat pageFormat;

    /** Handle to the singleton JDBV */
    private JEditTextArea testArea;

    /** 
     * Constructor takes an instance of JDBV.  Action is disabled whenever a 
     * diagram is not visible.
     */
    public CopyAction(JEditTextArea testArea )
    {
        super( NAME_VALUE );
        
        // Setup action parameters
        KeyStroke keyStroke = 
            KeyStroke.getKeyStroke( KEY_STROKE_CODE, KEY_STROKE_MODIFIERS );
        putValue( ACCELERATOR_KEY, keyStroke );
        putValue( MNEMONIC_KEY, new Integer( MNEMONIC_CODE ));
        putValue( SMALL_ICON, SMALL_ICON_VALUE );
        putValue( SHORT_DESCRIPTION, SHORT_DESCRIPTION_VALUE );

		this.testArea = testArea;
		setEnabled( true );
    }

    /** 
     * Attempts to print when selected.
     */
    public void actionPerformed( ActionEvent e )
    {
		testArea.copy();
    }
}
