package turtleExt;

import uk.ac.leedsbeckett.oop.TurtleGraphics;
import helperFunctions.UtilityFuncs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.*;

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
		this.setBackground_Col(Color.black);
		methods = buildMethodList();
		this.utility = new UtilityFuncs();
		this.tui = new TurtleUI(this);
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
							utility.invokeMethod(methodParamSize, parameters, m, this);
						}
					} else {
						utility.invokeMethod(0, new ArrayList<Object>(), m, this); // 0 Parameters
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"The command: \"" + command + "\" is not valid, or " + "the Parameters were incorrect. ",
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * The about method.
	 */
	@Override
	public void about() {
		new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				superAbout();
				Thread.sleep(3000);
				ts.clear();
				ts.getGraphicsContext().drawString("Lame... That had to go...", ts.getxPos() / 2 - 50, ts.getyPos());
				Thread.sleep(3000);
				ts.clear();
				ts.getGraphicsContext().drawString("Watch this instead!", ts.getxPos() / 2 - 50, ts.getyPos());
				Thread.sleep(3000);
				ts.clear();
				ts.displayMessage("TurtleGraphics V3.0");
				ts.getGraphicsContext().drawString("Here is a bad circle...", ts.getxPos() / 2 - 50, ts.getyPos());
				circle(100);
				Thread.sleep(4000);
				ts.clear();
				ts.getGraphicsContext().drawString("Here are some better circles", ts.getxPos() / 2 - 50, ts.getyPos());
				Thread.sleep(3000);
				ts.clear();
				makeBresenhams().get();
				ts.clear();
				hexagon().get();
				Thread.sleep(3000);
				ts.clear();
				honeyCombe().get();
				ts.clear();
				lightsaber().get();
				ts.getGraphicsContext().drawString("this was Turtle Graphics v3", ts.getxPos() / 2 - 50, ts.getyPos());
				ts.setTurtleSpeed(0);
				return null;
			}
		}.execute();
	}

	/**
	 * Run the original about method
	 */
	public void superAbout() {
		super.about();
	}

	/**
	 * Make a row of lightsabers Red, yellow, green, blue, cyan, orange
	 */
	public SwingWorker<Object, Object> lightsaber() {
		SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
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
				return null;
			}
		};
		sw.execute();
		return sw;
	}

	/**
	 * Switches colour based on a number input
	 * 
	 * @param number
	 * @return Color
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
	public SwingWorker<Object, Object> honeyCombe() {
		SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				Random r = new Random();
				ts.setTurtleSpeed(50);
				ts.setxPos(ts.getWidth() / 4);
				ts.setyPos(ts.getHeight() / 2);
				ts.penDown();
				for (int combes = 0; combes < 5; combes++) {
					for (int sides = 0; sides < 8; sides++) {
						customColour(r.nextInt(255), r.nextInt(255), r.nextInt(255));
						ts.turnLeft(45);
						ts.forward(50);
					}
					ts.setxPos(ts.getxPos() + 100);
				}
				ts.penUp();
				red();
				ts.reset();
				ts.setTurtleSpeed(0);
				return null;
			}
		};
		sw.execute();
		return sw;
	}

	/**
	 * Makes a hexagon shape halfway in the canvas
	 */
	public SwingWorker<Object, Object> hexagon() {
		SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				ts.setTurtleSpeed(50);
				ts.setxPos(ts.getWidth() / 2);
				ts.setyPos(ts.getHeight() / 2 - 50);
				ts.getGraphicsContext().drawString("Hexagon", ts.getWidth() / 4, ts.getHeight() / 2 - 60);
				ts.penDown();
				for (int sides = 0; sides < 6; sides++) {
					ts.getGraphicsContext().drawString(String.valueOf(sides + 1), ts.getxPos(), ts.getyPos() - 5);
					ts.forward(100);
					ts.turnRight(60);
					ts.repaint();
				}
				ts.penUp();
				ts.setTurtleSpeed(0);
				return null;
			}
		};
		sw.execute();
		return sw;
	}

	/**
	 * Provides an animation of making 4 bresenhams circles
	 */
	public SwingWorker<Object, Object> makeBresenhams() {
		SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
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
				return null;
			}
		};
		sw.execute();
		return sw;
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
		new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				int initialX = ts.getxPos(), xp = ts.getxPos();
				int initialY = ts.getyPos(), yp = ts.getyPos();
				// this.setTurtleSpeed(5);
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
				return null;
			}
		}.execute();
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
		if(utility.saveConfirmation(this))
			this.clear();
	}

	/*
	 * Set pen colour to blue
	 */
	public void blue() {
		this.setPenColour(Color.BLUE);
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
	private boolean isValidParamRange(String methodName, ArrayList<Object> parameters) {
		if (parameters.size() >= 1) {
			switch (methodName) {
			case "customcolour":
				if (!utility.verifyRGBRange(parameters)) {
					return false;
				}
			case "turnleft":
			case "turnright":
				if (!utility.withinAngleRange(parameters)) {
					JOptionPane.showMessageDialog(this, "One or more numbers not in angle range (0-360)",
							"Not in range", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			case "forward":
			case "backward":
				if (!utility.numberinGraphicsFrame(parameters, this.getHeight(), this.getWidth())) {
					JOptionPane.showMessageDialog(
							this, "Number not in canvas height and width ( " + this.getHeight() + ", " + this.getWidth()
									+ ")" + " or an invalid parameter",
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
			if (m.getDeclaringClass().getName() == "turtleExt.TurtleSystem" || m.getDeclaringClass().getName() == "uk.ac.leedsbeckett.oop.TurtleGraphics") {
				ml.add(m);
			}
		}
		return ml;
	}
}
