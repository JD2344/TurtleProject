package turtleExt;

import uk.ac.leedsbeckett.oop.TurtleGraphics;
import helperFunctions.UtilityFuncs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import java.lang.reflect.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Implements TurtleGraphics and renders Turtle Objects and Sprite.
 * 
 * @author James Davis - c3576413
 */
public class TurtleSystem extends TurtleGraphics {
	/**
	 * Generate serialVersionUID
	 */
	private static final long serialVersionUID = -24842294727668755L;

	/**
	 * Graphics Context
	 */
	private Graphics gc;

	/**
	 * Methods within TurtleGraphics and TurtleSystem
	 */
	private ArrayList<Method> methods;

	/*
	 * Variety of useful functions
	 */
	private UtilityFuncs utility = new UtilityFuncs();

	/**
	 * TurtleSystem Object
	 */
	private static TurtleSystem ts = new TurtleSystem();

	/**
	 * Create UI object
	 */
	private TurtleUI ui;

	/**
	 * Default constructor Build UI and other required elements
	 */
	private TurtleSystem() {
		gc = this.getGraphicsContext();
		ui = new TurtleUI(this);
		methods = buildMethodList();
	}

	/**
	 * Return a TurtleSystem
	 * 
	 * @return TurtleSystem
	 */
	public static TurtleSystem getTurtle() {
		return ts;
	}

