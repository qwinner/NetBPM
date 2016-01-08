package org.netbpm.gpd.dialog.util;

import java.awt.*;
import javax.swing.*;

import org.netbpm.gpd.ExceptionHandler;

import java.awt.print.*;

/** A simple utility class that lets you very simply print
 *  an arbitrary component. Just pass the component to the
 *  PrintUtilities.printComponent. The component you want to
 *  print doesn't need a print method and doesn't have to
 *  implement any interface or do anything special at all.
 *  <P>
 *  If you are going to be printing many times, it is marginally more 
 *  efficient to first do the following:
 *  <PRE>
 *    PrintUtilities printHelper = new PrintUtilities(theComponent);
 *  </PRE>
 *  then later do printHelper.print(). But this is a very tiny
 *  difference, so in most cases just do the simpler
 *  PrintUtilities.printComponent(componentToBePrinted).
 *
 *  7/99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 *  May be freely used or adapted.
 * <p>
 * This class has been modified to allow for multi-page printing.
 * @author Ross Paul
 * @version $Revision: 1.1 $
 */

public class PrintUtilities implements Printable {
  private Component componentToBePrinted;

    private int maxNumPages = 1;
    private Image image;

  public static void printComponent(Component c) {
    new PrintUtilities(c).print();
  }
  
  public PrintUtilities(Component componentToBePrinted) {
    this.componentToBePrinted = componentToBePrinted;
    Dimension size = componentToBePrinted.getSize();
    int w = (int)size.getWidth();
    int h = (int)size.getHeight();
    image = (Image)componentToBePrinted.createImage(w, h);
    Graphics2D g = (Graphics2D)image.getGraphics();
    componentToBePrinted.paint( g );
  }
  
  public void print() {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    printJob.setPrintable(this);
    if (printJob.printDialog())
      try {
        printJob.print();
      } catch(PrinterException pe) {
      	ExceptionHandler.getInstance().handleException(pe);
      }
  }

    /** Print is overridden to provide superior control and options */
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex >= maxNumPages || componentToBePrinted == null) {
            return(NO_SUCH_PAGE);
        } else 
        {
            Graphics2D g2d = (Graphics2D)g;
            //accounts for the margins
            g2d.translate(pageFormat.getImageableX(), 
                          pageFormat.getImageableY());
            disableDoubleBuffering(componentToBePrinted);
            
            int wPage = (int) pageFormat.getImageableWidth();
            int hPage = (int) pageFormat.getImageableHeight();
      
            int w = image.getWidth( componentToBePrinted );
            int h = image.getHeight( componentToBePrinted );
            if( w == 0 || h == 0 )
                return NO_SUCH_PAGE;
            
            int nCol = Math.max( (int)Math.ceil( (double)w/wPage ), 1);
            int nRow = Math.max( (int)Math.ceil( (double)h/hPage ), 1);
            maxNumPages = nCol*nRow;

            int iCol = pageIndex % nCol;
            int iRow = pageIndex / nCol;

            int x = iCol * wPage;
            int y = iRow * hPage;

            int wImage = Math.min( wPage, w-x );
            int hImage = Math.min( hPage, h-y );

            g2d.drawImage( image, 0, 0, wImage, hImage,
                           x, y, x+wImage, y+hImage, componentToBePrinted );
            System.gc();

            enableDoubleBuffering(componentToBePrinted);
            return(PAGE_EXISTS);
        }
    }

  /** The speed and quality of printing suffers dramatically if
   *  any of the containers have double buffering turned on.
   *  So this turns if off globally.
   *  @see enableDoubleBuffering
   */
  public static void disableDoubleBuffering(Component c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(false);
  }

  /** Re-enables double buffering globally. */
  
  public static void enableDoubleBuffering(Component c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(true);
  }

    public class TestFrame extends JFrame
    {
        public TestFrame( Image image ){
            JPanel foo = new JPanel();
            foo.add(new JLabel(new ImageIcon(image)));
            foo.add(new JLabel("this work now?"));
            this.setContentPane(foo);
            pack();
        }
    }
}
