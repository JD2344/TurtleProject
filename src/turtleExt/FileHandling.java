package turtleExt;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
	private TurtleSystem tS;
	private JFrame mainFrame;
	
	public FileHandling(JFrame ui, TurtleSystem ts) {
		mainFrame = ui;
		tS = ts;
	}
	/**
	 * Saves the current Turtle Graphic
	 * @param ts - TurtleSystem Object
	 */
	protected void saveImage() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(imageFilter);
		int returnVal = fc.showSaveDialog(tS);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				output = fc.getSelectedFile();
				String fileName = output.getName();
				String[] writerNames = ImageIO.getWriterFormatNames();
				Optional<String> o = Arrays.stream(writerNames).filter(fileName::contains).findAny();
				BufferedImage bi = tS.getBufferedImage();
				if(!o.isEmpty()) {
					boolean hasWriter = ImageIO.write(bi, o.get(), output);
					
					if(hasWriter) {
						mainFrame.setTitle("TurtleGraphics - " + fileName);
					}
				}
			} catch (Exception e){
				JOptionPane.showMessageDialog(fc, e.getMessage());
			}
		}
	}
	
	/**
	 * Loads a Turtle Graphic
	 * @param TurtleSystem Object
	 */
	protected void loadImage() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(imageFilter);
		int returnVal = fc.showOpenDialog(fc);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				output = fc.getSelectedFile();
				BufferedImage bi = ImageIO.read(output);
				tS.setBufferedImage(bi);
				mainFrame.setTitle("TurtleGraphics - " + output.getName());
			} catch (Exception e){
				JOptionPane.showMessageDialog(fc, e.getMessage());
			}
		}
	}
}
