package mai.linh.gui.swing.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Utilities {
	/**
	 * retrieves screen resolution
	 */
	public static Dimension getScreenResolution() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		return kit.getScreenSize();
	}
	
	/**
	 * loads image from path
	 * @param imageSrc
	 * @return Image
	 */
	public static Image getImageResource(String imageSrc)
	{
		Toolkit kit = Toolkit.getDefaultToolkit();
		java.net.URL imgURL = Utilities.class.getResource(imageSrc);
		return kit.getImage(imgURL);		
	}
	
	/**
	 * loads buffered image from path
	 * @param imageSrc
	 * @return
	 * @throws IOException 
	 */
	public static BufferedImage getBufferedImageResource(String imageSrc) throws IOException
	{
		URL imageUrl = Utilities.class.getResource(imageSrc);
		BufferedImage bi = ImageIO.read(imageUrl);
		return bi;
	}
	
	/**
	 * sets frame location at center
	 * @param frame
	 */
	public static void setFrameLocationCenter(JFrame frame)
	{
		Dimension screenDimension = getScreenResolution();
		Dimension frameDimension = frame.getSize();
		
		frame.setLocation((screenDimension.width - frameDimension.width)/ 2, 
						  (screenDimension.height - frameDimension.height)/ 2);
	}
	
	/**
	 * generates a random integer in range 
	 * @return a random integer in range [low, high]
	 */
	public static int getRandomInt(int low, int high)
	{
		return ThreadLocalRandom.current().nextInt(low, high);
	}
}
