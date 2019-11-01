package mai.linh.gui.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import mai.linh.gui.swing.util.Utilities;

/**
 * See http://docs.oracle.com/javase/tutorial/uiswing/painting/closer.html
 * Most of the standard Swing components have their look and feel implemented 
 * by separate UI Delegates. This means that most (or all) of the painting for 
 * the standard Swing components proceeds as follows.
 * + paint() invokes paintComponent().
 * + If the UI property is non-null, paintComponent() invokes ui.update().
 * + If the component's opaque property is true, ui.update() fills the 
 *   component's background with the background color and invokes ui.paint().
 * + ui.paint() renders the content of the component.
 * 
 * Therefore we can set the background color with alpha transparency to make
 * a JPanel look transparent.
 * 
 * The alpha value ranges between 0 and 255 where 0 is full transparent and 255 
 * is fully opaque. An intermediate value, would be semi-transparent.
 */
public class Transparency extends JFrame{
	
	private static final long serialVersionUID = 8676995784515275275L;

	/**
	 * Sub class of JPanel that draws a circle on a transparent panel. 
	 * One may have multiple circle on the same JPanel instead each on
	 * one panel, to avoid complexity with multiple JPanel instances.
	 * However for demo purpose we create two overlapping transparent
	 * Circle instances so we can observer the transparency effects.
	 */
	public class Circle extends JPanel {

		private static final long serialVersionUID = -4534070646606899782L;
		
		private double centerX, centerY;
		private double radius;
	
		public Circle() {
			super();
		    centerX = 200;
		    centerY = 200;
		    radius = 10;
		}
	
		public Circle( double x, double y, double r) {
			super();
			centerX = x;
		    centerY = y;
		    radius = r;
		}
	
		@Override
		public void paintComponent(Graphics g) {
		    super.paintComponent(g);
	
		    Graphics2D g2 = (Graphics2D) g;
		    Ellipse2D circle = new Ellipse2D.Double();
		    circle.setFrameFromCenter(centerX, centerY, centerX + radius, centerY + radius);
		    
		    g2.setPaint(new Color(255,255,255,255));
		    g2.draw(circle);
		}
	
		public void setCenterX(double x){this.centerX = x;}
		public void setCenterY(double y){this.centerY = y;}
		public void setRadius(double r){radius = r;}
	
		public double getCenterX(){return centerX;}
		public double getCenterY(){return centerY;}
		public double getRadius(){return radius;}
	}

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;


	private Transparency() {
		
		setTitle("Dancing Circles");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setContentPane(new JLabel(new ImageIcon("/home/lini/Pictures/pl.jpg")));    

		/**
		 * it is important to have absolute positioning layout to have 
		 * overlapping components
		 */
		setLayout(null);

		Circle myCircle = new Circle(150.0, 150.0, 60.0);
		Circle myCircle2 = new Circle(100.0, 100.0, 60.0);

		/** 
		 * alpha-transparency background to make JPanel transparent 
		 */
		myCircle.setBackground(new Color(0,0,0,65));
		myCircle2.setBackground(new Color(0,0,0,120));

		add(myCircle);  
		add(myCircle2); 
		
		/**
		 * position the components
		 */
		myCircle.setBounds(20,20,300,300);
		myCircle2.setBounds(30,30,300,300);

		myCircle.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		myCircle2.setBorder(BorderFactory.createLineBorder(Color.RED));
	}

	
	public static void main(String[] args) {
		/* invokeLater() allows us to post a "job" to Swing, which it will 
		 * then run on the event dispatch thread at its next convenience */
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				Transparency dc = new Transparency();
				Utilities.setFrameLocationCenter(dc);
				dc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				dc.setVisible(true);
			}
		});
	}
}
