/**
 * Adapter Pattern: Adapter is commonly used with an existing app to make some
 * otherwise incompatible classes work together nicely.
 * 
 * Adapter changes the interface of an existing object. Proxy provides it with
 * the same interface. Decorator provides it with an enhanced interface. Facade
 * defines a new interface for existing objects.
 * 
 * In the following example:
 * 
 * Interface Woker is the expected interface.
 * 
 * Class Service is the existing service to be adapted.
 * 
 * Class ServiceAdapter is the adapter that implments the Worker and delegates
 * works to Service.
 */
public class AdapterPattern {

    /**
     * The expected interface
     */
    public interface Worker {
    
        public void doWork();
    }

    /**
     * Service class which we cannot change
     */
    public static class Service {

        public void doService() {
            System.out.println("Doing service");
        }
    }

    /**
     * Adapter
     */
    public static class ServiceAdapter implements Worker {
        Service service;

        public ServiceAdapter(Service service) {
            this.service = service;
        }

        @Override
        public void doWork() {
            System.out.println("Adapting service:");
            service.doService();
        }
    } 

    /**
     * Client code
     */
    public static class Client {
        /**
         * Client code that expects a Worker instance
         * @param worker
         */
        public void testAdapter(Worker worker) {
            worker.doWork();
        }
    }

    public static void main(String[] args) {
        new Client().testAdapter(new ServiceAdapter(new Service()));   
    }
}