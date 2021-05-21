package turtleExt;

import uk.ac.leedsbeckett.oop.TurtleGraphics;
import helperFunctions.UtilityFuncs;

import java.util.ArrayList;
import java.util.Arrays;

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
	 * Methods within TurtleGraphics and TurtleSystem
	 */
	private ArrayList<Method> methods;

	/**
	 * TurtleSystem Object
	 */
	private static TurtleSystem ts = new TurtleSystem();

	/*
	 * Variety of useful functions
	 */
	protected UtilityFuncs utility;
	
	private TurtleUI tui;
	
	/**
	 * Default constructor Build UI and other required elements
	 */
	private TurtleSystem() {
		methods = buildMethodList();
		this.utility = new UtilityFuncs();
		System.out.println(this.isOpaque());
		tui = new TurtleUI(this);
		this.reset();
	}

	/**
	 * Return a TurtleSystem
	 * 
	 * @return TurtleSystem
	 */
	public static TurtleSystem getTurtle() {
		return ts;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	/**
	 * Overrides TurtleGraphics processCommand User input is handled to allow
	 * calling of methods from TurtleCommands Package.
	 * 
	 * @param command - String - The command given
	 */
	@Override
	public void processCommand(String command) {
		ArrayList<String> commands = new ArrayList<String>(Arrays.asList(command.split(" ")));
		String methodCall = commands.get(0);
		commands.remove(methodCall);
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters = utility.getValidParameterType(commands);

		ArrayList<Method> mA = this.getMatchedMethod(methods, methodCall, parameters);

		if (mA.size() != 0) {
			if (mA.size() == 1) {
				// Check if method has parameters. if none. invoke. Else check types and match
				// with command
				Method m = mA.get(0);
				int paramSize = m.getParameterCount();
				if (paramSize == parameters.size()) {
					if (paramSize >= 1) {
						if (isValidParamRange(methodCall, parameters)) {
							this.invokeMethod(paramSize, parameters, m);
						}
					} else {
						this.invokeMethod(0, new ArrayList<Object>(), m);
					}
				} else {
					JOptionPane.showMessageDialog(ts, "Please enter the correct parameters or provide none",
							"Invalid Parameter", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(ts,
					"The command: \"" + command + "\" is not valid. " + "Please enter a working command",
					"Invalid Command", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * TODO: Implement something... Overrides the current Turtle Graphic about
	 * method
	 */
	@Override
	public void about() {
		super.about();
		/*
		int x = this.getHeight() / 2;
		int width = this.getWidth();
		int start = 0;
		this.setTurtleSpeed(10);
		this.setyPos(x);

		System.out.println(this.getyPos());
		System.out.println(this.getxPos());
		this.setxPos(0);
		this.turnLeft(90);
		this.penDown();
		
		while(start < width) {
			this.forward(1);
			System.out.println(this.getyPos());
			super.repaint();
			start++;
		}
		this.penUp();*/
	}

	/**
	 * Overrides the default TurtleGraphics Circle Behaviour
	 * 
	 * @param radius - Int - The radius of the circle
	 */
	@Override
	public void circle(int radius) {
		int initialX = this.getxPos(), xp = this.getxPos();
		int initialY = this.getyPos(), yp = this.getyPos();
		this.setTurtleSpeed(5);
		
		int angle = 0;
		// Loop over 360 times and set the respective X/Y pos to correspond to cos and
		// sine Trigonometry
		this.penDown();
		
		while (angle <= 360) {
			this.setxPos(Math.round(Math.round(radius * Math.cos(angle))) + initialX);
			this.setyPos(Math.round(Math.round(radius * Math.sin(angle))) + initialY);
			this.turnRight(Math.round(Math.round(radius * Math.tan(angle))));
			this.forward(1);
			
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
		this.forward(distance);
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
	 * Resets the canvas and sets turtle position to the center of the screen
	 */
	@Override
	public void reset() {
		this.setxPos(this.getWidth() / 2);
		this.setyPos(this.getHeight() / 2);
		this.clear();
		this.direction = 90;
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
				if (!utility.verifyRGBRange(parameters)) {
					JOptionPane.showMessageDialog(ts, "One or more numbers not in RGB range (0-255)", "Not in range",
							JOptionPane.WARNING_MESSAGE);
					return false;
				}
			case "turnleft":
			case "turnright":
				if (!utility.withinAngleRange(parameters)) {
					JOptionPane.showMessageDialog(ts, "One or more numbers not in angle range (0-360)", "Not in range",
							JOptionPane.WARNING_MESSAGE);
					return false;
				}
			case "forward":
			case "backward":
				if (!utility.numberinGraphicsFrame(parameters, tui)) {
					JOptionPane
							.showMessageDialog(
									ts, "Make sure the number is between the canvas height and width ( "
											+ this.getHeight() + " " + this.getWidth() + ")",
									"Not in range", JOptionPane.WARNING_MESSAGE);
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
		Method[] tsm = this.getClass().getMethods(); // Returns all methods within this and child classes

		for (Method m : tsm) {
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
	private ArrayList<Method> getMatchedMethod(ArrayList<Method> ms, String command, ArrayList<Object> commands) {
		ArrayList<Method> returnMethods = new ArrayList<Method>();

		// Add all methods from TurtleSystem/ TurtleGraphics to method array list
		for (Method m : ms) {
			if (m.getName().toLowerCase().equals(command)) {
				if (commands.size() == m.getParameterCount()
						&& checkParametersMatchCommand(m.getParameters(), commands)) {
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
