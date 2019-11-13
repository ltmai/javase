/**
 * FlyweightPattern
 */
public class FlyweightPattern {

    /**
     * Helper enumeration
     */
    public enum Color {
        RED,
        GREEN,
        BLUE
    };

    /**
     * Sprite: Helper enummeration.
     * In reality, it may hold the large visual graphics data
     * shared by all moving particles.
     */
    public enum Sprite {
        FIRE,
        SPRITE
    };

    /**
     * Helper class
     */
    public static class Coord {
        private int x, y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
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

        @Override
        public String toString() {
            return String.format("{%d,%d}", x, y);
        }
    }

    /**
     * Helper class
     */
    public static class Direction {
        private int x, y;

        public Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    /**
     * Particle: holds common core data and behavior
     */
    public static class Particle {
    
        private Color color;
        private Sprite sprite;

        public Particle(Color color, Sprite sprite) {
            this.color = color;
            this.sprite = sprite;
        }

        public void move(Coord coord, Direction direction) {
            coord.setX(coord.getX() + direction.getX());
            coord.setY(coord.getY() + direction.getY());

            System.out.println("Particle::move to " + coord.toString());
        };

        public void draw(Coord coord) {
            System.out.println(String.format("Particle::draw coord=%s, color=%s, sprite=%s", coord, color, sprite));
        }
    }

    /**
     * MovingParticle: the light-weight object
     */
    public static class MovingParticle {    
        private Particle particle;
        private Direction direction;
        private Coord coord;

        public MovingParticle(Particle particle, Coord coord, Direction direction) {
            this.particle = particle;
            this.direction = direction;
            this.coord = coord;
            System.out.println("New MovingParticle at " + coord.toString());
        }

        public void move() {
            particle.move(coord, direction);
        }

        public void draw() {
            particle.draw(coord);
        }
    }

    public static void main(String[] args) {
        Particle simpleBullet = new Particle(Color.RED, Sprite.FIRE);
        MovingParticle movingBullet = new MovingParticle(simpleBullet, new Coord(0, 0), new Direction(1, 2));

        movingBullet.move();
        movingBullet.draw();
    }
}