package turtleExt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.*;

import helperFunctions.UtilityFuncs;

/**
 * Renders the Turtle Menu GUI
 * 
 * @author James Davis - c3576413
 *
 */
public class TurtleUI {
	/**
	 * TurtleSystem Object
	 */
	private TurtleSystem tS;
	/**
	 * File Handler
	 */
	private FileHandling fileHandle;
	/**
	 * Utility Library
	 */
	private UtilityFuncs helpS;
	/**
	 * The JFrame Context
	 */
	public JFrame mainFrame;
	
	/**
	 * Height/Width Label
	 */
	private JLabel panelSize;
	

	/**
	 * Handles the GUI for the Main program
	 * 
	 * @param tg TurtleGraphics Object to passed
	 */
	public TurtleUI(TurtleSystem ts) {
		tS = ts;
		helpS = ts.utility;
		mainFrame = buildUIFrame();
		fileHandle = new FileHandling(mainFrame, ts);
		customLayout();
	}

	/**
	 * Builds the UI frames and sets the corresponding Menu Items
	 * 
	 * @param tg TurtleGraphics Object to be used
	 * @return JFrame Object to be displayed
	 */
	private JFrame buildUIFrame() {
		JMenuBar mb = buildUpperMenuBar();
		mainFrame = new JFrame();
		mainFrame.setJMenuBar(mb);// Set the menu bar
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultWindowTitle();
		mainFrame.setContentPane(tS);
		mainFrame.pack();// set the frame to a size we can see
		mainFrame.setVisible(true);// now display it

		// Allows resizeable nature of graphics container on screen.
		mainFrame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent ev) {
				int height = tS.getHeight();
				int width = tS.getWidth();
				tS.setPanelSize(width, height);
				panelSize.setText("Height: "+ tS.getHeight() + " Width: " + tS.getWidth());
				tS.reset();
			}
		});

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(helpS.saveConfirmation(tS)) {
					mainFrame.dispose();
					System.exit(0);
				}
			}
		});

		return mainFrame;
	}

	/**
	 * Sets the default window title
	 */
	private void setDefaultWindowTitle() {
		mainFrame.setTitle("TurtleGraphics - *Untitled");
	}

	/**
	 * Implements a MenuBar into the GUI.
	 * 
	 * @return JMenuBar Object with corresponding Menu items
	 */
	private JMenuBar buildUpperMenuBar() {
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
	 * Creates a jpanel with height/width information
	 * @return
	 */
	private JPanel setBottomPanel() {
		JPanel bottomPanel = new JPanel();
		panelSize = new JLabel("Height: "+ tS.getHeight() + " Width: " + tS.getWidth());
		panelSize.setForeground(Color.ORANGE);
		bottomPanel.add(panelSize);
		bottomPanel.setOpaque(false);
		return bottomPanel;
	}

	/**
	 * Builds a JMenu Object with default menu items
	 * 
	 * @return JMenu - Typical Menu Selection
	 */
	private JMenu generateFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem fileNew = new JMenuItem(new AbstractAction("New") {
			/**
			 * Generate Serial Version ID
			 */
			private static final long serialVersionUID = 4169888162340719052L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(helpS.saveConfirmation(tS))
					tS.clear();
					setDefaultWindowTitle();
			}
		});
		fileNew.setToolTipText("Opens a new canvas");

		JMenuItem fileOpen = new JMenuItem(new AbstractAction("Load") {
			/**
			 * Generate Serial Version ID
			 */
			private static final long serialVersionUID = 4169888162340719052L;

			@Override
			public void actionPerformed(ActionEvent e) {
				fileHandle.loadImage();
			}

		});
		fileOpen.setToolTipText("Open a Saved canvas");

		JMenuItem fileSave = new JMenuItem(new AbstractAction("Save") {
			/**
			 * Generate Serial Version ID
			 */
			private static final long serialVersionUID = 4169888162340719052L;

			@Override
			public void actionPerformed(ActionEvent e) {
				fileHandle.saveImage();
			}

		});
		fileSave.setToolTipText("Save current canvas");

		fileMenu.add(fileNew);
		fileMenu.add(fileOpen);
		fileMenu.add(fileSave);
		return fileMenu;
	}

	/**
	 * Builds a JMenu Object with Help menu items
	 * 
	 * @return JMenu - Typical Menu Selection
	 */
	private JMenu generateExtraMenu() {
		JMenu helpMenu = new JMenu("Help");
		JMenuItem showInterface = new JMenuItem(new AbstractAction("Show Interface") {
			/**
			 * Generated Serial Version ID
			 */
			private static final long serialVersionUID = 410334401177661816L;

			/**
			 * Shows/Hides the interface on screen
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				Component[] guiComponents = tS.getComponents();

				for (Component c : guiComponents) {
					if (c.isVisible()) {
						c.setVisible(false);
					} else {
						c.setVisible(true);
					}
				}
			}

		});
		showInterface.setToolTipText("Show/Hide the Interface");

		JMenuItem helpAbout = new JMenuItem(new AbstractAction("Help") {
			/**
			 * Generated Serial version ID
			 */
			private static final long serialVersionUID = 410334401177661816L;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(Thread.currentThread().getName());
			}
			
		});
		helpAbout.setToolTipText("About this program");

		helpMenu.add(showInterface);
		helpMenu.add(helpAbout);
		return helpMenu;
	}

	/**
	 * Builds a JMenu Object to allow turtle manipulation
	 * 
	 * @return JMenu 
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
				tS.clear();
			}
		});
		JMenuItem resetTurtle = new JMenuItem(new AbstractAction("Reset") {
			/**
			 * Generate Serial Version ID
			 */
			private static final long serialVersionUID = 4169888162340719052L;

			@Override
			public void actionPerformed(ActionEvent e) {
				tS.reset();
			}

		});
		
		JMenuItem setImage = new JMenuItem(new AbstractAction("Turtle to tux") {
			/**
			 * Generated Serial Version ID
			 */
			private static final long serialVersionUID = -927056946182216451L;

			@Override
			public void actionPerformed(ActionEvent e) {
				tS.setTurtleImage("resources/928px-TUX_G2.svg.png");
			}
		});
		setImage.setToolTipText("Changes the image of the turtle");
		
		JMenuItem setSpeed = new JMenuItem(new AbstractAction("Set Speed") {
			/**
			 * Generate Serial Version ID
			 */
			private static final long serialVersionUID = 4169888162340719052L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// Get first number out of string
				int speed = Integer.parseInt(JOptionPane.showInputDialog("Please enter a number "));
				tS.setTurtleSpeed(speed);
			}
		});
		setSpeed.setToolTipText("Set the speed of the turtle");

		JTextField redField = new JTextField(5);
		JTextField greenField = new JTextField(5);
		JTextField blueField = new JTextField(5);
		JPanel rgbPanel = new JPanel();
		rgbPanel.add(new JLabel("Red Value (0 - 255):"));
		rgbPanel.add(redField);
		rgbPanel.add(Box.createHorizontalStrut(15)); // a spacer
		rgbPanel.add(new JLabel("Green Value (0 - 255):"));
		rgbPanel.add(greenField);
		rgbPanel.add(Box.createHorizontalStrut(15)); // a spacer
		rgbPanel.add(new JLabel("Blue Value (0 - 255):"));
		rgbPanel.add(blueField);
		JMenuItem setPenColour = new JMenuItem(new AbstractAction("Set Pen Colour") {
			/**
			 * Generated Serial Version ID
			 */
			private static final long serialVersionUID = -1655760731657191013L;

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(tS, rgbPanel, "Set RGB Values", JOptionPane.YES_NO_OPTION);
				
				if(result == JOptionPane.YES_OPTION) {
					if (redField.getText() != null || greenField.getText() != null || blueField.getText() != null) {
						if (helpS.numberinRGBRange(Integer.valueOf(redField.getText()))
								&& helpS.numberinRGBRange(Integer.valueOf(blueField.getText()))
								&& helpS.numberinRGBRange(Integer.valueOf(greenField.getText()))) {
							if (result == JOptionPane.YES_OPTION) {
								tS.setPenColour(new Color(Integer.valueOf(redField.getText()),
										Integer.valueOf(greenField.getText()), Integer.valueOf(blueField.getText())));
								redField.setText("0");
								greenField.setText("0");
								blueField.setText("0");
							}
						}
					}					
				}
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
	 * 
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
				tS.about();
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
				String s = JOptionPane.showInputDialog(tS, "Please enter a radius");
				if (s != null) {
					if (s.matches("\\d+")) {
						int radius = Integer.parseInt(s);
						tS.circle(radius);
					}
				}
			}
		});
		cmdCircle.setToolTipText("Runs the Circle Animation");
		commandMenu.add(cmdAbout);
		commandMenu.add(cmdCircle);
		return commandMenu;
	}

	/**
	 * Manipulates the UI that is already rendered within the TurtleSystem Object
	 * and further allow implementation of on screen elements.
	 */
	public JPanel turtleControlsPanel() {
		ArrayList<Method> upS = new ArrayList<Method>();
		tS.methods.stream().forEach(m -> {
			if (m.getName().toLowerCase() == "forward" || m.getName().toLowerCase() == "backward") {
				upS.add(m);
			}
		});
		
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(100);
		Method upM = helpS.getMatchedMethod(upS, "forward", parameters).get(0);
		Method backward = helpS.getMatchedMethod(upS, "backward", parameters).get(0);
		
		JButton up = new JButton("Forward");
		up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				helpS.invokeMethod(100, upM, tS);
			}

		});
		JButton down = new JButton("Backward");
		down.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				helpS.invokeMethod(100, backward, tS);
			}

		});
		JButton left = new JButton("Left");
		left.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tS.turnLeft();
			}

		});
		JButton right = new JButton("Right");
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tS.turnRight();
			}

		});
		JPanel controls = new JPanel();
		controls.add(up);
		controls.add(down);
		controls.add(left);
		controls.add(right);
		controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
		controls.setOpaque(false);

		return controls;
	}
	
	/**
	 * Places all existing components on GUI into a panel
	 */
	private void customLayout() {
		Component[] cs = tS.getComponents();
		
		GridBagConstraints c = new GridBagConstraints();
		JPanel outside = new JPanel();
		outside.setOpaque(false);
		outside.setBorder(BorderFactory.createLineBorder(Color.blue));
		outside.setLayout(new GridBagLayout());
		
		
		JPanel inputP = new JPanel();
		inputP.setBorder(BorderFactory.createLineBorder(Color.orange));
		inputP.setLayout(new BoxLayout(inputP, BoxLayout.X_AXIS));
		inputP.setOpaque(false);
		
		JTextField input = (JTextField) cs[0];//Input field
		inputP.add(input);
		inputP.add(Box.createRigidArea(new Dimension(5,0)));//Add spacer between button
		
		JButton sendinput = (JButton) cs[1];//Send input button
		inputP.add(sendinput);
		
		JLabel ver = (JLabel) cs[2];//Version label
		ver.setForeground(Color.CYAN);
		inputP.add(ver);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		outside.add(inputP, c);
		
		JPanel extras = new JPanel();
		extras.setBorder(BorderFactory.createLineBorder(Color.red));
		extras.setOpaque(false);
		JPanel windowInfo = setBottomPanel();
		extras.add(windowInfo);
		JPanel controls = turtleControlsPanel();
		extras.add(controls);

		c.fill = GridBagConstraints.HORIZONTAL;
		outside.add(extras, c);
		
		tS.add(outside);
		tS.setBorder(BorderFactory.createLineBorder(Color.pink));
	}
}
