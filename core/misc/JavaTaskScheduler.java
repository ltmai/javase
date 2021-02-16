import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JavaTaskScheduler {

    public static void main(String[] args) {

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        ses.scheduleWithFixedDelay(()->{
           System.out.println(new Date()); 
           try {
            Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("----"); 
        }, 5, 1, TimeUnit.MILLISECONDS);

        try {
            Thread.sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ses.shutdown();
    }
}