	/**
	 * Overrides TurtleGraphics processCommand User input is handled to allow
	 * calling of methods from TurtleCommands Package.
	 */
	@Override
	public void processCommand(String command) {
		ArrayList<String> commands = new ArrayList<String>(Arrays.asList(command.split(" ")));
		String methodCall = commands.get(0);
		commands.remove(methodCall);
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters = utility.formatInput(commands);

		ArrayList<Method> mA = this.getMatchedMethod(methods, methodCall);
		if (mA.size() != 0) {
			if (isValidParamRange(methodCall, parameters)) {
				if (mA.size() > 1) {
					for (Method m : mA) {
						int paramSize = m.getParameterCount();

						if (parameters.size() > paramSize || parameters.size() < paramSize) {
							continue;
						} else {
							// If all parameters dont match. skip method. else invoke
							if (!checkParametersMatchCommand(m.getParameters(), parameters)) {
								continue;
							} else {
								this.invokeMethod(paramSize, parameters, m);
							}
						}
					}
				} else if (mA.size() == 1) {
					// Check if method has parameters. if none. invoke. Else check types and match
					// with command
					Method m = mA.get(0);
					int paramSize = m.getParameterCount();

					if (paramSize >= 1) {
						if (checkParametersMatchCommand(m.getParameters(), parameters)) {
							this.invokeMethod(paramSize, parameters, m);
						}
					} else {
						this.invokeMethod(0, new ArrayList<Object>(), m);
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(ts,
					"The command: " + command + " is not valid. " + "Please enter a working command");
		}
	}

	/**
	 * TODO: Implement something... Overrides the current Turtle Graphic about
	 * method
	 */

	@Override
	public void about() {
		this.penDown();
		this.forward(100);
		this.turnLeft();
		this.forward(100);
	}

	/**
	 * Overrides the default TurtleGraphics Circle Behaviour
	 */
	@Override
	public void circle(int radius) {
		double angle = 0;
		int initialX = this.getxPos(), xp = this.getxPos();
		int initialY = this.getyPos(), yp = this.getyPos();

		// Loop over 360 times and set the respective X/Y pos to correspond to cos and
		// sine Trigonometry
		this.penDown();
		while (angle <= 360) {
			this.forward(1);
			this.setxPos(Math.round(Math.round(radius * Math.cos(angle))) + initialX);
			this.setyPos(Math.round(Math.round(radius * Math.sin(angle))) + initialY);
			angle++;
		}
		this.penUp();
		this.setxPos(xp);
		this.setyPos(yp);
	}

	/**
	 * Allows a custom colour to be used with RGB values.
	 * 
	 * @param r = Red Value as integer
	 * @param g = Green Value as integer
	 * @param b = Blue Value as integer
	 */
	public void customColour(int r, int g, int b) {
		this.setPenColour(new Color(r, g, b));
	}

	/**
	 * Allows the turtle to move backwards
	 * 
	 * @param distance - int
	 */
	public void backward(int distance) {
		this.turnLeft(180);
		this.forward(-distance);
	}

	/**
	 * Clears the display
	 */
	public void New() {
		utility.saveConfirmation(ts);
	}

	/*
	 * Set pen colour to black
	 */
	public void black() {
		this.setPenColour(Color.BLACK);
	}

	/**
	 * Set pen colour to green
	 */
	public void green() {
		this.setPenColour(Color.GREEN);
	}

	/**
	 * Set pen colour to red
	 */
	public void red() {
		this.setPenColour(Color.RED);
	}

	/**
	 * Set pen colour to white
	 */
	public void white() {
		this.setPenColour(Color.WHITE);
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
			if (o.getClass().getTypeName() == Integer.class.getTypeName()) {
				inRange = utility.numberinRGBRange((int) o);
				if (!inRange) {
					return inRange;
				}
			}
		}
		return inRange;
	}
	
	/**
	 * Checks whether a given number is within an angle range of 0 - 360
	 * @param parameters - The parameters to check
	 * @return true if in range
	 */
	private boolean withinAngleRange(ArrayList<Object> parameters) {
		for(Object o : parameters) {
			if (o.getClass().getTypeName() == Integer.class.getTypeName()) {
				if((int)o >= 0 && (int)o <= 360) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks a call has a valid number range.
	 * 
	 * @param methodCall - String name of the method to be called
	 * @param parameters - ArrayList<Object> The parameters provided
	 * @return
	 */
	private boolean isValidParamRange(String methodCall, ArrayList<Object> parameters) {
		if (parameters.size() >= 1) {
			switch (methodCall) {
				case "customcolour":
					if (!verifyRGBRange(parameters)) {
						JOptionPane.showMessageDialog(ts, "One or more numbers not in RGB range (0-255)");
						return false;
					}
				case "turnleft":
				case "turnright":
					if(!withinAngleRange(parameters)) {
						JOptionPane.showMessageDialog(ts, "One or more numbers not in angle range (0-360)");
						return false;
					}
				default:
					if (!utility.verifyNumbers(parameters, this.ui)) {
						JOptionPane.showMessageDialog(ts,
								"The parameter entered is either" + "not in range or is not a valid number");
						return false;
					}
			}
		}
		return true;
	}

	/**
	 * Executes a method based from reflection data that has returned a method
	 * 
	 * @param paramSize  - Parameter size of a method
	 * @param parameters - Parameters specified by user input
	 * @param m          - the method to invoke
	 */
	private void invokeMethod(int paramSize, ArrayList<Object> parameters, Method m) {
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
	 * Builds array list of methods from this class and TurtleGraphics
	 * 
	 * @return ArrayList<Method> - Methods Returned
	 */
	private ArrayList<Method> buildMethodList() {
		ArrayList<Method> ml = new ArrayList<Method>();
		Method[] tsm = this.getClass().getMethods();

		for (Method m : tsm) {
			// System.out.println(m.getName() + " declarer: " +
			// m.getDeclaringClass().getSimpleName());
			ml.add(m);
		}
		return ml;
	}

	/**
	 * Uses reflection to get a matching method based on a string pattern
	 * 
	 * @param ms      - ArrayList<Method>
	 * @param command - String command in lowercase
	 * @return Method if found else null
	 */
	private ArrayList<Method> getMatchedMethod(ArrayList<Method> ms, String command) {
		ArrayList<Method> returnMethods = new ArrayList<Method>();

		// Add all methods from TurtleSystem/ TurtleGraphics to method array list
		for (Method m : ms) {
			if (m.getName().toLowerCase().equals(command)) {
				returnMethods.add(m);
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
		// if method has parameters
		if (ps.length >= 1) {
			// Check each parameters types in comparison to commands given
			for (Parameter parameter : ps) {
				String isWrapped = toWrapper(parameter.getType()).getName();
				for (Object command : commands) {
					if (isWrapped == command.getClass().getTypeName()) 
						return true;
					 else 
						return false;
				}
			}
		} else {
			return true; // Return true if no parameters are in method
		}
		return false;
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
