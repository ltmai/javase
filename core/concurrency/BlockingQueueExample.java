import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This example demonstrates how to use BlockingQueue<E> to solve the Producer-Consumer
 * problem.  BlockingQueue interface defines a take() method that blocks if the queue is 
 * empty, and a put() methods that blocks if the queue is full. 
 * 
 * SynchronousQueue<E> is a blocking queue implementation in which each insert operation 
 * must wait for a corresponding remove operation by another thread, and vice versa. A 
 * SynchronousQueue<E> does not have any internal capacity, not even a capacity of one.
 * 
 * This also demonstrates the use of PeCs: Producducer extends Consumer super
 * 
 * Unlike in example ProducerConsumer.java, for each transfer we create a new instance 
 * of message since it cannot be reused (we rely on Synchronization of put() and take() 
 * but we do not provide synchronization when the message is processed by consumer). A
 * message is a wrapper of content to be sent from Producer to Consumer.
 * 
 * @author MaiL
 *
 */
public class BlockingQueueExample {

	class Message {
		private int value;
		
		public Message(int i) {
			setValue(i);
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}
	
	/**
	 * Producer creates a message to Consumer and waits for some time before continues 
	 */
	class Producer extends Thread {
		private BlockingQueue<? super Message> queue;

		public Producer(BlockingQueue<? super Message> blockingQueue) {
			queue = blockingQueue;
		}

		public void run() {
			while (true) {				
				try {
					int i = ThreadLocalRandom.current().nextInt(1000, 3000);
					Message msg = new Message(i);
					System.out.print("Sending ..." + i);
					queue.put(msg);					
					Thread.sleep(i);
				} catch (InterruptedException e) {
					System.out.println("Producer failed: " + e.getMessage());
				}				
			}
		}
	}
	
	/**
	 * Consumer processes the message and wait for some time before continues
	 */
	class Consumer extends Thread {
		private BlockingQueue<? extends Message> queue;

		public Consumer(BlockingQueue<? extends Message> blockingQueue) {
			queue = blockingQueue;
		}

		public void run() {
			while (true) {
				try {
					Message msg = queue.take();
					System.out.println("... Receiving ..." + msg.getValue());
					Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
				} catch (InterruptedException e) {
					System.out.println("Consumer failed: " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Initializes and starts Producer and Consumer
	 */
	public BlockingQueueExample() {
		BlockingQueue<Message> blockingQueue = new SynchronousQueue<Message>();
		Producer producer = new Producer(blockingQueue);
		Consumer consumer = new Consumer(blockingQueue);
		
		producer.start();
		consumer.start();
	}
	
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args)
	{
		new BlockingQueueExample();
	}	
}
