package turtleExt;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import uk.ac.leedsbeckett.oop.TurtleGraphics;
import helperFunctions.UtilityFuncs;

/**
 * Renders the Turtle Menu GUI
 * @author James Davis - c3576413
 *
 */
public class TurtleUI {
	private static TurtleGraphics tG;
	private UtilityFuncs helpS = new UtilityFuncs();
	
	/**
	 * Handles the GUI for the Main program
	 * @param tg TurtleGraphics Object to passed
	 */
	public TurtleUI(TurtleGraphics tg)
	{
		tG = tg;
		buildUIFrame(tg);
	}
	
	/**
	 * Builds the UI frames and sets the corresponding Menu Items
	 * @param tg TurtleGraphics Object to be used
	 * @return JFrame Object to be displayed
	 */
	private JFrame buildUIFrame(TurtleGraphics tg)
	{
		JMenuBar mb = buildMenuBar();
        JFrame mainFrame = new JFrame();//create a frame to display the turtle panel on
        mainFrame.setLayout(new FlowLayout());//Stickies Graphics frame and centers nicely.
        mainFrame.add(tg); //Add turtle graphics object to be rendered
        mainFrame.pack();//set the frame to a size we can see
        mainFrame.setVisible(true);//now display it
        mainFrame.setJMenuBar(mb);//Set the menu bar
        
        //Allows resizeable nature of graphics container on screen.
        mainFrame.addComponentListener(new ComponentAdapter() {
        	public void componentResized(ComponentEvent ev) {
        		int height = mainFrame.getHeight();
        		int width = mainFrame.getWidth();
        		tG.setPanelSize(width, height);
        	}
        });
        
        return mainFrame;
	}
	
	/**
	 * Implements a MenuBar into the GUI.
	 * @return JMenuBar Object with corresponding Menu items
	 */
	private JMenuBar buildMenuBar() {
		JMenu fileMenu = generateFileMenu();
		JMenu aboutMenu = generateExtraMenu();
		JMenu cmdMenu = generateCommandsMenu();
		JMenu graphicsMenu = generateGraphicsMenu();
		JMenuBar mBar = new JMenuBar();
		mBar.add(fileMenu);
		mBar.add(cmdMenu);
		mBar.add(graphicsMenu);
		mBar.add(aboutMenu);
		return mBar;
	}
	
	/**
	 * Builds a JMenu Object with default menu items
	 * @return JMenu - Typical Menu Selection
	 */
	private JMenu generateFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem fileNew = new JMenuItem("New");
		fileNew.setToolTipText("Opens a new canvas");
		JMenuItem fileOpen = new JMenuItem("Open");
		fileOpen.setToolTipText("Open a Saved canvas");
		JMenuItem fileSave = new JMenuItem("Save");
		fileSave.setToolTipText("Save current canvas");

		fileMenu.add(fileNew);
		fileMenu.add(fileOpen);
		fileMenu.add(fileSave);
		return fileMenu;
	}
	
	/**
	 * Builds a JMenu Object with Help menu items
	 * @return JMenu - Typical Menu Selection
	 */
	private JMenu generateExtraMenu() {
		JMenu helpMenu = new JMenu("Help");
		JMenuItem showInterface = new JMenuItem("Show Interface");
		showInterface.setToolTipText("Show/Hide the Interface");
		JMenuItem helpAbout = new JMenuItem("About");
		helpAbout.setToolTipText("About this program");
		
		helpMenu.add(showInterface);
		helpMenu.add(helpAbout);
		return helpMenu;
	}
	
	/**
	 * Builds a JMenu Object to allow turtle manipulation
	 * @return
	 */
	private JMenu generateGraphicsMenu() {
		JMenu graphicsMenu = new JMenu("Graphics");
		JMenuItem clearScreen = new JMenuItem(new AbstractAction("Clear") {
			/**
			 * Generate Serial Version ID
			 */
			private static final long serialVersionUID = -5623458469668093075L;

			@Override
			public void actionPerformed(ActionEvent e) {
				tG.clear();
			}
		});
		JMenuItem resetTurtle = new JMenuItem(new AbstractAction("Reset") {
			/**
			 * Generate Serial Version ID
			 */
			private static final long serialVersionUID = 4169888162340719052L;

			@Override
			public void actionPerformed(ActionEvent e) {
				tG.reset();
			}
			
		});
		JMenuItem setImage = new JMenuItem("Set Turtle Look");
		setImage.setToolTipText("Sets the image for the turtle");
		JMenuItem setSpeed = new JMenuItem(new AbstractAction("Set Speed") {
			/**
			 * Generate Serial Version ID 
			 */
			private static final long serialVersionUID = 4169888162340719052L;
			@Override
			public void actionPerformed(ActionEvent e) {
				//Get first number out of string
				int speed = helpS.parseIntOrNull(JOptionPane.showInputDialog("Please enter a number ").split("\\d+")[0]);
				tG.setTurtleSpeed(speed);
			}
		});
		setSpeed.setToolTipText("Set the speed of the turtle");
		JMenuItem setPenColour = new JMenuItem(new AbstractAction("Set Pen Colour") {
			/**
			 * Generated Serial Version ID
			 */
			private static final long serialVersionUID = -1655760731657191013L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		setPenColour.setToolTipText("Set a custom pen Colour");
		graphicsMenu.add(setImage);
		graphicsMenu.add(setSpeed);
		graphicsMenu.add(resetTurtle);
		graphicsMenu.add(clearScreen);
		graphicsMenu.add(setPenColour);
		return graphicsMenu;
	}
	
	/**
	 * Builds a JMenu Object with Command menu items
	 * @return JMenu - Typical Menu Selection
	 */
	private JMenu generateCommandsMenu() {
		JMenu commandMenu = new JMenu("Commands");
		JMenuItem cmdAbout = new JMenuItem(new AbstractAction("About") {
			/**
			 * Generated Serial Version ID
			 */
			private static final long serialVersionUID = -5623458469668093075L;

			@Override
			public void actionPerformed(ActionEvent e) {
				tG.about();
			}
		});
		cmdAbout.setToolTipText("Runs the About Animation");

		JMenuItem cmdCircle = new JMenuItem(new AbstractAction("circle") {
			/**
			 * Generate Serial Version ID 
			 */
			private static final long serialVersionUID = 4169888162340719052L;
			@Override
			public void actionPerformed(ActionEvent e) {
				String radius = JOptionPane.showInputDialog("Please enter a radius");
				tG.circle(10);
			}
		});
		cmdCircle.setToolTipText("Runs the Circle Animation");
		commandMenu.add(cmdAbout);
		commandMenu.add(cmdCircle);
		return commandMenu;
	}
}
