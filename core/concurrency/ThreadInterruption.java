/**
 * This example demonstrates thread interuption
 * 
 * @author MaiL
 *
 *         The Thread class has an attribute that stores a boolean value
 *         indicating whether the thread has been interrupted or not. When you
 *         call the interrupt() method of a thread, you set that attribute to
 *         true.
 * 
 *         The isInterrupted() method only returns the value of that attribute.
 *         The static interrupted() tests the current thread has been interruped
 *         however it clears the interrupted status of the thread. Therefore the
 *         isInterrupted() is recommended.
 */
public class ThreadInterruption {
	
	private class ThreadedTask implements Runnable
	{
		/**
		 * simulates a long running task
		 */
		private void doLongTask()
		{
			try {
				System.out.println("long task running ...");
				while (true)
				{
					for (int i = 1; i< 100000; i++) {}
					if (Thread.currentThread().isInterrupted())
					{
						System.out.println("thread interrupted");
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
            try {
                doLongTask();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("thread terminated with error");
                return;
            }
		}
	}
	
	/**
	 * starts a thread and interrupts it after 3 seconds
	 */
	private void testInterruption() {
		Runnable runnableTask = new ThreadedTask();
		Thread threadedTask = new Thread(runnableTask);

		try {
		    System.out.println("starting thread");
		    threadedTask.setPriority(Thread.MIN_PRIORITY);
			threadedTask.start();
			Thread.sleep(5000);
			threadedTask.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main
	 * @param args main parameters
	 */
	public static void main(String[] args)
	{
		new ThreadInterruption().testInterruption();
	}	
}
