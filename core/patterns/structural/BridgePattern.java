/**
 * BridgePattern
 */
public class BridgePattern {

    /**
     * Shape
     */
    public static class Shape {

        private int x, y;

        public Shape(int x, int y) {
            this.setX(x);
            this.setY(y);
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void draw(Graphics graphics) {
            System.out.println("Draw shape:");
            graphics.drawLine(x, y, 200, 200);
            graphics.drawLine(x, y, 400, 400);
        };
    }

    /**
     * Graphics
     */
    public interface Graphics {

        void drawPoint(int x, int y);

        void drawLine(int x1, int y1, int x2, int y2);
        
    }

    /**
     * Windows graphics
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
     * OsxGraphics
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
     * Abstract
     */
    public static class GraphicsManager {
    
        protected Graphics graphics;

        public GraphicsManager(Platform platform) {
            switch (platform) {
                case WINDOWS:
                    graphics = new WindowsGraphics();
                    break;
                case OSX:
                    graphics = new OsxGraphics();
                    break;
                default:
                    throw new RuntimeException("Unsupported platform");
            }
        }

        public void drawPoint(int x, int y) {
            graphics.drawPoint(x, y);
        }

        public void drawLine(int x1, int y1, int x2, int y2) {
            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * AdvancedGraphicsManager
     */
    public static class AdvancedGraphicsManager extends GraphicsManager {
    
        public AdvancedGraphicsManager(Platform platform) {
            super(platform);
        }

        public void drawShape(Shape shape) {
            shape.draw(this.graphics);
        }
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