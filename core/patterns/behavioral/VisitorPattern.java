/**
 * VisitorPattern
 * 
 * Visitor pattern encapsulates the operations on a object hierarchy, and allows
 * you define new operations without changing the classes.
 * 
 * In Visitor pattern, all operations implement a common interface Operation,
 * which defines visit() methods for ALL CONCRETE input types from the Shape
 * hierachy, which is a down-side of this pattern.
 * 
 * In the following example:
 * 
 * Class Point, Vector: are helper classes in defining Shapes and Operations.
 * 
 * Interface Shape, class Circle, Triangle: the target object hierarchy.
 * 
 * Interface Operation, class Transition, Rotation: are the operations on that
 * hierarchy.
 */
public class VisitorPattern {
    /**
     * Class Point: helper class used in defining Shapes.
     */
    public static class Point {

        private int x, y;

        public Point(int x, int y) {
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

        public String toString() {
            return String.format("(%d,%d)", x, y);
        }
    }

    /**
     * Class Vector: helper class used in define Operations.
     */
    public static class Vector extends Point {

        public Vector(int x, int y) {
            super(x, y);
        }
    }

    /**
     * Shape: the abstration of Shape.
     */
    public interface Shape {
        /**
         * The so-called accept method
         * 
         * @param visitor
         */
        void apply(Operation visitor);
    }

    /**
     * Triangle: Concrete Shape.
     */
    public static class Triangle implements Shape {

        private Point p1, p2, p3;

        public Triangle(Point p1, Point p2, Point p3) {
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
        }

        @Override
        public void apply(Operation operation) {
            operation.execute(this);
        }

        public Point getP1() {
            return p1;
        }

        public void setP1(Point p1) {
            this.p1 = p1;
        }

        public Point getP2() {
            return p2;
        }

        public void setP2(Point p2) {
            this.p2 = p2;
        }

        public Point getP3() {
            return p3;
        }

        public void setP3(Point p3) {
            this.p3 = p3;
        }

        @Override
        public String toString() {
            return String.format("Triangle %s, %s, %s", p1, p2, p3);
        }
    }

    /**
     * Circle: Concrete Shape
     */
    public static class Circle implements Shape {

        private Point center;

        private int radius;

        public Circle(Point center, int radius) {
            this.center = center;
            this.radius = radius;
        }

        @Override
        public void apply(Operation operation) {
            operation.execute(this);
        }

        public Point getCenter() {
            return center;
        }

        public void setCenter(Point center) {
            this.center = center;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        @Override
        public String toString() {
            return String.format("Circle at %s, radius=%d", center, radius);
        }
    }

    /**
     * Operation interface
     * This interface must define visit() methods with ALL CONCRETE input
     * types from the Shape hierachy, which is a down-side of this pattern.
     */
    public interface Operation {
        /**
         * The so-called visit method
         * 
         * @param triangle
         */
        void execute(Triangle triangle);

        /**
         * The so-called visit method
         * 
         * @param circle
         */
        void execute(Circle circle);
    }

    /**
     * Concrete operation
     */
    public static class Translation implements Operation {

        private Vector vector;

        public Translation(Vector vector) {
            this.vector = vector;
        }

        /**
         * This method is not thread-safe. Care should be taken if the triangle is
         * shared among threads.
         * 
         * @param triangle
         */
        @Override
        public void execute(Triangle triangle) {
            System.out.println(String.format("Transform triangle by %s", vector));

            Point p1 = triangle.getP1();
            Point p2 = triangle.getP2();
            Point p3 = triangle.getP3();

            p1.setX(p1.getX() + vector.getX());
            p1.setY(p1.getY() + vector.getY());
            p2.setX(p2.getX() + vector.getX());
            p2.setY(p2.getY() + vector.getY());
            p3.setX(p3.getX() + vector.getX());
            p3.setY(p3.getY() + vector.getY());
        }

        /**
         * This method is not thread-safe. Care should be taken if the triangle is
         * shared among threads.
         * 
         * @param circle
         */
        @Override
        public void execute(Circle circle) {
            System.out.println(String.format("Transform circle by %s", vector));

            Point center = circle.getCenter();

            center.setX(center.getX() + vector.getX());
            center.setY(center.getY() + vector.getY());
        }

        public Vector getVector() {
            return vector;
        }

        public void setVector(Vector vector) {
            this.vector = vector;
        }
    }

    /**
     * Rotation : rotate shape a given angle about one point
     */
    public static class Rotation implements Operation {

        private Point point;

        private double radian;

        public Rotation(Point point, double radian) {
            this.point = point;
            this.radian = radian;
        }

        @Override
        public void execute(Triangle triangle) {
            System.out.println(String.format("Rotate triangle about %s an angle of %f radian", point, radian));
        }

        @Override
        public void execute(Circle circle) {
            System.out.println(String.format("Rotate circle about %s an angle of %f radian", point, radian));
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public double getRadian() {
            return radian;
        }

        public void setRadian(double radian) {
            this.radian = radian;
        }
    }

    public static void main(String[] args) {
        Operation translation = new Translation(new Vector(2, 3));
        Operation rotation = new Rotation(new Point(20, 30), 0.5 * Math.PI);

        Shape triangle = new Triangle(new Point(1,1), new Point(3,4), new Point(6,7));
        Shape circle = new Circle(new Point(15, 15), 8);

        System.out.println(triangle);
        triangle.apply(translation);
        System.out.println(triangle);

        System.out.println(circle.toString());
        circle.apply(translation);
        System.out.println(circle.toString());

        triangle.apply(rotation);
        circle.apply(rotation);
    }
}