package org.netbpm.gpd.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import org.netbpm.gpd.Configuration;
import org.netbpm.gpd.GpdGraph;
import org.netbpm.gpd.io.SimpleFileFilter;
import org.netbpm.gpd.tools.classinspector.ServiceInspector;

public class ExportNetBPMFile extends AbstractAction{
	/** The short descriptive name = "Print Preview  ..." */
	public static final String NAME_VALUE = "export file par ...";

	/** The mnemonic keycode = KeyEvent.VK_W */
	public static final int MNEMONIC_CODE = KeyEvent.VK_X;

	/** The long description text of this action */
	 public static final String LONG_DESCRIPTION_VALUE = 
		 "Export to JBPM par";

	/** The icon for the toolbar */
	public final Icon SMALL_ICON_VALUE = 
	new ImageIcon(getClass().getClassLoader().getResource
			   ( "gif/export.gif" ));

	/** Short description for tool-tip */
	public static final String SHORT_DESCRIPTION_VALUE = "Export to JBPM par";

	private GpdGraph graph;
	
	public ExportNetBPMFile(GpdGraph graph)
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

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		JFileChooser jc = new JFileChooser();
		jc.setDialogTitle("Export File .par");
                jc.addChoosableFileFilter(new SimpleFileFilter("par","\" *.par \" NetBPM Files"));
                String lastFile=(String)Configuration.getInstance().getProperty(Configuration.LAST_SELECTED_PROCESSEXPORT_FILE);
		if (lastFile!=null){
			jc.setSelectedFile(new File(lastFile));
		}
		int result = jc.showSaveDialog(graph);
		if (result == JFileChooser.APPROVE_OPTION && jc.getSelectedFile()!=null) {
			File file = jc.getSelectedFile();
                        if ( file.getName().indexOf('.') < 0 )
                                file = new File(file+".par");
                        File dir = new File( file.getPath().substring( 0, file.getPath().lastIndexOf('.') ) );
                        //if ( dir.isDirectory() ) dir.delete();
                        File dirWeb = new File(dir+File.separator+"web");
                        if (!dirWeb.exists()) dirWeb.mkdirs();
                        
                        ExportJpegAction jpegAction = new ExportJpegAction(graph);
                        jpegAction.export(new File(dirWeb+File.separator+dir.getName()+".jpg"));
                        ExportNetBPMWebAction jbpmWeb = new ExportNetBPMWebAction(graph);
                        jbpmWeb.export(new File(dirWeb+File.separator+"webinterface.xml"),dir.getName()+".jpg");
                        
                        ExportNetBPMAction jbpmAction = new ExportNetBPMAction(graph);
                        jbpmAction.export(new File(dir+File.separator+"processdefinition.xml"));

                        Iterator it=jbpmAction.getClasses(graph).iterator();
                        while (it.hasNext())
                                ServiceInspector.getServiceInspector().getTheClass(
                                        (String)it.next(), 
                                        dir.toString().concat(File.separator+"class") );

                        export(file);
			Configuration.getInstance().setProperty(Configuration.LAST_SELECTED_PROCESSEXPORT_FILE,file.toString());
		}
	}
        
        protected void export(File file)
        {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                ZipOutputStream zos = new ZipOutputStream(fos);
                if ( file.getName().indexOf('.') > 0 )
                    file = new File( file.getPath().substring( 0, file.getPath().lastIndexOf('.') ) );
                exportDir(zos,file,null);
                zos.close();
                fos.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
        
        private void exportDir( ZipOutputStream zos, File file, String dir )
        {
            try {
                byte[] buffer=new byte[4096];
                if (file.isDirectory()) {
                    File[] files = ( file ).listFiles();
                    for (int i=0; i<files.length; i++) {
                        String directory = dir;
                        if (files[i].isDirectory())
                            directory = (dir==null)?files[i].getName():dir+File.separator+files[i].getName();
                        exportDir(zos,files[i],directory);
                    }
                } else {
                    String fileName = (dir==null)?file.getName():dir+File.separator+file.getName();
                    ZipEntry ze = new ZipEntry(fileName);
                    zos.putNextEntry(ze);
                    FileInputStream fis = new FileInputStream(file);
                    int sz;
                    while ( (sz=fis.read(buffer,0,buffer.length)) > -1 )
                        zos.write(buffer,0,sz);
                    fis.close();
                }
            } catch(Exception e) { e.printStackTrace(); }
        }
}
