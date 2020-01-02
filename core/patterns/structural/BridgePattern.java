/**
 * BridgePattern The Bridge pattern decouples an abstraction and its
 * implementation so that they can vary independently.
 * 
 * In the following example:
 * 
 * GraphicsManager is the abstraction that delegates real drawing work to the
 * platform-specific implementations Graphics (Windows, OSX).
 * 
 * EnhancedGraphicsManager is the extended GraphicsManager, enhanced with new
 * capability to draw shapes.
 */
public class BridgePattern {

    /**
     * GraphicsManager : 
     * The abstraction that delegates calls to an implementation
     */
    public static class GraphicsManager {
    
        protected Graphics graphicsImpl;

        public GraphicsManager(Platform platform) {
            switch (platform) {
                case WINDOWS:
                    graphicsImpl = new WindowsGraphics();
                    break;
                case OSX:
                    graphicsImpl = new OsxGraphics();
                    break;
                default:
                    throw new RuntimeException("Unsupported platform");
            }
        }

        public void drawPoint(int x, int y) {
            graphicsImpl.drawPoint(x, y);
        }

        public void drawLine(int x1, int y1, int x2, int y2) {
            graphicsImpl.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * AdvancedGraphicsManager: 
     * GraphicsManager can be enhanded with new capability to draw shapes.
     */
    public static class AdvancedGraphicsManager extends GraphicsManager {
    
        public AdvancedGraphicsManager(Platform platform) {
            super(platform);
        }

        /**
         * This extended capability is independent of implementations
         * @param shape
         */
        public void drawShape(Shape shape) {
            shape.draw(this.graphicsImpl);
        }
    }

    /**
     * Shape : simplified version of Shape objects that
     * knows how to draw itself given a Graphics object
     * that realizes the work on different platform.
     */
    public static class Shape {

        private int x, y;

        public Shape(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics graphics) {
            System.out.println("Draw shape:");
            graphics.drawLine(x, y, 200, 200);
            graphics.drawLine(x, y, 400, 400);
        };
    }

    /**
     * Graphics : 
     * The interface of implementations
     */
    public interface Graphics {

        void drawPoint(int x, int y);

        void drawLine(int x1, int y1, int x2, int y2);
        
    }

    /**
     * Windows graphics implementation
     */
    public static class WindowsGraphics implements Graphics{
    
        @Override
        public void drawPoint(int x, int y) {
            System.out.println("[Windows] Draw point");
        }

        @Override
        public void drawLine(int x1, int y1, int x2, int y2) {
            System.out.println("[Windows] Draw line");
        }
    }

    /**
     * OSX Graphics implementation
     */
    public static class OsxGraphics implements Graphics {
    
        @Override
        public void drawPoint(int x, int y) {
            System.out.println("[OSX] Draw point");
        }

        @Override
        public void drawLine(int x1, int y1, int x2, int y2) {
            System.out.println("[OSX] Draw line");
        }
    }

    private enum Platform {
        WINDOWS,
        OSX,
        LINUX
    }

    /**
     * Client code
     */
    public static class Client {
    
        public void testGraphicsManager() {
            GraphicsManager gm = new GraphicsManager(Platform.WINDOWS);
            gm.drawPoint(100, 200);
            gm.drawLine(100, 200, 500, 600);
    
            AdvancedGraphicsManager agm = new AdvancedGraphicsManager(Platform.OSX);
            agm.drawShape(new Shape(200, 200));
        }
    }

    public static void main(String[] args) {
        new Client().testGraphicsManager();
    }
}