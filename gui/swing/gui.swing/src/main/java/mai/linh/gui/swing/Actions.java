package mai.linh.gui.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import mai.linh.gui.swing.util.Utilities;

/**
 * This example demonstrates the use of actions both by tool bar and shortcut.
 * ----------------------------------------------------------------------------------------
 * Note on toolbar: 
 * Toolbar dragging works if the toolbar is inside a container with a border layout, or 
 * any other layout manager that supports the North, East, South, and West constraints.
 * ----------------------------------------------------------------------------------------
 * To summarize, here is what you do to carry out the same action in response to a button,
 * a menu item, or a keystroke:
 * 1. Implement a class that extends the AbstractAction class. You may be able to use the
 *    same class for multiple related actions.
 * 2. Construct an object of the action class.
 * 3. Construct a button or menu item from the action object. The constructor will read
 *    the label text and icon from the action object.
 * 4. For actions that can be triggered by keystrokes, you have to carry out additional
 *    steps. First locate the top-level component of the window, such as a panel that contains
 *    all other components.
 * 5. Then get the WHEN_ANCESTOR_OF_FOCUSED_COMPONENT input map of the top-level component.
 *    Make a KeyStroke object for the desired keystroke. Make an action key object, such as
 *    a string that describes your action. Add the pair (keystroke, action key) into the
 *    input map.
 * 6. Finally, get the action map of the top-level component. Add the pair (action key,
 *    action object) into the map.
 * ----------------------------------------------------------------------------------------
 */
public class Actions extends JFrame {
	private static final long serialVersionUID = 8740833275816444915L;
	
	private static final String KEY_STROKE_QUIT = "ctrl Q";
	private static final String KEY_STROKE_ABOUT = "ctrl H";
	
	private static final String ACTION_NAME_QUIT = "application.quit";
	private static final String ACTION_NAME_ABOUT = "application.help";
	
	private static final String PATH_ICON_QUIT = "/images/icons/quit.png";
	private static final String PATH_ICON_HELP = "/images/icons/help.png";
	
	private Action actionQuit;
	private Action actionHelp;	
	/**
	 * The Action that is reused by menu item, tool bar button and shortcut.
	 * You may implement interface Action, however it would be better to extend
	 * AbstractAction. This class provides default implementations for the JFC 
	 * Action interface. Standard behaviors like the get and set methods for 
	 * Action object properties (icon, text, and enabled) are defined here. The 
	 * developer need only subclass this abstract class and define the 
	 * actionPerformed method.
	 */
	
	/**
	 * action Quit
	 */
	private class ActionQuit extends AbstractAction {
		private static final long serialVersionUID = 2804659748944742148L;
		
		public ActionQuit(String name, Icon icon) {
			super(name, icon);
			putValue(Action.NAME, name);
			putValue("Nickname", name); 
			putValue(Action.SHORT_DESCRIPTION, "Quit application (Ctrl+Q)");
		}
			
		@Override
		public void actionPerformed(ActionEvent event) {
			System.exit(NORMAL);
		}
	} 
	
	/**
	 * action Help
	 */
	private class ActionHelp extends AbstractAction {
		private static final long serialVersionUID = 2804659748944742148L;
		
		public ActionHelp(String name, Icon icon) {
			super(name, icon);
			putValue(Action.NAME, name);
			putValue("Nickname", name); 
			putValue(Action.SHORT_DESCRIPTION, "Help (Ctrl+H)");
		}
			
		@Override
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, "heeeeeeelp!");
		}
	}
	
	private JPanel contentPane;

	/**
	 * Launches the application.
	 */
	public static void main(String[] args) {
		/* invokeLater() allows us to post a "job" to Swing, which it will 
		 * then run on the event dispatch thread at its next convenience */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Actions frame = new Actions();
					Utilities.setFrameLocationCenter(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Every JComponent has three input maps, each mapping KeyStroke objects to 
	 * associated actions. The three input maps correspond to three different 
	 * conditions: WHEN_FOCUSED, WHEN_ANCESTOR_OF_FOCUSED_COMPONENT and
	 * WHEN_IN_FOCUSED_WINDOW
	 */

	/**
	 * Creats the frame.
	 */
	public Actions() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());

		createActions();
		
		/**
		 * menu bar
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		/**
		 * menu File
		 */
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.setAction(actionQuit);
		mnFile.add(mntmQuit);
		
		/**
		 * menu Help
		 */
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.setAction(actionHelp);
		mnHelp.add(mntmHelp);
		
		/**
		 * toolbar
		 */
		JToolBar toolBar = new JToolBar();
		toolBar.add(actionQuit);
		toolBar.add(actionHelp);
		
		/**
		 * buttons
		 */
		JButton btnQuit = new JButton("Quit");
		JButton btnHelp = new JButton("Help");
		
		btnQuit.setAction(actionQuit);
		btnHelp.setAction(actionHelp);

		JPanel btnPanel = new JPanel();
		btnPanel.add(btnQuit);
		btnPanel.add(btnHelp);
		
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		setContentPane(contentPane);		
	}
	
	/**
	 * creates actions
	 */
	private void createActions() {
		InputMap imap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap amap = contentPane.getActionMap();
		/**
		 * action QUIT
		 */
		actionQuit = new ActionQuit("Quit", new ImageIcon(Animation.class.getResource(PATH_ICON_QUIT)));
		imap.put(KeyStroke.getKeyStroke(KEY_STROKE_QUIT), ACTION_NAME_QUIT);
		amap.put(ACTION_NAME_QUIT, actionQuit);	
		/**
		 * action HELP
		 */
		actionHelp = new ActionHelp("About", new ImageIcon(Animation.class.getResource(PATH_ICON_HELP)));
		imap.put(KeyStroke.getKeyStroke(KEY_STROKE_ABOUT), ACTION_NAME_ABOUT);
		amap.put(ACTION_NAME_ABOUT, actionHelp);
	}
}
