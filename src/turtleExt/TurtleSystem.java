package turtleExt;

import uk.ac.leedsbeckett.oop.TurtleGraphics;
import helperFunctions.UtilityFuncs;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

import java.lang.reflect.*;
import java.net.URL;
import java.awt.Color;

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
	protected ArrayList<Method> methods;

	/**
	 * TurtleSystem Object
	 */
	private static TurtleSystem ts = new TurtleSystem();

	/*
	 * Variety of useful functions
	 */
	protected UtilityFuncs utility;

	protected TurtleUI tui;

	/**
	 * Default constructor Build UI and other required elements
	 */
	private TurtleSystem() {
		methods = buildMethodList();
		this.utility = new UtilityFuncs();
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
	
	/**
	 * Set the Turtle UI object
	 * @param tui
	 */
	public void setTurtleUI(TurtleUI tui) {
		this.tui = tui;
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

		ArrayList<Method> mA = utility.getMatchedMethod(methods, methodCall, parameters);

		if (mA.size() != 0) {
			if (mA.size() == 1) {
				// Check if method has parameters. if none. invoke. Else check types and match
				// with command
				Method m = mA.get(0);
				int methodParamSize = m.getParameterCount();
				if (methodParamSize == parameters.size()) {
					if (methodParamSize >= 1) {
						if (isValidParamRange(methodCall, parameters)) {
							utility.invokeMethod(methodParamSize, parameters, m, ts);
						}
					} else {
						utility.invokeMethod(0, new ArrayList<Object>(), m, ts); // 0 Parameters
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
		// super.about();
		int radius = 50;
		int xc = this.getxPos();
		int yc = this.getyPos();
		int x = 0, y = radius, delta = 3 - (2 * radius);
		this.penDown();
		this.setTurtleSpeed(0);
		// Circle algo followed:
		// https://www.javatpoint.com/computer-graphics-bresenhams-circle-algorithm
		EightWaySymmetricPlot(xc, yc, x, y);
		while (x <= y) {
			if (delta <= 0) {
				delta = delta + (4 * x) + 1;
			} else {
				delta = delta + (4 * x) - (4 * y) + 10;
				y = y - 1;
			}
			x = x + 1;
			EightWaySymmetricPlot(xc, yc, x, y);
		}
		this.penUp();
	}

	/**
	 * Splits a canvas into 8 plots with the corresponding x,y candidate values
	 * 
	 * @param xc
	 * @param yc
	 * @param x
	 * @param y
	 */
	private void EightWaySymmetricPlot(int xc, int yc, int x, int y) {
		putpixel(x + xc, y + yc);
		putpixel(-x + xc, y + yc);
		putpixel(x + xc, -y + yc);
		putpixel(-x + xc, -y + yc);
		putpixel(y + xc, x + yc);
		putpixel(y + xc, -x + yc);
		putpixel(-y + xc, -x + yc);
		putpixel(-y + xc, x + yc);
	}

	/**
	 * Draws a pixel on the canvas for a corresponding x,y value
	 * 
	 * @param x
	 * @param y
	 */
	private void putpixel(int x, int y) {
		this.setxPos(x);
		this.setyPos(y);
		this.forward(1);
		this.update(getGraphics());
	}

	/**
	 * Overrides the default TurtleGraphics Circle Behaviour
	 * 
	 * @param radius - Int - The radius of the circle
	 */
	@Override
	public void circle(int radius) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				int initialX = ts.getxPos(), xp = ts.getxPos();
				int initialY = ts.getyPos(), yp = ts.getyPos();
				// ts.setTurtleSpeed(5);
				int angle = 0;
				// Loop over 360 times and set the respective X/Y pos to correspond to cos and
				// sine Trigonometry
				ts.penDown();

				while (angle <= 360) {
					ts.setxPos(Math.round(Math.round(radius * Math.cos(angle))) + initialX);
					ts.setyPos(Math.round(Math.round(radius * Math.sin(angle))) + initialY);
					ts.turnRight(Math.round(Math.round(radius * Math.tan(angle))));
					ts.forward(1);
					ts.update(getGraphics());
					angle++;
				}

				ts.penUp();
				ts.setxPos(xp);
				ts.setyPos(yp);
			}
		});
		t.start();
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
	 * Resets turtle position to the center of the screen
	 */
	@Override
	public void reset() {
		this.setxPos(this.getWidth() / 2);
		this.setyPos(this.getHeight() / 2);
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
					JOptionPane.showMessageDialog(ts,
									"Make sure the number is between the canvas height and width ( " + this.getHeight()
											+ ", " + this.getWidth() + ")",
									"Not in range", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		return true;
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
}
