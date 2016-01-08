/*
 * @(#)FormatFillColor.java	1.2 31.01.2003
 *
 * Copyright (C) 2003 sven.luzar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package org.netbpm.gpd.action;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;

import org.jgraph.graph.CellView;
import org.jgraph.graph.GraphConstants;
import org.netbpm.gpd.GpdGraph;
import org.netbpm.gpd.cell.ActivityCell;
import org.netbpm.gpd.cell.Comment;
import org.netbpm.gpd.cell.DecisionCell;
import org.netbpm.gpd.cell.StartCell;

/**
 * Action that sets the selections fill color 
 * using a color dialog.
 * 
 * @author sven.luzar modified
 * @version 1.0
 *
 */
public class FormatFillColor extends AbstractAction{
    
	/** The short descriptive name = "Print Preview  ..." */
	public static final String NAME_VALUE = "Fill shape of color ...";

	/** The mnemonic keycode = KeyEvent.VK_W */
	public static final int MNEMONIC_CODE = KeyEvent.VK_X;

	/** The long description text of this action */
	 public static final String LONG_DESCRIPTION_VALUE = 
		 "Fill the selected shape of color to chose";

	/** The icon for the toolbar */
	public final Icon SMALL_ICON_VALUE = 
	new ImageIcon(getClass().getClassLoader().getResource
			   ( "gif/fill.gif" ));

	/** Short description for tool-tip */
	public static final String SHORT_DESCRIPTION_VALUE = "Fill shape of color.";

	public final static Rectangle defaultBounds = new Rectangle(10, 10, 20, 20);

	/** Hashtable for properties. Initially empty */
	protected Map attributes = GraphConstants.createMap();

        private GpdGraph graphpad;

        /**
	 * Constructor for FormatFillColor.
	 * @param graphpad
	 * @param name
	 */
	public FormatFillColor(GpdGraph graph) {
		super( NAME_VALUE );
        
		// Setup action parameters
		putValue( MNEMONIC_KEY, new Integer( MNEMONIC_CODE ));
		putValue( LONG_DESCRIPTION, LONG_DESCRIPTION_VALUE );
		putValue( SMALL_ICON, SMALL_ICON_VALUE );
		putValue( SHORT_DESCRIPTION, SHORT_DESCRIPTION_VALUE );

		this.graphpad = graph;
		setEnabled( true );
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
            int selectCount = graphpad.getSelectionCount();
            if ( selectCount > 0 ) {
                Component component = graphpad.getComponent(0);
                Color value = JColorChooser.showDialog(component,"ColorDialog",null);
                if (value != null) {
                    Object[] obj = graphpad.getSelectionCells();
                    for (int i = 0; i < selectCount; i++) {
			Object objCell = graphpad.getModel().getRootAt(
                                graphpad.getModel().getIndexOfRoot(obj[i]));
                        if (objCell instanceof ActivityCell)
                            ((ActivityCell)objCell).getModel().setBackColor(value);
                        else if (objCell instanceof DecisionCell)
                            ((DecisionCell)objCell).getModel().setBackColor(value);
                        else if (objCell instanceof Comment)
                            ((Comment)objCell).getModel().setBackColor(value);
                        else if (objCell instanceof StartCell)
                            ((StartCell)objCell).getModel().setBackColor(value);
                        CellView view = graphpad.getGraphLayoutCache().getMapping(objCell, false);
                        view.update();
                    }
                }
            }
        }

}
