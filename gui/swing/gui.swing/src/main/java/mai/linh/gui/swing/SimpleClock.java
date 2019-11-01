package mai.linh.gui.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import mai.linh.gui.swing.util.Utilities;


/**
 * Simple clock 
 * Theme: google.com/?clock%20face
 * + Font/Size for number
 * + Font/Size for brand name (upper e.g. Pilot and lower e.g. Automatic)
 * + Hand images
 * + Date window images
 * + Face images 
 *   
 * @author mail
 *
 */
public class SimpleClock extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final int CLOCK_RADIUS = 60;

	private ClockPanel clockPanel;
	
	private javax.swing.Timer timer;

	private BufferedImage imgSecond;
	
	private BufferedImage imgMinute;
	
	private BufferedImage imgHour;
	
	private BufferedImage imgFace;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/* invokeLater() allows us to post a "job" to Swing, which it will 
		 * then run on the event dispatch thread at its next convenience */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleClock clock = new SimpleClock();
					Utilities.setFrameLocationCenter(clock);
					clock.start();
					clock.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	
	public class ClockPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private static final long MEGABYTE = 1024L * 1024L;
		
		private static final double RADIAN = 180.0 / Math.PI; 
		
		private int timeSecond;


		private int timeHour;


		private int timeMinute;
		
		public ClockPanel()
		{
			super();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
		    Graphics2D g2 = (Graphics2D) g;
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    
		    timeSecond = Calendar.getInstance().get(Calendar.SECOND);
		    timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
		    timeHour   = Calendar.getInstance().get(Calendar.HOUR);
		    paintFaceFromImage(g2);
		    paintHandsFromImages(g2);
		    
		    Runtime runtime = Runtime.getRuntime();
		    long memory = runtime.totalMemory() - runtime.freeMemory();
		    System.out.println("Used memorys: " + memory + "(" + (memory / MEGABYTE) + " MB)");
		}

		private void paintFaceFromImage(Graphics2D g2) {
			g2.drawImage(imgFace, 0,  0,  null);
		};
		
		@SuppressWarnings("unused")
		private void paintFace(Graphics2D g2) {
			int cx = getWidth()/2;
			int cy = getHeight()/2;
			
			for (int i = 1; i <= 12; i ++) {
				TextLayout tl = new TextLayout(Integer.toString(i), getFont(), g2.getFontRenderContext());
			      Rectangle2D bb = tl.getBounds();
			      double angle = (i * (360/12) - 90) / RADIAN;
			      double x = (CLOCK_RADIUS + 10) * Math.cos(angle) - bb.getCenterX() / 2;
			      double y = (CLOCK_RADIUS + 10) * Math.sin(angle) - bb.getCenterY() / 2;
			      tl.draw(g2, (float)(cx + x), (float)(cy + y));				
			}
		}

		private void paintHandsFromImages(Graphics2D g2)
		{
			double angle;
			angle = (timeHour * (360/24)) / RADIAN;
			paintHandImage(g2, imgHour  , angle, 10);
			angle = (timeMinute * (360/60)) / RADIAN;
			paintHandImage(g2, imgMinute, angle, 10);
			angle = (timeSecond * (360/60)) / RADIAN;
			paintHandImage(g2, imgSecond, angle, 23);
		}
		
		private void paintHandImage(Graphics2D g2, Image img, double angle, int offset)
		{
			AffineTransform saveXform = g2.getTransform();
            // TODO: make toCenterAt a member variable
			AffineTransform toCenterAt = new AffineTransform();
			toCenterAt.translate(getWidth()/2, getHeight()/2);
			toCenterAt.rotate(angle);
			toCenterAt.translate(-img.getWidth(null)/2, -offset);
			
			g2.drawImage(img, toCenterAt, null);
			g2.setTransform(saveXform);
		}
		
		@SuppressWarnings("unused")
		private void paintHands(Graphics2D g2) {
			int cx = (getWidth())/2;
			int cy = (getHeight())/2;
			double angle = (timeSecond * (360/60) - 90) / RADIAN;
			double x = (CLOCK_RADIUS) * Math.cos(angle);
			double y = (CLOCK_RADIUS) * Math.sin(angle);			
			
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1));
			g2.drawLine(cx, cy, cx + (int)x, cy + (int)y);
		}
	}
		
	public SimpleClock() {
		try {
			imgSecond = Utilities.getBufferedImageResource("/resources/clocks/second.png" );
			imgMinute = Utilities.getBufferedImageResource("/resources/clocks/minute.png" );
			imgHour   = Utilities.getBufferedImageResource("/resources/clocks/hour.png" );
			imgFace   = Utilities.getBufferedImageResource("/resources/clocks/face.png" );
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		/**
		 * Determines the width of the image. If the width is not yet known, 
		 * this method returns -1 and the specified ImageObserver object is 
		 * notified later.
		 */
		int w = imgFace.getWidth(null);
		int h = imgFace.getHeight(null);
		
		Shape circle = new Ellipse2D.Double(0 ,0, w, h);
		
		setUndecorated(true);
		setShape(circle);
		setSize(w, h);
		setIconImage(Utilities.getImageResource("/resources/clocks/hourglass.png"));
				
		clockPanel = new ClockPanel();
		setContentPane(clockPanel);
		timer = new Timer(1000, new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										update();
									}
								});
		Runtime.getRuntime().gc();
	}
	
	public void start() {
		timer.start();
	}
	
	public void update() {
		clockPanel.repaint();
	}
}
