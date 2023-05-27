package mai.linh.junit;

public class ExecutionTimer {
    private ExecutionTimer()
    {
    }

    public static long time(Runnable runnable)
    {
        long startTime = System.currentTimeMillis();
        runnable.run();
        long endTime = System.currentTimeMillis();
        return (endTime - startTime);
    }
}
