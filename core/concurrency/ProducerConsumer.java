import java.util.concurrent.ThreadLocalRandom;

/**
 * Guarded block is the most common concurrency idiom. Such a block begins
 * by polling a condition that must be true before the block can proceed.
 * Let's use guarded blocks to create a Producer-Consumer application.
 * This is done with Object.wait() that blocks the current thread until
 * it is notified that something has happened. 
 * This kind of application shares data between two threads: the producer
 * that creates the data, and the consumer, that does something with it. 
 * The two threads communicate using a shared object. Coordination is 
 * essential: the consumer thread must not attempt to retrieve the data 
 * before the producer thread has delivered it, and the producer thread 
 * must not attempt to deliver new data if the consumer hasn't retrieved 
 * the old data.
 */
class ProducerConsumer {
	static private final int MAX_RUN_COUNT = 10;
	static private final String LAST_MESSAGE = "-1";
	/**
	 * The single message container used in communication between the
	 * producer and consumer.
	 * @author MaiL
	 */
	private static class MessageContainer {
		private String  content;
		private boolean retrieved = true;
		
		public MessageContainer() {
		}

		synchronized
		public void setDelivered() {
			retrieved = false;
			notifyAll();
		}

		synchronized
		public boolean isDelivered() {
			return (retrieved == false);
		};
		
		/* Synchronized */
		public synchronized void setRetrieved() {
			retrieved = true;
			notifyAll();
		}
		
		/* Synchronized */
		public synchronized boolean isRetrieved() {
			return (retrieved == true);
		}
		
		public String getMessage() {
			return content;
		}
		
		public void setMessage(String message) {
			this.content = message;
		};		
	}

	/**
	 * The producer
	 * @author MaiL
	 */
	private static class Producer extends Thread {
		private MessageContainer messageContainer;
		
		public Producer(MessageContainer messageContainer) {
			this.messageContainer = messageContainer;
		}
		
		private void deliver(MessageContainer letter) {
			System.out.print("Sending " + letter.getMessage());

			try {
				Thread.sleep((long)(Math.random() * 1000));
			}
			catch (InterruptedException e) {
			}
			letter.setDelivered();
		}
		
		@Override
		public void run() {
			for (int i = 0; i <= MAX_RUN_COUNT; i++) {
				/* wait() or notify() or notifyAll() an object requires holding 
				 * the intrinsic lock with synchronized. the thread is notified
				 * when the condition no longer matches: messageContainer is retrieved
				 */
				synchronized (messageContainer) {
					while (!messageContainer.isRetrieved()) {
						try {
							messageContainer.wait();
						}
						catch (InterruptedException e) {
							/* wait() can throw InterruptedException. In this 
							 * example, we can just ignore that exception we 
							 * only care about the condition 
							 */
						}
					}
				}
				if (i < MAX_RUN_COUNT)
				    messageContainer.setMessage(Integer.toString(ThreadLocalRandom.current().nextInt(1, 100)));
                else
                    messageContainer.setMessage(LAST_MESSAGE);
				deliver(messageContainer);
			}
		}
	}
	
	/**
	 * The consumer
	 * @author MaiL
	 */
	private static class Consumer extends Thread {
		private MessageContainer messageContainer;
		
		public Consumer(MessageContainer messageContainer) {
			this.messageContainer = messageContainer;
		}
		
		private void retrieve(MessageContainer letter) {
			try {
				Thread.sleep((long)(Math.random() * 1000));
			}
			catch (InterruptedException e) {
			}			
			System.out.println(" ... " + letter.getMessage() + " received");
			letter.setRetrieved();
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
			}
			
			while (true) {
				/* guarded block: guard invokes Object.wait to suspend the current 
				 * thread. The invocation of wait does not return until another 
				 * thread has issued a notification that some special event may 
				 * have occurred though not necessarily the event this thread is
				 * waiting for 
				 * wait() or notify() or notifyAll() an object requires holding 
				 * the intrinsic lock with synchronized. the thread is notified
				 * when the condition no longer matches: messageContainer is delivered
				 */
				synchronized (messageContainer) {
					while ((messageContainer != null) && (!messageContainer.isDelivered())) {
						try {
							messageContainer.wait();
						}
						catch (InterruptedException e) {
						}
					}
				}
				retrieve(messageContainer);
				if (messageContainer.getMessage().equals(LAST_MESSAGE)) {
				 	return;
				}
			}
		}
	}
	
	public static void main(String args[]) {
		MessageContainer  message  = new MessageContainer();
		Producer producer = new Producer(message);
		Consumer consumer = new Consumer(message);
		
		producer.start();
		consumer.start();
	}
}
