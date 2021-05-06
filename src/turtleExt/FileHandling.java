package turtleExt;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Handles all IO operations for a TurtleGraphics Object
 * @author James Davis - c3576413
 *
 */
public class FileHandling {
	private File output;
	private FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
			"Image Files (*.jpg, *.png, *.gif, *.jpeg)", 
			"jpg", "png", "gif", "jpeg");
	
	/**
	 * Saves the current Turtle Graphic
	 * @param ts - TurtleSystem Object
	 */
	public void saveImage(TurtleSystem ts) {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(imageFilter);
		int returnVal = fc.showSaveDialog(ts);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				output = fc.getSelectedFile();
				BufferedImage bi = ts.getBufferedImage();
				ImageIO.write(bi, "png", output);				
			} catch (Exception e){
				JOptionPane.showMessageDialog(fc, e.getMessage());
			}
		}
	}
	
	/**
	 * Loads a Turtle Graphic
	 * @param TurtleSystem Object
	 */
	public void loadImage(TurtleSystem ts) {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(imageFilter);
		int returnVal = fc.showOpenDialog(fc);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				output = fc.getSelectedFile();
				BufferedImage bi = ImageIO.read(output);
				ts.setBufferedImage(bi);
			} catch (Exception e){
				JOptionPane.showMessageDialog(fc, e.getMessage());
			}
		}
	}
}
