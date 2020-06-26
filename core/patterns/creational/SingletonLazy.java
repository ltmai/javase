/**
 * Singleton that is thread-safe and lazy
 */
public class SingletonLazy {
    /**
     * The Singleton class.
     */
    public static class Singleton {

        /**
         * The inner Singleton holder, which is not loaded before first use even 
         * when the outer class Singleton already loaded.
         */
        private static final class SingletonHolder {
            public static final Singleton INSTANCE = new Singleton();
        }

        public static Singleton getInstance() {
            return SingletonHolder.INSTANCE;
        }

        /**
         * Static method that causes the class be loaded but no 
         * instance created.
         */
        public static void classIsLoaded() {
            System.out.println("Singleton class is loaded");
        }

        /**
         * Constructor: we would see the message printed out when 
         * an instance of Singleton is created.
         */
        private Singleton() {
            System.out.println("Constructor: create a instance of Singleton");
        }

        public void hello() {
            System.out.println("Singleton::hello");
        };
    }

    public static void doSomething() {
        System.out.println("Do other things here");
    }

    public static void main(String[] args) {
        Singleton singleton;
        
        Singleton.classIsLoaded();

        doSomething();
        
        singleton = Singleton.getInstance();

        singleton.hello();
    }
}