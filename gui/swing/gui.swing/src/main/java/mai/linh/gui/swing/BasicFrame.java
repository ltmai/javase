package mai.linh.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.RepaintManager;
import javax.swing.border.EmptyBorder;

import mai.linh.gui.swing.util.PrintSuit;
import mai.linh.gui.swing.util.Utilities;

/**
 *	This example demonstrates the most basic custom drawing.
 *  The JFrame is one of the few Swing components that is not painted on a 
 *  canvas. Thus, the decorations (buttons, title bar, icons, and so on) are 
 *  drawn by the user's windowing system, not by Swing. (Core Java 1).
 *  
 *  You can turn off all frame decoration by calling setUndecorated(true);
 *  
 *                                       JComponent --- JPanel
 *  Object --- Component --- Container <
 *                                       Window --- Frame --- JFrame
 */
public class BasicFrame extends JFrame {

	private static final long serialVersionUID = -4734442730902348660L;
	
	private static final int COLOR_BOX_WIDTH = 20;	
	private static final int COLOR_BOX_HEIGHT = 10;	
	private static final int COLOX_BOX_TEXT_DISTANCE = 10;
	private static final String PATH_APP_ICON = "/images/dogs/T0.gif";
	private static final String KEY_STROKE_QUIT = "ctrl Q";
	private static final String KEY_STROKE_PRINT = "ctrl P";
	
	public static final String SYS_COLOR_DESKTOP                = "desktop";
	public static final String SYS_COLOR_ACTIVECAPTION          = "activeCaption";
	public static final String SYS_COLOR_ACTIVECAPTIONTEXT      = "activeCaptionText";
	public static final String SYS_COLOR_INACTIVECAPTION        = "inactiveCaption";
	public static final String SYS_COLOR_INACTIVECAPTIONTEXT    = "inactiveCaptionText";
	public static final String SYS_COLOR_INACTIVECAPTIONBORDER  = "inactiveCaptionBorder";
	public static final String SYS_COLOR_WINDOW                 = "window";
	public static final String SYS_COLOR_WINDOWBORDER           = "windowBorder";
	public static final String SYS_COLOR_WINDOWTEXT             = "windowText";
	public static final String SYS_COLOR_MENU                   = "menu";
	public static final String SYS_COLOR_MENUTEXT               = "menuText";
	public static final String SYS_COLOR_TEXT                   = "text";
	public static final String SYS_COLOR_TEXTTEXT               = "textText";
	public static final String SYS_COLOR_TEXTINACTIVETEXT       = "textInactiveText";
	public static final String SYS_COLOR_TEXTHIGHLIGHT          = "texthighlight";
	public static final String SYS_COLOR_TEXTHIGHLIGHTTEXT      = "texthighlighttext";
	public static final String SYS_COLOR_CONTROL                = "control";
	public static final String SYS_COLOR_CONTROLTEXT            = "controlText";
	public static final String SYS_COLOR_CONTROLLTHIGHLIGHT     = "controlLtHighlight";
	public static final String SYS_COLOR_CONTROLHIGHLIGHT       = "controlHighlight";
	public static final String SYS_COLOR_CONTROLSHADOW          = "controlShadow";
	public static final String SYS_COLOR_CONTROLDKSHADOW        = "controlDkShadow";
	public static final String SYS_COLOR_SCROLLBAR              = "scrollbar";
	public static final String SYS_COLOR_INFO                   = "info";
	public static final String SYS_COLOR_INFOTEXT               = "infoText";

	
	private static Dimension resolution;
	
	private JPanel contentPane;
	private ActionQuit actionQuit;
	private ActionPrint actionPrint;
	private MyPanel printablePanel;

	private class ActionQuit extends AbstractAction {
		private static final long serialVersionUID = 2804659748944742148L;
		
		public ActionQuit(String name) {
			putValue(Action.NAME, name);
			putValue("Nickname", name); 
			putValue(Action.SHORT_DESCRIPTION, "Quit application (Ctrl+Q)");
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			System.exit(NORMAL);
		}
	}
	
	private class ActionPrint extends AbstractAction {
		private static final long serialVersionUID = 2804659748944742148L;
		
		public ActionPrint(String name) {
			putValue(Action.NAME, name);
			putValue("Nickname", name); 
			putValue(Action.SHORT_DESCRIPTION, "Print current view (Ctrl+P)");
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			PrintSuit.printComponent(printablePanel);
		}
	}	
	
