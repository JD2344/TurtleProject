package turtleExt;

import uk.ac.leedsbeckett.oop.TurtleGraphics;
import helperFunctions.UtilityFuncs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;
import java.lang.reflect.*;
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

	/**
	 * The turtle UI
	 */
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
	 * 
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
				}
			}
		} else {
			JOptionPane.showMessageDialog(ts,
					"The command: \"" + command + "\" is not valid, or " + "the Parameters were incorrect. ",
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * The about method.
	 */
	@Override
	public void about() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				superAbout();
				sleepCustom(3);
				ts.clear();
				ts.getGraphicsContext().drawString("Lame... That had to go...", ts.getxPos() / 2 - 50, ts.getyPos());
				sleepCustom(3);
				ts.clear();
				ts.getGraphicsContext().drawString("Watch this instead!", ts.getxPos() / 2 - 50, ts.getyPos());
				sleepCustom(3);
				ts.clear();
				ts.displayMessage("TurtleGraphics V3.0");
				ts.getGraphicsContext().drawString("Here is a bad circle...", ts.getxPos() / 2 - 50, ts.getyPos());
				circle(100);
				sleepCustom(3);
				ts.clear();
				ts.getGraphicsContext().drawString("Here are some better circles", ts.getxPos() / 2 - 50, ts.getyPos());
				sleepCustom(3);
				ts.clear();
				makeBresenhams();
				ts.clear();
				hexagon();
				ts.clear();
				honeyCombe();
				ts.clear();
				lightsaber();
				ts.getGraphicsContext().drawString("This was Turtle Graphics v3", ts.getxPos() / 2 - 50, ts.getyPos());
				ts.setTurtleSpeed(0);
			}
		});
		t.start();
	}
	
	/**
	 * Pause thread execution by a few seconds
	 * @param tts - number in seconds
	 */
	private void sleepCustom(int tts) {
		try {
			Thread.sleep(tts * 1000);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ts, e.getMessage());
		}
	}
	
	/**
	 * Run the original about method
	 */
	private void superAbout() {
		this.setBackground(Color.BLACK);
		super.about();
	}

	/**
	 * Make a row of lightsabers Red, yellow, green, blue, cyan, orange
	 */
	public void lightsaber() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				ts.getGraphicsContext().drawString("LIGHTSABERS", ts.getWidth() / 3, ts.getHeight() / 2 - 100);
				ts.setxPos(ts.getWidth() / 6);
				ts.setyPos(ts.getHeight() / 2 + 10);
				ts.setTurtleSpeed(50);
				
				ts.penDown();
				
				for (int c = 0; c < 6; c++) {
					ts.customColour(128, 128, 128);
					turnLeft();
					ts.forward(10);
					ts.turnRight();
					ts.forward(10);
					ts.turnRight();
					ts.forward(10);
					ts.turnRight();
					ts.forward(10);
					ts.setPenColour(changeColour(c));
					ts.forward(100);
					ts.turnRight();
					ts.forward(10);
					ts.turnRight();
					ts.forward(100);
					ts.setyPos(ts.getyPos());
					ts.customColour(128, 128, 128);
					ts.forward(80);
					ts.turnRight();
					ts.forward(10);
					ts.turnRight();
					ts.forward(80);
					ts.setyPos(ts.getyPos());
					ts.turnLeft(180);
					ts.setxPos(ts.getxPos() + 100);
				}
				ts.penUp();
			}
		});
		t.start();
	}

	/**
	 * Switches colour based on a number input
	 * 
	 * @param number
	 * @return
	 */
	private Color changeColour(int number) {
		switch (number) {
		case 0:
			return Color.RED;
		case 1:
			return Color.YELLOW;
		case 2:
			return Color.GREEN;
		case 3:
			return Color.BLUE;
		case 4:
			return Color.CYAN;
		case 5:
			return Color.ORANGE;
		default:
			return Color.RED;
		}
	}

	/**
	 * Make a honeycombe like animation
	 */
	public void honeyCombe() {
		Random r = new Random();
		ts.setTurtleSpeed(500);
		ts.setxPos(this.getWidth() / 3);
		ts.setyPos(this.getHeight() / 2);
		ts.penDown();
		for (int combes = 0; combes < 5; combes++) {
			for (int sides = 0; sides < 8; sides++) {
				customColour(r.nextInt(255), r.nextInt(255), r.nextInt(255));
				ts.turnLeft(45);
				ts.forward(50);
			}
			ts.setxPos(this.getxPos() + 100);
		}
		ts.penUp();
		red();
		ts.reset();
	}

	/**
	 * Makes a hexagon shape halfway in the canvas
	 */
	public void hexagon() {
		ts.setTurtleSpeed(50);
		ts.setxPos(ts.getWidth() / 2);
		ts.setyPos(ts.getHeight() / 2 - 50);
		ts.getGraphicsContext().drawString("Hexagon", ts.getWidth() / 4, ts.getHeight() / 2 - 60);
		ts.penDown();
		for (int sides = 0; sides < 6; sides++) {
			ts.getGraphicsContext().drawString(String.valueOf(sides + 1), ts.getxPos(), ts.getyPos() - 5);
			ts.forward(100);
			ts.turnRight(60);
		}
		ts.penUp();
		sleepCustom(2);
	}

	/**
	 * Provides an animation of making 4 bresenhams circles
	 */
	public void makeBresenhams() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				ts.setTurtleSpeed(5);
				ts.setxPos(ts.getWidth() / 5);
				ts.setyPos(ts.getHeight() / 2);
				ts.getGraphicsContext().drawString("Bresenhams Circles", ts.getWidth() / 4, ts.getHeight() / 2 - 60);
				int iX = ts.getxPos();
				int iY = ts.getyPos();
				ts.penDown();
				for (int circles = 0; circles < 5; circles++) {
					switch (circles) {
					case 0:
						ts.setPenColour(Color.BLUE);
						break;
					case 1:
						ts.setPenColour(Color.YELLOW);
						break;
					case 2:
						ts.setPenColour(Color.BLACK);
						break;
					case 3:
						ts.setPenColour(Color.GREEN);
						break;
					case 4:
						ts.setPenColour(Color.RED);
						break;
					}
					int radius = 50;
					int xc = iX += 100;
					int yc = iY;
					int x = 0, y = radius, delta = 3 - (2 * radius);
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
				}
				ts.penUp();
				ts.reset();
				
			}
		});
		t.start();
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
	}

	/**
	 * Overrides the default TurtleGraphics Circle Behaviour
	 * 
	 * @param radius - Int - The radius of the circle
	 */
	@Override
	public void circle(int radius) {
		Thread t = new Thread(new Runnable() {
			@Override
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
	public void blue() {
		this.setPenColour(Color.BLUE);
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
	private boolean isValidParamRange(String methodName, ArrayList<Object> parameters) {
		if (parameters.size() >= 1) {
			switch (methodName) {
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
					JOptionPane.showMessageDialog(ts, "Number not in canvas height and width ( " 
							+ this.getHeight() + ", " + this.getWidth() + ")" 
							+ "or an invalid parameter",
									"Parameter Warning", JOptionPane.WARNING_MESSAGE);
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
