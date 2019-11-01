package mai.linh.gui.swing;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import mai.linh.gui.swing.util.Utilities;


public class ImgViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JSplitPane splitPane; 
	
	JScrollPane listScrollPane;
	JScrollPane pictureScrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/* invokeLater() allows us to post a "job" to Swing, which it will 
		 * then run on the event dispatch thread at its next convenience */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImgViewer imgViewer = new ImgViewer();
					Utilities.setFrameLocationCenter(imgViewer);
					imgViewer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ImgViewer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 700);
		setTitle("Image viewer");
		
		listScrollPane = new JScrollPane(new JTextArea());
		pictureScrollPane = new JScrollPane(new JTextArea());
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, pictureScrollPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		Dimension minimumSize = new Dimension(100, 50);
		listScrollPane.setMinimumSize(minimumSize);
		pictureScrollPane.setMinimumSize(minimumSize);
		
		setContentPane(splitPane);
	}
}
