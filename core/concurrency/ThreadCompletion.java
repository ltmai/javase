import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This example demonstrates usage of CompletionService (CompletionServiceExecuter)
 * which is a consumer that takes completed tasks and processes their results in the
 * other they complete. A CompletionService can for example be used to manage 
 * asynchronous IO, in which tasks that perform reads are submitted in one part of 
 * a program or system, and then acted upon in a different part of the program when 
 * the reads complete, possibly in a different order than they were requested.
 * 
 * @author MaiL
 *
 */
public class ThreadCompletion {

	private static final int _POOL_SIZE = 4;
	private static final int THREAD_COUNT = 8;

	/**
	 * Thread that returns String
	 * @author MaiL
	 *
	 */
	private class StringTask implements Callable<String> {
		
		public StringTask() {
		}
		
		/*
		 * sleeps for a random interval between 1 and 10 seconds
		 * and returns that interval as string
		 */
		@Override
		public String call() throws Exception {
			int t = ThreadLocalRandom.current().nextInt(1000, 10000);
			Thread.sleep(t);
			return Integer.toString(t);
		}
	}
	
	/**
	 * test ThreadCompletionService
	 */
	private void testThreadCompletion() {
		ExecutorService threadPool = Executors.newFixedThreadPool(_POOL_SIZE);
		CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);
		
		/*
		 * submits tasks
		 */
		for(int i = 0; i < THREAD_COUNT; i++){   
		    pool.submit(new StringTask());
		}
		
		/*
		 * processes results in the order the threads complete
		 */
		for(int i =0; i <THREAD_COUNT; i++){
		    try {
		    	/*
		    	  take() retrieves and removes the Future representing the
		    	  next completed task, waiting if none are yet present.
		    	 */
				String result = pool.take().get();
				System.out.println("Thread slept (ms): " + result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		threadPool.shutdown();
	}
	
	/**
	 * Main
	 * @param args main parameters
	 */
	public static void main(String[] args) {		
		new ThreadCompletion().testThreadCompletion();
		System.out.println("Program terminated normally");
	}

}
