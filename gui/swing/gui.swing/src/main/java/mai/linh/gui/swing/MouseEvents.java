package mai.linh.gui.swing;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mai.linh.gui.swing.util.Utilities;

/**
 * This example demonstrates mouse event handling
 * When the user clicks a mouse button, three listener methods are called: mousePressed when
 * the mouse is first pressed, mouseReleased when the mouse is released, and, finally, mouse-
 * Clicked.
 * 
 * It also demonstrates how repaint in Swing works. 
 * 
 * To be implemented
 * 1. Menu bar File, Edit(Shape, Color, Mode, Clear), About (Help)
 * 2. Tool bar (Save, Clear, Rectangle, Circle, Triangle, Fill/No-Fill, Color, Draw/Drag)
 * 3. Status bar (Current position, ...)
 * 
 * @author MaiL
 *
 */
public class MouseEvents extends JFrame {

	private static final long serialVersionUID	= 4652025347222381059L;
	
	private static final int FRAME_POSX 		= 300;
	private static final int FRAME_POSY 		= 300;
	private static final int FRAME_HEIGHT 		= 300;
	private static final int FRAME_WIDTH 		= 450;
	private static final int STROKE_WIDTH 		= 5;
	private static final int SHAPE_SIZE_RECT 	= 30;
	private static final int SHAPE_SIZE_CIRCLE 	= 30;
	private static final int COUNT_RECT			= 10;
	private static final int COUNT_CIRCLE		= 10;
	
	private Playground canvas;
	/**
	 * Operation mode: drawing, dragging
	 */
	private OpMode opMode = OpMode.DRAG;
	/**
	 * X-difference from the click point to the current shape position
	 */
	private double deltaX = 0;
	/**
	 * Y-difference from the click point to the current shape position
	 */
	private double deltaY = 0;
	
	/**
	 * Operation modes
	 * @author MaiL
	 *
	 */
	public enum OpMode {
		DRAW (0), /* drawing shape */
		DRAG (1); /* dragging shapes */
		
		private final int mode;
		
		OpMode(int i) {
			mode = i;
		}

		public int getMode() {
			return mode;
		}
	}
	
	/**
	 * debug a rectangle
	 * @param rect
	 */
	public static void debugRectangle(Rectangle rect) {
		System.out.println("RECT: (" + rect.x + ", " + rect.y + ") w=" + rect.width + " h=" + rect.height);
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
					MouseEvents frame = new MouseEvents();
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
	public MouseEvents() {
		setTitle("Mouse Event Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(FRAME_POSX, FRAME_POSY, FRAME_WIDTH, FRAME_HEIGHT);
		
		canvas = new Playground();
		canvas.setBorder(new EmptyBorder(5, 5, 5, 5));
		canvas.setLayout(new BorderLayout(0, 0));		
		setContentPane(canvas);
		
		for (int i = 0; i < COUNT_RECT; i++) {
			Shape rect = new Rectangle2D.Double(Utilities.getRandomInt(1, FRAME_WIDTH -50 + 1),
												Utilities.getRandomInt(1, FRAME_HEIGHT -70 + 1),
												SHAPE_SIZE_RECT, SHAPE_SIZE_RECT);
			canvas.addShape(rect);
		}

		for (int i = 0; i < COUNT_CIRCLE; i++) {
			Shape circle = new Ellipse2D.Double(Utilities.getRandomInt(1, FRAME_WIDTH -50+ 1),
												Utilities.getRandomInt(1, FRAME_HEIGHT -70+ 1),
										 	   	SHAPE_SIZE_CIRCLE, SHAPE_SIZE_CIRCLE);
			canvas.addShape(circle);
		}		
		
		/**
		 * handle mouse clicks
		 * getClickCount() returns the number of consecutive mouse clicks associated with this event.
		 * getModifiersEx() returns the extended or �down� modifiers for this event.
		 */
		canvas.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent event) {
				if (opMode == OpMode.DRAW) {
					/**
					 * should be created with factory method depending on which shape type is currently active
					 */
					//GShape shape = new GRectangle(event.getX(), event.getY(), SHAPE_SIZE_RECT, SHAPE_SIZE_RECT);
					//contentPane.addShape(shape);
				}
				else {
					canvas.findDraggedShape(event.getPoint());
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent event) {
				canvas.releaseDraggedShapes();
			}
		});
		
		/**
		 * handle mouse moves
		 */
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent event) {
				if (opMode == OpMode.DRAW) {
				}
				else {
					canvas.updateDraggedShape(event.getPoint());
				}
			}
			
