/**
 * Singleton pattern can be implemented as Enumeration which is inherently
 * singleton.
 */
public class SingletonEnum {
    enum Singleton {
        INSTANCE;

        public void hello() {
            System.out.println("Singleton::hello");
        }
    }

    public static void main(String[] args) {
        Singleton.INSTANCE.hello();
    }
}