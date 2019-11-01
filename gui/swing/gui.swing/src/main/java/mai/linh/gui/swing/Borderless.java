package mai.linh.gui.swing;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JLabel;

import mai.linh.gui.swing.util.Utilities;
/**
 * This example demonstrates title and border less custom frame
 */
public class Borderless {

	static final int RADIUS = 400;

	public static void main(String[] args) {
		JFrame frame = new JFrame("TitleLessJFrame");
		Shape circle = new Ellipse2D.Double(0 ,0, RADIUS, RADIUS);

		/**
		 *  make frame title-less and border-less
		 */
		frame.setUndecorated(true);
		/**
		 *  make frame circle
		 */
		frame.setShape(circle);
		/**
		 * make frame transparency (Java version 1.7+, otherwise:
		 * com.sun.awt.AWTUtilities.setWindowOpacity(this,0.5f)
		 */
		frame.setOpacity(0.5f);
		/** 
		 * set center location 
		 */
		frame.setSize(RADIUS, RADIUS);
		Utilities.setFrameLocationCenter(frame);
		
		frame.getContentPane().add(new JLabel(" HEY1234567890000000000000"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}