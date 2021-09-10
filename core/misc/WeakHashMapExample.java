import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * WeakHashMap example
 */
public class WeakHashMapExample {

    private static final int NUMBER_OF_THREADS = 20;
    private static final int SHORT_LIVED_DURATION = 5;
    private static final int LONG_LIVED_DURATION = 10;

    private static Map<Thread, String> weakMap = Collections.synchronizedMap(new WeakHashMap<>()); 

    static void startThread(long duration) {
        new Thread(() -> {
            System.out.println(String.format("Thread %d started", Thread.currentThread().getId()));
            weakMap.put(Thread.currentThread(), Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(duration);
                Thread.sleep(duration);
            } 
            catch (Exception e) {}
        }).start();
    }

    public static void main(String[] args) {

        try {
            System.out.println("Number of threads in weak map: " + weakMap.size());
            System.out.println("Starting short-lived threads ...");
            for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                startThread(SHORT_LIVED_DURATION);
            }

            TimeUnit.SECONDS.sleep(2);
            System.out.println("Number of threads in weak map: " + weakMap.size());
            System.out.println("Starting long-lived threads ...");

            for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                startThread(LONG_LIVED_DURATION);
            }
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Number of threads in weak map: " + weakMap.size());

            TimeUnit.SECONDS.sleep(2);
            System.out.println("Start garbage collection. Short-lived threads should be gargabe collected");
            Runtime.getRuntime().gc();
            // garbage collection should finish by now
            TimeUnit.SECONDS.sleep(2);
        } 
        catch (Exception e) {} 

        System.out.println("Number of threads in weak map: " + weakMap.size());
    }
}
