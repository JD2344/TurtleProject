package helperFunctions;

import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import turtleExt.TurtleSystem;

/**
 * Utility Library for useful Functions
 * 
 * @author James Davis - c3576413
 *
 */
public class UtilityFuncs {
	/**
	 * Checks whether a specified string can be parsed as an integer
	 * 
	 * @param value to check - String
	 * @return Int or null if not a number
	 */
	public Integer parseIntOrNull(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
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

	public void executeFunction(Method m, TurtleSystem ts) {
		try {
			m.invoke(ts, null);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ts, e.getMessage());
		}
	}

	public void executeFunction(Method m, TurtleSystem ts, int num) {
		try {
			m.invoke(ts, num);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ts, e.getMessage());
		}
	}
}