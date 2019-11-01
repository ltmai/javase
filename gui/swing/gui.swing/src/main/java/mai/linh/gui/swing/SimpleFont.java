package mai.linh.gui.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mai.linh.gui.swing.util.Utilities;

/**
 * This example demonstrates simple font capabilities in Java
 */
public class SimpleFont extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Content panel
	 *
	 */
	class FontPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			String message = "Hello, World!";
			Font f = new Font("Serif", Font.BOLD, 36);
			g2d.setFont(f);
			// measure the size of the message
			FontRenderContext context = g2d.getFontRenderContext();
			Rectangle2D bounds = f.getStringBounds(message, context);
			// set (x,y) = top-left corner of text
			double x = (getWidth() - bounds.getWidth()) / 2;
			double y = (getHeight() - bounds.getHeight()) / 2;
			// add ascent to y to reach the baseline
			double ascent = -bounds.getY();
			double baseY = y + ascent;
			// draw the message
			g2d.drawString(message, (int) x, (int) baseY);
			g2d.setPaint(Color.LIGHT_GRAY);
			// draw the baseline
			g2d.draw(new Line2D.Double(x, baseY, x + bounds.getWidth(), baseY));
			// draw the enclosing rectangle
			Rectangle2D rect = new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight());
			g2d.draw(rect);
		}
	}
	
	public SimpleFont() {
		setSize(600,400);
		setContentPane(new FontPanel());
	}
	
	public static void main(String[] args) {
		/* invokeLater() allows us to post a "job" to Swing, which it will 
		 * then run on the event dispatch thread at its next convenience */
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				SimpleFont sf = new SimpleFont();
				Utilities.setFrameLocationCenter(sf);
				sf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				sf.setVisible(true);
			}
		});
	}
}
