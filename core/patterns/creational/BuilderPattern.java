/**
 * Builder Pattern: The Builder pattern can be used to - build a complex object
 * otherwise would require a big constructor with a lot of parameters - build
 * different types of object that share the same construction process.
 */
public class BuilderPattern {

    /**
     * Car
     */
    public static class Car {
        private int seats;
        private Engine engine;
        private Color color;
        private String name;

        public int getSeats() {
            return seats;
        }

        public void setSeats(int seats) {
            this.seats = seats;
        }

        public Engine getEngine() {
            return engine;
        }

        public void setEngine(Engine engine) {
            this.engine = engine;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public String toString() {
            return new StringBuilder().append("Car [name=")
                                      .append(name)
                                      .append(", color")
                                      .append(color.toString())
                                      .append(", engine=")
                                      .append(engine.toString())
                                      .append(", seats=")
                                      .append(seats)
                                      .append("]")
                                      .toString();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * Manual
     */
    public static class Manual {
        private Engine engine;
        private Material material;
        private String name;

        public Engine getEngine() {
            return engine;
        }

        public void setEngine(Engine engine) {
            this.engine = engine;
        }

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }

        public String toString() {
            return new StringBuilder().append("Manual [name=")
                                      .append(name)
                                      .append(", type=")
                                      .append(material)
                                      .append(", engine=")
                                      .append(engine)
                                      .append("]")
                                      .toString();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static enum Engine {
        SPORT_ENGINE,
        SUV_ENGINE,
        SERIAL_ENGINE
    }

    public static enum Material {
        HANDBOOK,
        CD_ROM
    }

    public static enum Color {
        RED,
        BLACK,
        BLUE
    }

    /**
     * The builder interface defines only the common build steps which are
     * implemented by the concrete Builders. Each concrete Builder produces
     * an own representation (product type), that may not necessarily belong
     * to the same object hierarchy with other representations.
     * 
     * The getResult() method is not implemented in the Builder interface
     * if the output representations are not on the same hierarchy (e.g. Car
     * and Manual). But if you are dealing with creating objects of the same
     * hierarchy you can define the method in Builder.
     */
    public static interface Builder {
        Builder reset();
        Builder setName(String name);
        Builder setSeats(int seats);
        Builder setEngine(Engine engine);
        Builder setColor(Color color);
    }

    /**
     * CarBuilder 
     */
    public static class CarBuilder implements Builder {
        private int seats;
        private Engine engine;
        private Color color;  
        private String name;

        @Override
        public Builder reset() {
            seats = 0;
            engine = null;
            color = null;
            name = null;
            return this;
        };

        @Override
        public CarBuilder setSeats(int seats) {
            this.seats = seats;
            return this;
        };

        @Override
        public CarBuilder setEngine(Engine engine) {
            this.engine = engine;
            return this;
        };

        @Override
        public CarBuilder setColor(Color color) {
            this.color = color;
            return this;
        };

        @Override
        public CarBuilder setName(String name) {
            this.name = name;
            return this;
        };

        public Car buildCar() {
            Car car = new Car();
            car.setName(name);
            car.setColor(color);
            car.setEngine(engine);
            car.setSeats(seats);
            return car;
        }
    }

    /**
     * ManualBuilder
     */
    public static class ManualBuilder implements Builder {
        private Engine engine;
        private String name;

        @Override
        public Builder reset() {
            this.engine = null;
            return this;
        };

        @Override
        public Builder setSeats(int seats) {
            return this;
        };

        @Override
        public Builder setEngine(Engine engine) {
            this.engine = engine;
            return this;
        };

        @Override
        public Builder setColor(Color color) {
            return this;
        };

        @Override
        public Builder setName(String name) {
            this.name = name;
            return this;
        };
        
        public Manual buildManual() {
            Manual manual = new Manual();
            manual.setEngine(engine);
            manual.setName(name);
            return manual;
        }
    }

    /**
     * Director is like an expert who knows how to build specific types of Product 
     * (e.g. what components to be used, in which order). Director can also be thought 
     * of as specilized client code that builds most-used types of product. Director
     * is needed when the construction logic should be reused, for instance to build
     * a Sport car and its Manual. The Director itself does not create the objects
     * but just encapsulates the logic to create one. The object is created by the
     * corresponding builder.
     * 
     * If you are dealing with the objects of the same hierarchy, Director can get
     * the created object from abstract Builder and return here. Otherwise you have
     * to retrieve the object from the concrete builder.
     */
    public static class Director {

        private static final String SPORT_PACKAGE = "Sport package serial model";

        public Director() {
        }   

        /**
         * Build a special sport car model with pre-defined configuration. This 
         * process can also be re-used to create other representation (e.g. Car
         * Manual)
         * @param builder
         * @return Car instance
         */
        public void buildSportCar(Builder builder) {
            builder.reset()
                   .setName(SPORT_PACKAGE)
                   .setSeats(2)
                   .setEngine(Engine.SPORT_ENGINE)
                   .setColor(Color.BLACK);
        }
    }

    /**
     * Client code
     */
    public static class Client {
    
        private static final String SPECIAL_RED_EDITION = "Special RED edition";

        /**
         * To build a special car with individual configuration, client code can work
         * with the builder directly.
         */
        public void buildCarWithIndividualConfig() {
            System.out.println("\nBuild a Car with individual configuration:");
            CarBuilder carBuilder = new CarBuilder();
            
            carBuilder.reset()
                      .setName(SPECIAL_RED_EDITION)
                      .setEngine(Engine.SERIAL_ENGINE)
                      .setSeats(4)
                      .setColor(Color.RED);
    
            Car redCar = carBuilder.buildCar();
            System.out.println(redCar);
        }

        /**
         * To build a serial model sport car, get involved the director - the exert 
         * who knows how to build that model.
         */
        public void buildSerialModelCar() {
            System.out.println("\nBuild a serial model sport car:");
            CarBuilder carBuilder = new CarBuilder();
            ManualBuilder manualBuilder = new ManualBuilder();
            Director director = new Director();
            
            director.buildSportCar(carBuilder);
            director.buildSportCar(manualBuilder);

            Car sportCar = carBuilder.buildCar(); 
            Manual manual = manualBuilder.buildManual();
    
            System.out.println(sportCar);
            System.out.println(manual);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();

        client.buildSerialModelCar();
        client.buildCarWithIndividualConfig();
    }
}