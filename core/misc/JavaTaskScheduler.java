import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JavaTaskScheduler {
    private static final int INITIAL_DELAY = 2000;
    private static final int EXECUTION_DELAY = 1000;
    private static final int TASK_DURATION = 2000;
    private static final int TASK_REPEATS = 5;


    public static void main(String[] args) {

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        ses.scheduleWithFixedDelay(()->{
            System.out.println(new Date() + " Task started"); 
            try { TimeUnit.MILLISECONDS.sleep(TASK_DURATION); } catch (Exception e) {}
            System.out.println(new Date() + " Task completed"); 
        }, INITIAL_DELAY, EXECUTION_DELAY, TimeUnit.MILLISECONDS);

        try { TimeUnit.MILLISECONDS.sleep((TASK_DURATION+EXECUTION_DELAY)*TASK_REPEATS); } catch (Exception e) {}

        ses.shutdown();
    }
}
