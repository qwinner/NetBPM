package org.netbpm.gpd.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.netbpm.gpd.Configuration;
import org.netbpm.gpd.ExceptionHandler;
import org.netbpm.gpd.GpdGraph;
import org.netbpm.gpd.io.GpdConverter;
import org.netbpm.gpd.io.SimpleFileFilter;

public class ExportJpegAction extends AbstractAction{
	public static final String NAME_VALUE = "Save JPEG ...";

	public static final int MNEMONIC_CODE = KeyEvent.VK_S;

	/** The long description text of this action */
	 public static final String LONG_DESCRIPTION_VALUE = 
		 "Save JPEG";

	/** Short description for tool-tip */
	public static final String SHORT_DESCRIPTION_VALUE = "Save JPEG";

	private GpdGraph graph;
	
	public ExportJpegAction( GpdGraph graph )
	{
		super( NAME_VALUE );
        
		// Setup action parameters
		putValue( MNEMONIC_KEY, new Integer( MNEMONIC_CODE ));
		putValue( LONG_DESCRIPTION, LONG_DESCRIPTION_VALUE );
		putValue( SHORT_DESCRIPTION, SHORT_DESCRIPTION_VALUE );

		this.graph = graph;
		setEnabled( true );
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		JFileChooser jc = new JFileChooser();
		jc.setDialogTitle("Export File JPEG");
		jc.addChoosableFileFilter(new SimpleFileFilter("jpg","\" *.jpg \" Image Files"));
                String lastFile=(String)Configuration.getInstance().getProperty(Configuration.LAST_SELECTED_JPEG_FILE);
		if (lastFile!=null){
			jc.setSelectedFile(new File(lastFile));
		}
		int result = jc.showSaveDialog(graph);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = jc.getSelectedFile();
                        if ( file.getName().indexOf('.') < 0 )
                            file = new File(file+".jpg");
			export(file);
			Configuration.getInstance().setProperty(Configuration.LAST_SELECTED_JPEG_FILE,file.toString());
		}
		
	}

        protected void export(File file)
        {
                try {
                        BufferedImage img = GpdConverter.toImage(graph);
                        ImageIO.write(img, "jpeg".toLowerCase(),file);
                } catch (Exception e2) {
                        ExceptionHandler.getInstance().handleException(e2);
                }
        }
}
