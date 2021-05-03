package helperFunctions;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import turtleExt.TurtleSystem;
import turtleExt.TurtleUI;

/**
 * Utility Library for useful Functions
 * 
 * @author James Davis - c3576413
 *
 */
public class UtilityFuncs {
	/**
	 * Looks at array of strings and returns an int or the initial string
	 * @param commands - ArrayList<String>
	 * @return ArrayList<Object> - Array of objects (String or Int)
	 */
	public ArrayList<Object> formatInput(ArrayList<String> commands) {
		ArrayList<Object> o = new ArrayList<Object>();
		for(String s : commands) {
			if(s.matches("\\d+")) {
				o.add(Integer.valueOf(s));
			} else {
				o.add(s);
			}
		}
		return o;
	}
	
	/**
	 * checks if integer is within RGB range
	 * 
	 * @param num
	 * @return boolean
	 */
	public boolean numberinRGBRange(int num) {
		if (num >= 0 && num <= 255) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Validates the parameters to make sure they are Postive and within the frames boundaries.
	 * @param parameters - ArrayList<Object> The parameters to be checked
	 * @param ui - TurtleUI - The UI elements associated
	 * @return
	 */
	public boolean verifyNumbers(ArrayList<Object> parameters, TurtleUI ui) {
		for(Object o : parameters) {
			if (o.getClass().getTypeName() == Integer.class.getTypeName()) {
				if((int)o >= 0 && ((int)o <= 
						(ui.mainFrame.getHeight() > ui.mainFrame.getWidth() ? 
								ui.mainFrame.getHeight(): ui.mainFrame.getWidth()))) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Displays JOptionPane to user about unsaved items
	 * @param tS - TurtleSystem Object
	 */
	public void saveConfirmation(TurtleSystem tS) {
		int action = JOptionPane.showConfirmDialog(null, "The Current image is not saved. " 
				+ "Do you want to continue?", "Unsaved Image", JOptionPane.YES_NO_OPTION);
		if(action == JOptionPane.YES_OPTION) {
			tS.clear();
		}
	}
}