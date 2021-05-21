import turtleExt.FileHandling;
import turtleExt.TurtleSystem;
import turtleExt.TurtleUI;

/**
 * Handle the entry point into the application and render display
 * @author James Davis - c3576413
 *
 */
public class MainClass {
	/**
	 * The current file
	 */
	protected static FileHandling fileHandle;
	
	/**
	 * Create UI object
	 */
	protected static TurtleUI ui;
	
	public static void main(String[] args) {
		//Get a turtle instance and pass utility reference
		TurtleSystem turtle = TurtleSystem.getTurtle();
		fileHandle = new FileHandling();
		ui = new TurtleUI(turtle, fileHandle);
		
	}
}