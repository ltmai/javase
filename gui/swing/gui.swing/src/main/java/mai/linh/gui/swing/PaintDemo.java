package mai.linh.gui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mai.linh.gui.swing.util.Utilities;

/**
 * this example demonstrates some techniques in custom painting
 */
public class PaintDemo extends JFrame {

	private static final long serialVersionUID = -8482288751320347589L;
	
	private class PaintPanel extends JPanel {
		private static final long serialVersionUID = 8522809489150539223L;
		
		private int x = 50; /** upper left x */
		private int y = 50; /** upper left y */
		private int w = 20; /** width        */
		private int h = 20; /** height       */
		
		public PaintPanel() {
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					/**
					 * An important point worth noting is that although we have 
					 * invoked repaint twice in a row in the same event handler
					 * Swing is smart enough to take that information and repaint 
					 * those sections of the screen all in one single paint 
					 * operation. In other words, Swing will not repaint the 
					 * component twice in a row, even if that is what the code 
					 * appears to be doing.
					 */
					repaint(x,y,w+1,h+1);
					x = e.getX();
					y = e.getY();
					repaint(x,y,w+1,h+1);
				}
			});
		}
		
		public Dimension getPreferredSize() {
	        return new Dimension(250,200);
	    }
		
		public void paintComponent(Graphics g) {
	        Rectangle rect = g.getClipBounds();
	        if (rect != null) {
	        	System.out.println("Painting x=" + rect.getX() + ", y=" + rect.getY() 
	        			+ ", w=" + rect.getWidth() + ", h=" + rect.getHeight());
	        }
			super.paintComponent(g);
	        g.setColor(Color.RED);
	        g.fillRect(x, y, w, h);
	        g.setColor(Color.BLUE);
	        g.drawRect(x, y, w, h);
	    }
	}
	
	public PaintDemo(String title) {
		super(title);
		add(new PaintPanel());
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		/* invokeLater() allows us to post a "job" to Swing, which it will 
		 * then run on the event dispatch thread at its next convenience */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaintDemo frame = new PaintDemo("Paint Demo");
					Utilities.setFrameLocationCenter(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
