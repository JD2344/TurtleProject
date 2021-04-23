package turtleExt;

import uk.ac.leedsbeckett.oop.TurtleGraphics;
import helperFunctions.UtilityFuncs;

import java.util.ArrayList;
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
	 * Globally allow access to the graphics context
	 */
	@SuppressWarnings("unused")
	private Graphics gc;

	/**
	 * Method array made up of Methods within TurtleSystem class
	 */
	private ArrayList<Method> methods;
	
	private UtilityFuncs utility = new UtilityFuncs();

	/**
	 * Make a singleton of turtle, render a UI for said turtle.
	 * 
	 */
	private static TurtleSystem ts = new TurtleSystem();

	/**
	 * Default constructor
	 */
	private TurtleSystem() {
		gc = this.getGraphicsContext();
		new TurtleUI(this);
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
		System.out.println(methods.size());
		command.toLowerCase();
		// Split the command and potential number coinciding
		Pattern findNum = Pattern.compile("\\d+"); // match any digit
		Pattern getCommand = Pattern.compile("[a-zA-Z]+"); // match any of chars
		Matcher comFound = getCommand.matcher(command);
		Matcher numFound = findNum.matcher(command);

		if (comFound.find()) {
			System.out.println("Command Entered: " + command);
			Method run = this.getMatchedMethod(methods, comFound.group(0));
			if (run != null) {
				System.out.println("FOUND IT HERE");
				// TODO: Check for parameters... Then call respective method.
				// Make Sure that digit provided takes into max BOUND of degrees (e.g. 360)
				// Disregard for distance if it toggles a distance...
				// MUST REPORT invalid commands
				// CORRECTLY BIND PARAMETERS (Minus values not acceptable). Report errors
				if (numFound.find()) {
					int number = utility.parseIntOrNull(numFound.toMatchResult().group());
					switch (run.getName()) {
					case "turnleft":
						utility.executeFunction(run, ts, number);
						break;
					case "turnright":
						utility.executeFunction(run, ts, number);
						break;
					case "forward":
						utility.executeFunction(run, ts, number);
						break;
					case "backward":
						utility.executeFunction(run, ts, number);
						break;
					}
				} else {
					utility.executeFunction(run, ts);
				}
			} else {
				JOptionPane.showMessageDialog(ts, "Please enter a valid command");
			}
		}
	}

	/**
	 * TODO: Implement something... Overrides the current Turtle Graphic about
	 * method
	 */
	// @Override
	// public void about() {

	// }

	/**
	 * Overrides the default TurtleGraphics Circle Behaviour
	 */
	@Override
	public void circle(int radius) {
		float angle = 1;
		int initialX = this.getxPos(), xp = this.getxPos();
		int initialY = this.getyPos(), yp = this.getyPos();
		System.out.println(xp + " " + yp);
		this.setTurtleSpeed(50);
		// Loop over 360 times and set the respective X/Y pos to correspond to cos and
		// sine Trigonometry
		while (angle <= 360) {
			this.forward(1);
			this.penDown();
			this.setxPos(Math.round(Math.round(radius * Math.cos(angle))) + initialX);
			this.setyPos(Math.round(Math.round(radius * Math.sin(angle))) + initialY);
			// this.turnRight(Math.round(Math.round(radius * Math.tan(angle))));
			this.turnLeft(Math.round(Math.round(radius * Math.cos(angle))));
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
	 * Builds array list of methods from this class and TurtleGraphics
	 * 
	 * @return ArrayList<Method> - Methods Returned
	 */
	private ArrayList<Method> buildMethodList() {
		ArrayList<Method> ml = new ArrayList<Method>();
		Method[] tsm = this.getClass().getDeclaredMethods();
		Method[] tgm = TurtleGraphics.class.getDeclaredMethods();

		for (Method m : tsm) {
			ml.add(m);
		}

		for (Method m : tgm) {
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
	private Method getMatchedMethod(ArrayList<Method> ms, String command) {
		int count = 0;
		// Add all methods from TurtleSystem/ TurtleGraphics to method array list
		for (Method m : ms) {
			System.out.println(count + " Method name: " + m.getName().toLowerCase() + " Matches: "
					+ m.getName().toLowerCase().equals(command));
			count++;
			if (m.getName().toLowerCase().equals(command)) {
				return m;
			}
		}
		return null;
	}
}
