import turtleExt.TurtleSystem;

/**
 * Handle the entry point into the application and render display
 * @author James Davis - c3576413
 *
 */
public class MainClass {
	/**
	 * TODO: Make sure frame is passed turtle graphics object from TurtleSystem.
	 * @param args
	 */
	public static void main(String[] args) {
		//Get a turtle instance
		TurtleSystem turtle = TurtleSystem.getTurtle();
		turtle.circle(50);
	}
}