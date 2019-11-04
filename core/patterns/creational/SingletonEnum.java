/**
 * SingletonEnum
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