			/**
			 * change cursor when the mouse is inside a shape
			 */
			@Override
			public void mouseMoved(MouseEvent event) {
				// if (find(event.getPoint()) == null) setCursor(Cursor.getDefaultCursor());
				// else setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			}
		});
	}
	
	/**
	 * The playground, the canvas to play with
	 */
	public class Playground extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private Set<Shape> shapes = new HashSet<Shape>();
		
		private Shape draggedShape;

		private Rectangle lastBoundingBox;
		private Rectangle repaintBox;

		public Playground() 
		{
			super();
		}
		
		/**
		 * Paint the canvas
		 * @param g
		 */
		@Override
		public void paintComponent(Graphics g) {
			int count=0;
			Graphics2D g2d = (Graphics2D)g;
			
			super.paintComponent(g);
			g2d.setStroke(new BasicStroke(STROKE_WIDTH));
			
			/**
			 * in a complex case with thousand shapes, we should consider
			 * redrawing those intersecting with the graphics repaint area
			 */
			for (Shape shape : shapes) {
				int R = (int) (Math.random( )*256);
				int G = (int)(Math.random( )*256);
				int B= (int)(Math.random( )*256);
				Color randomColor = new Color(R, G, B);
				g2d.setColor(randomColor);
				
				if (repaintBox != null)
				{
					Rectangle boundingBox = shape.getBounds();
					adjustRectangleWithStrokeWidth(boundingBox);
					if (overlap(boundingBox, repaintBox))
					{
						g2d.draw(shape);
						count++;
					}
				}
				else
				{
					count++;
					g2d.draw(shape);
				}
			}
			System.out.println("paintComponent: " + count);
		}
		
		/**
		 * releases the dragged shape (mouse released)
		 * set no shape dragged and no repaint box calculated yet so all shapes 
		 * are repainted when window resizes.
		 */
		public void releaseDraggedShapes() {
			draggedShape = null;
			repaintBox = null;
			deltaX = 0;
			deltaY = 0;
		}

		/**
		 * updates dragged shape with new position. repaints only the dirty region 
		 * (which is the intersection of bounding boxes at old and new positions)
		 * @param p
		 */
		public void updateDraggedShape(Point p) {
			if (draggedShape != null) {
				Rectangle newBoundingBox;
				/**
				 * first begin with the repaint as the last bounding box considered 
				 * its stroke width of the dragged shape, then extend to the new 
				 * bounding box (also consider its stroke width)  
				 */
				repaintBox = lastBoundingBox;				
				setPos(draggedShape, new Point2D.Double(p.x - deltaX, p.y - deltaY));				
				newBoundingBox = draggedShape.getBounds();
				adjustRectangleWithStrokeWidth(newBoundingBox);
				repaintBox.add(newBoundingBox);
				canvas.repaint(repaintBox);
				lastBoundingBox = newBoundingBox;
			}
		}

		/**
		 * Get position of an abstract Shape
		 * @param shape
		 * @return Position in Point2D.Double
		 */
		private Point2D.Double getPos(Shape shape) {
			if (shape instanceof Rectangle2D) {
				Rectangle2D s2d = (Rectangle2D)shape;
				return new Point2D.Double(s2d.getX(), s2d.getY());
			}
			if (shape instanceof Ellipse2D) {
				Ellipse2D s2d = (Ellipse2D)shape;
				return new Point2D.Double(s2d.getX(), s2d.getY());
			}			
			return null;
		}
		
		/**
		 * Set position of an abstract Shape 
		 * @param shape
		 * @param point
		 */
		private void setPos(Shape shape, Point2D.Double point)
		{
			if (shape instanceof Rectangle2D.Double) {
				Rectangle2D.Double s2d = (Rectangle2D.Double)shape;
				s2d.x = point.getX();
				s2d.y = point.getY();
			}
			if (shape instanceof Ellipse2D.Double) {
				Ellipse2D.Double s2d = (Ellipse2D.Double)shape;
				s2d.x = point.getX();
				s2d.y = point.getY();
			}			
		}
		
		/**
		 * checks if 2 rectangles overlap
		 * (RectA.X1 < RectB.X2 && RectA.X2 > RectB.X1 && RectA.Y1 < RectB.Y2 && RectA.Y2 > RectB.Y1) 
		 * http://stackoverflow.com/questions/306316/determine-if-two-rectangles-overlap-each-other
		 * @param rect1
		 * @param rect2
		 * @return true if overlap, false otherwise
		 */
		private boolean overlap(Rectangle rect1, Rectangle rect2) {
			
			if (rect1.intersects(rect2))
				return true;
			
			if ((rect1.getX() < rect2.getX() + rect2.getWidth()) && 
			   (rect1.getX()+rect1.getWidth() > rect2.getX()) && 
			   (rect1.getY() < rect2.getY() + rect2.getHeight()) && 
			   (rect1.getY() + rect1.getHeight() > rect2.getY()))
				return true;
				
			return false;
		}
		
		/**
		 * adjust a given rectangle (abstract object) with stroke width
		 * @param rect
		 */
		private void adjustRectangleWithStrokeWidth(Rectangle rect)
		{
			rect.x = rect.x - (STROKE_WIDTH/2);
			rect.y = rect.y - (STROKE_WIDTH/2);
			rect.width = rect.width + STROKE_WIDTH;
			rect.height = rect.height + STROKE_WIDTH;
		}
		
		/**
		 * Find the first shape in collection containing the point (dragged)
		 * @param p
		 */
		public Shape findDraggedShape(Point p) {
			draggedShape = null;
			
			for (Shape s : shapes) {
				if (s.contains(p)) {
					draggedShape = s;
					Point2D.Double pos = getPos(s);
					deltaX = p.x - pos.getX();
					deltaY = p.y - pos.getY();
					lastBoundingBox = s.getBounds();
					adjustRectangleWithStrokeWidth(lastBoundingBox);
					break;
				}
			}
			return draggedShape;
		}

		/**
		 * Add a Shape object to collection
		 * @param shape
		 */
		public void addShape(Shape shape) 
		{
			shapes.add(shape);
			canvas.repaint(shape.getBounds());
		}
		
		/**
		 * Remove a Shape object from collection
		 * @param shape
		 */
		public void removeShape(Shape shape)
		{
			
		}		
	}
}