	/**
	 * Create a component onto which you can draw by extending JComponent and
	 * override paintComponent.
	 */
	@SuppressWarnings("unused")
	private class MyComponent extends JComponent {
		private static final long serialVersionUID = -1049111596546276714L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("Component drawing", 100, 100);
			System.out.println("MyComponent::paintComponent");
		}
	}
	
	private void createActions() {
		InputMap imap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap amap = contentPane.getActionMap();
		/**
		 * an icon should be set for this action so that it appears in the toolbar
		 */
		actionQuit = new ActionQuit("Quit");
		imap.put(KeyStroke.getKeyStroke(KEY_STROKE_QUIT), "application.quit");
		amap.put("application.quit", actionQuit);		
		
		actionPrint = new ActionPrint("Print");
		imap.put(KeyStroke.getKeyStroke(KEY_STROKE_PRINT), "application.print");
		amap.put("application.print", actionPrint);
	}	
	
	/**
	 * Instead of extending JComponent, some programmers prefer to extend the 
	 * JPanel class. A JPanel is intended to be a container that can contain 
	 * other components, but it is also possible to paint on it. There is just 
	 * one difference. A panel is opaque, which means that it is responsible 
	 * for painting all pixels within its bounds. The easiest way to achieve
	 * that is to paint the panel with the background color, by calling super
	 * .paintComponent in the paintComponent method of each panel subclass
	 * (Core Java 1)
	 */
	private class MyPanel extends JPanel implements Printable 
	{
		private static final long serialVersionUID = 2832007930300719526L;

		public MyPanel() {
			super();			
			setBorder(BorderFactory.createLineBorder(Color.BLUE));
			
			createActions();
			
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			JMenu mnFile = new JMenu("File");
			menuBar.add(mnFile);
			
			JMenuItem mntmQuit = new JMenuItem("Quit");
			mntmQuit.setAction(actionQuit);
			mnFile.add(mntmQuit);
			
			JMenuItem mntmPrint = new JMenuItem("Print");
			mntmPrint.setAction(actionPrint);
			mnFile.add(mntmPrint);
			
			JMenu mnHelp = new JMenu("Help");
			menuBar.add(mnHelp);
			
			JMenuItem mntmNewMenaboutuItem = new JMenuItem("About");
			mnHelp.add(mntmNewMenaboutuItem);
			
			JToolBar toolBar = new JToolBar();
			toolBar.add(actionQuit);
			toolBar.add(actionPrint);
			
			JButton btnQuit = new JButton("Quit");
			btnQuit.setAction(actionQuit);

			JButton btnAbout = new JButton("About");

			JPanel btnPanel = new JPanel();
			btnPanel.add(btnQuit);
			btnPanel.add(btnAbout);
			
			contentPane.add(toolBar, BorderLayout.NORTH);
			contentPane.add(btnPanel, BorderLayout.SOUTH);			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawString("Screen resolution: " + (int)resolution.getWidth() + " x " + (int)resolution.getHeight(), 50, 50);
			
			paintColorBox(g, 50, 70, SYS_COLOR_DESKTOP                 );
			paintColorBox(g, 50, 90, SYS_COLOR_ACTIVECAPTION           );
			paintColorBox(g, 50, 110, SYS_COLOR_ACTIVECAPTIONTEXT      );
			paintColorBox(g, 50, 130, SYS_COLOR_INACTIVECAPTION        );
			paintColorBox(g, 50, 150, SYS_COLOR_INACTIVECAPTIONTEXT    );
			paintColorBox(g, 50, 170, SYS_COLOR_INACTIVECAPTIONBORDER  );
			paintColorBox(g, 50, 190, SYS_COLOR_WINDOW                 );
			paintColorBox(g, 50, 210, SYS_COLOR_WINDOWBORDER           );
			paintColorBox(g, 50, 230, SYS_COLOR_WINDOWTEXT             );
			paintColorBox(g, 50, 250, SYS_COLOR_MENU                   );
			paintColorBox(g, 50, 270, SYS_COLOR_MENUTEXT               );
			paintColorBox(g, 50, 290, SYS_COLOR_TEXT                   );
			paintColorBox(g, 50, 310, SYS_COLOR_TEXTTEXT               );
			paintColorBox(g, 50, 330, SYS_COLOR_TEXTINACTIVETEXT       );
			paintColorBox(g, 50, 350, SYS_COLOR_TEXTHIGHLIGHT          );
			paintColorBox(g, 50, 370, SYS_COLOR_TEXTHIGHLIGHTTEXT      );
			paintColorBox(g, 50, 390, SYS_COLOR_CONTROL                );
			paintColorBox(g, 50, 410, SYS_COLOR_CONTROLTEXT            );
			paintColorBox(g, 50, 430, SYS_COLOR_CONTROLLTHIGHLIGHT     );
			paintColorBox(g, 50, 450, SYS_COLOR_CONTROLHIGHLIGHT       );
			paintColorBox(g, 50, 470, SYS_COLOR_CONTROLSHADOW          );
			paintColorBox(g, 50, 490, SYS_COLOR_CONTROLDKSHADOW        );
			paintColorBox(g, 50, 510, SYS_COLOR_SCROLLBAR              );
			paintColorBox(g, 50, 530, SYS_COLOR_INFO                   );
			paintColorBox(g, 50, 550, SYS_COLOR_INFOTEXT               );
			
			System.out.println("MyPanel::paintComponent");
		}
		
		/**
		 * Draw a filled color box
		 * @param g
		 * @param x
		 * @param y
		 */
		private void paintColorBox(Graphics g, int x, int y, String name) {
			Graphics2D g2d = (Graphics2D)g;
			Paint gp1 = g2d.getPaint();
			Paint gp2 = getSystemColorByName(name);
			
			g2d.setPaint(gp2);				
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.fillRect(x, y, COLOR_BOX_WIDTH, COLOR_BOX_HEIGHT);
			g2d.setPaint(gp1);
			g2d.drawString(name, x + COLOR_BOX_WIDTH + COLOX_BOX_TEXT_DISTANCE, y + COLOR_BOX_HEIGHT);
		}

		/**
		 * Map system color name to color object
		 * @param name
		 * @return
		 */
		private SystemColor getSystemColorByName(String name) {
			switch (name) {
				case SYS_COLOR_DESKTOP                : return SystemColor.desktop;
				case SYS_COLOR_ACTIVECAPTION          : return SystemColor.activeCaption;
				case SYS_COLOR_ACTIVECAPTIONTEXT      : return SystemColor.activeCaptionText;
				case SYS_COLOR_INACTIVECAPTION        : return SystemColor.inactiveCaption;
				case SYS_COLOR_INACTIVECAPTIONTEXT    : return SystemColor.inactiveCaptionText;
				case SYS_COLOR_INACTIVECAPTIONBORDER  : return SystemColor.inactiveCaptionBorder;
				case SYS_COLOR_WINDOW                 : return SystemColor.window;
				case SYS_COLOR_WINDOWBORDER           : return SystemColor.windowBorder;
				case SYS_COLOR_WINDOWTEXT             : return SystemColor.windowText;
				case SYS_COLOR_MENU                   : return SystemColor.menu;
				case SYS_COLOR_MENUTEXT               : return SystemColor.menuText;
				case SYS_COLOR_TEXT                   : return SystemColor.text;
				case SYS_COLOR_TEXTTEXT               : return SystemColor.textText;
				case SYS_COLOR_TEXTINACTIVETEXT       : return SystemColor.textInactiveText;
				case SYS_COLOR_TEXTHIGHLIGHT          : return SystemColor.textHighlight;
				case SYS_COLOR_TEXTHIGHLIGHTTEXT      : return SystemColor.textHighlightText;
				case SYS_COLOR_CONTROL                : return SystemColor.control;
				case SYS_COLOR_CONTROLTEXT            : return SystemColor.controlText;
				case SYS_COLOR_CONTROLLTHIGHLIGHT     : return SystemColor.controlLtHighlight;
				case SYS_COLOR_CONTROLHIGHLIGHT       : return SystemColor.controlHighlight;
				case SYS_COLOR_CONTROLSHADOW          : return SystemColor.controlShadow;
				case SYS_COLOR_CONTROLDKSHADOW        : return SystemColor.controlDkShadow;
				case SYS_COLOR_SCROLLBAR              : return SystemColor.scrollbar;
				case SYS_COLOR_INFO                   : return SystemColor.info;
				case SYS_COLOR_INFOTEXT               : return SystemColor.infoText;
			}
			return null;
		}

		@Override
		public int print(Graphics g, PageFormat pageFormat, int pageIndex)
				throws PrinterException 
		{
			if (pageIndex > 0)
			{
				return (NO_SUCH_PAGE);
			}
			else
			{
				int x = (int)pageFormat.getImageableX() + 1;
				int y = (int)pageFormat.getImageableY() + 1;
				g.translate(x,y);
				RepaintManager currentManager = RepaintManager.currentManager(this);
				currentManager.setDoubleBufferingEnabled(false);				
				paint(g);
				currentManager.setDoubleBufferingEnabled(true);
				return (PAGE_EXISTS);
			}
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/* invokeLater() allows us to post a "job" to Swing, which it will 
		 * then run on the event dispatch thread at its next convenience */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					resolution = Utilities.getScreenResolution();
					BasicFrame frame = new BasicFrame();
					Utilities.setFrameLocationCenter(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public BasicFrame() {
		/**
		 * setup main frame
		 */
		setIconImage(Utilities.getImageResource(PATH_APP_ICON));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 700);
		setTitle("Basic frame");
		/**
		 * setup content pane
		 */
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		/**
		 * add MyPanel as content pane
		 */
		printablePanel = new MyPanel();
		contentPane.add(printablePanel);
		/**
		 * alternatively:
		 * contentPane.add(new MyComponent());
		 */
		setContentPane(contentPane);
		/**
		 * let the windowing system place the frame
		 */
		setLocationByPlatform(true);
		/**
		 * set maximal size
		 */
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}
}
