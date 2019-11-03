/**
 * Let's use guarded blocks to create a Producer-Consumer application. 
 * This kind of application shares data between two threads: the producer
 * that creates the data, and the consumer, that does something with it. 
 * The two threads communicate using a shared object. Coordination is 
 * essential: the consumer thread must not attempt to retrieve the data 
 * before the producer thread has delivered it, and the producer thread 
 * must not attempt to deliver new data if the consumer hasn't retrieved 
 * the old data.
 */
class ProducerConsumer {

	private static class MessageContainer {
		private boolean retrieved = true;
		
		public synchronized void setDelivered() {
			retrieved = false;
			System.out.println("Message delivered");
			notifyAll();
		}
		public synchronized boolean isDelivered() {
			return (retrieved == false);
		};
		
		public synchronized void setRetrieved() {
			retrieved = true;
			System.out.println("Message retrieved");
			notifyAll();
		}
		public synchronized boolean isRetrieved() {
			return (retrieved == true);
		};		
	}

	private static class Producer extends Thread {
		private MessageContainer message;
		
		public Producer(MessageContainer message) {
			this.message = message;
		}
		
		private void deliver(MessageContainer message) {
			message.setDelivered();
			try {
				Thread.sleep((long)(Math.random() * 1000));
			}
			catch (InterruptedException e) {
			}
		}
		
		@Override
		public void run() {
			while (true) {
				message = new MessageContainer();
				while (!message.isRetrieved()) {
					try {
						message.wait();
					}
					catch (InterruptedException e) {
					}
				}			
				deliver(message);
			}
		}
	}
	
	private static class Consumer extends Thread {
		private MessageContainer message;
		
		public Consumer(MessageContainer message) {
			this.message = message;
		}
		
		private void retrieve(MessageContainer message) {
			message.setRetrieved();
			try {
				Thread.sleep((long)(Math.random() * 1000));
			}
			catch (InterruptedException e) {
			}
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
			}		
			while (true) {
				while ((message != null) && (!message.isDelivered())) {
					try {
						message.wait();
					}
					catch (InterruptedException e) {
					}
				}
				retrieve(message);
			}

		}
	}
	
	public static void main(String args[]) {
		MessageContainer  message  = null;
		Producer producer = new Producer(message);
		Consumer consumer = new Consumer(message);
		producer.start();
		consumer.start();
	}
}