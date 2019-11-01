package mai.linh.gui.swing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mai.linh.gui.swing.util.Utilities;

/**
 * This class demonstrates a simple animation using label's image icons.
 */
public class Animation 
	extends JFrame
	implements ActionListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * path to resource files
	 */
	private static final String RESOURCE_PATH = "/images/dogs";
	/**
	 * total number of image frames
	 */
	private static final int NUM_FRAMES = 14;
	/**
	 * slider minimal value
	 */
	private static final int MIN_VALUE  = 1;
	/**
	 * slider maximal value
	 */
	private static final int MAX_VALUE  = NUM_FRAMES;
	/**
	 * slider initial value
	 */
	private static final int INIT_VALUE = NUM_FRAMES;
	
	private JPanel contentPane;
	private JSlider slider;
	private JLabel picture;
	private JLabel text;
	
	/**
	 * current frames per second (speed)
	 */
	private int fps = NUM_FRAMES;
	/**
	 * current timer delay between image loads
	 */
	private int delay = 1000/fps;
	/**
	 * current frame (image) to be loaded
	 */
	private int frame = 0;
	
	private Timer timer = new Timer(delay, this);
	private ImageIcon images[] = new ImageIcon[NUM_FRAMES];

	/**
	 * @brief Launch the application.
	 */
	public static void main(String[] args) {
		/* invokeLater() allows us to post a "job" to Swing, which it will 
		 * then run on the event dispatch thread at its next convenience */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Animation frame = new Animation();
					Utilities.setFrameLocationCenter(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @brief initializes GUI components
	 */
	private void initGui()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 248, 276);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		slider = new JSlider(JSlider.HORIZONTAL, MIN_VALUE, MAX_VALUE, INIT_VALUE);
		slider.setMajorTickSpacing(NUM_FRAMES-1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		// Create the label table
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer( MIN_VALUE ), new JLabel("slower") );
		labelTable.put( new Integer( MAX_VALUE ), new JLabel("faster") );
		slider.setLabelTable( labelTable );		
		
		picture = new JLabel();
		picture.setIcon(new ImageIcon(Animation.class.getResource(RESOURCE_PATH + "/T0.gif")));
		
		text = new JLabel("FPS: " + fps);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(picture, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(text, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(slider, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(text)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(picture)
					.addContainerGap(32, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}	
	
	/**
	 * @brief adjust values when user moves the slider 
	 */
	private void initEvent() {
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				text.setText("FPS: " + Integer.toString(slider.getValue()));	
				fps = slider.getValue();
				delay = 1000/fps;
				timer.setDelay(delay);
				timer.restart();
			}
		});
	}
	
	/**
	 * @brief Constructor
	 */
	public Animation() {
		super("Animation");
		initResource();
		initGui();
		initEvent();
		timer.start();
	}
	/**
	 * @brief loads images into an array for convenient access
	 */
    private void initResource() {
    	for (int i = 0; i < NUM_FRAMES; i++)
    	{
    		images[i] = createImageIcon(RESOURCE_PATH + "/T" + i + ".gif");
    	}	
	}
    /**
     * @brief creates an ImageIcon by loading from path
     * @param path
     * @return An ImageIcon
     */
	protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Animation.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
        	System.out.println("Cannot load image at " + path);
            return null;
        }
    }

	/**
	 * @brief handles timer action callback
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame++;
		if (frame == NUM_FRAMES)
			frame = 0;
		if (images[frame]!=null)
			picture.setIcon(images[frame]);
		else
			System.out.println("Image not found" + frame);
	}	
}
