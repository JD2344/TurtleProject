package helperFunctions;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
	public ArrayList<Object> getValidParameterType(ArrayList<String> commands) {
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
	public boolean numberinGraphicsFrame(ArrayList<Object> parameters, TurtleUI ui) {
		for(Object o : parameters) {
			if (o instanceof Integer) {
				//If parameter is an integer and between the frame boundaries
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
	 * Verifies whether given input is within RGB colour range
	 * 
	 * @param parameters
	 * @return inRange - boolean
	 */
	public boolean verifyRGBRange(ArrayList<Object> parameters) {
		boolean inRange = false;
		for (Object o : parameters) {
			if (o instanceof Integer) {
				inRange = this.numberinRGBRange((int) o);
				if (!inRange) {
					return inRange;
				}
			} else {
				return false;
			}
		}
		return inRange;
	}

	/**
	 * Checks whether a given number is within an angle range of 0 - 360
	 * 
	 * @param parameters - The parameters to check
	 * @return true if in range
	 */
	public boolean withinAngleRange(ArrayList<Object> parameters) {
		boolean inRange = false;
		for (Object o : parameters) {
			if (o instanceof Integer) {
				if ((int) o >= 0 && (int) o <= 360) {
					inRange = true;
				}
			} else {
				return false;
			}
		}
		return inRange;
	}
	
	/**
	 * Displays JOptionPane to user about unsaved items
	 * @param tS - TurtleSystem Object
	 */
	public void saveConfirmation(TurtleSystem tS) {
		int action = JOptionPane.showConfirmDialog(tS, "The Current image is not saved. " 
				+ "Do you want to continue?", "Unsaved Image", JOptionPane.YES_NO_OPTION);
		if(action == JOptionPane.YES_OPTION) {
			tS.clear();
		}
	}
	
	/**
	 * Executes a method based from reflection data that has returned a method
	 * 
	 * @param paramSize  - Parameter size of a method
	 * @param parameters - Parameters specified by user input
	 * @param m          - the method to invoke
	 */
	public void invokeMethod(int paramSize, ArrayList<Object> parameters, Method m, TurtleSystem ts) {
		try {
			if (paramSize >= 1)
				m.invoke(ts, parameters.toArray());
			else
				m.invoke(ts, null);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ts, e.getMessage());
		}
	}
	
	/**
	 * Invoke Method but only for methods that have 1 integer parameter
	 * @param number
	 * @param m
	 * @param ts
	 */
	public void invokeMethod(int number, Method m, TurtleSystem ts) {
		try {
			m.invoke(ts, number);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ts, e.getMessage());
		}
	}
	
	/**
	 * Uses reflection to get a matching method based on a string command and the number of parameters
	 * 
	 * @param methods      - ArrayList<Method>
	 * @param Method Name - String method name in lowercase
	 * @return Method if found else null
	 */
	public ArrayList<Method> getMatchedMethod(ArrayList<Method> methods, String command, ArrayList<Object> parameters) {
		ArrayList<Method> returnMethods = new ArrayList<Method>();

		// Add all methods from TurtleSystem/ TurtleGraphics to method array list
		for (Method m : methods) {
			if (m.getName().toLowerCase().equals(command)) {
				if (parameters.size() == m.getParameterCount()
						&& checkParametersMatchCommand(m.getParameters(), parameters)) {
					returnMethods.add(m);
				}
			}
		}
		return returnMethods;
	}

	/**
	 * Checks a given parameter array if types match a corresponding array of
	 * strings DEFAULT: Returns false
	 * 
	 * @param ps       - Array of Parameters
	 * @param commands - Array of commands
	 * @return true if parameters match, else false
	 */
	private boolean checkParametersMatchCommand(Parameter[] ps, ArrayList<Object> commands) {
		boolean doMatch = false;
		// if method has parameters
		if (ps.length >= 1) {
			// Check each parameters types in comparison to commands given
			for (Parameter parameter : ps) {
				String wrappedClass = toWrapper(parameter.getType()).getName();
				for (Object command : commands) {
					doMatch = wrappedClass == command.getClass().getTypeName();
					if (!doMatch) {
						return doMatch;
					}
				}
			}
		} else {
			doMatch = true; // Return true if no parameters are in method
		}
		return doMatch;
	}

	/**
	 * If class is not primitive return the corresponding class type
	 * 
	 * @param clazz - Generic<?> the class to be checked
	 * @return Corresponding Wrapper Object of the primitive type
	 */
	private Class<?> toWrapper(Class<?> clazz) {
		if (!clazz.isPrimitive())
			return clazz;

		if (clazz == Integer.TYPE)
			return Integer.class;
		if (clazz == Long.TYPE)
			return Long.class;
		if (clazz == Boolean.TYPE)
			return Boolean.class;
		if (clazz == Byte.TYPE)
			return Byte.class;
		if (clazz == Character.TYPE)
			return Character.class;
		if (clazz == Float.TYPE)
			return Float.class;
		if (clazz == Double.TYPE)
			return Double.class;
		if (clazz == Short.TYPE)
			return Short.class;
		if (clazz == Void.TYPE)
			return Void.class;

		return clazz;
	}
}