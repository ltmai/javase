import java.util.HashMap;
import java.util.Map;

/**
 * Proxy pattern: provides a substitute or placeholder for another object, and controls 
 * access to the original object, allowing you to perform something either before or after 
 * the request gets through to the original object.
 * 
 * In the following example:
 * 
 * Interface Service: the interface to all Service types.
 * 
 * Class RemoteService: the concrete implementation of Service, which we do not want to 
 * expose directly to client code.
 * 
 * Class ServiceProxy: the proxy for RemoteService, that also implements Service. It 
 * handles the requests and makes use of Cache, so only forwards request to RemoteService
 * if result is not found in cache (avoiding expensive remote calls). 
 */
public class ProxyPattern {

    /**
     * Abstract service
     */
    public interface Service {
        String retrieveFile(String uri);
    }

    /**
     * RemoteService class, for which we create the proxy
     */
    public static class RemoteService implements Service {
        @Override
        public String retrieveFile(String uri) {
            System.out.println("[Service] Retrieving remotely " + uri);
            return "Remote data";
        }
    }

    /**
     * The proxy for an concrete Service
     */
    public static class ServiceProxy implements Service {

        private Service service;

        private Map<String, String> cache = new HashMap<>();

        public ServiceProxy(Service service) {
            this.service = service;
        }

        @Override
        public String retrieveFile(String uri) {
            System.out.println("[Proxy  ] Retrieving " + uri);
            
            // before real invocation: check cache first
            if (cache.containsKey(uri)) {
                System.out.println("[Proxy  ] " + uri + " found in cache");
                return cache.get(uri);
            }

            // real invocation
            String data = service.retrieveFile(uri);

            // after real invocation: add to cache
            cache.put(uri, data);
            return data;
        }        
    }

    public static void main(String[] args) {
        ServiceProxy proxy = new ServiceProxy(new RemoteService());

        String uri_1 = "file_1";
        String uri_2 = "file_2";

        System.out.println("[Client ] File content: " + proxy.retrieveFile(uri_1)); // 1st time
        System.out.println("[Client ] File content: " + proxy.retrieveFile(uri_2)); // 1st time
        System.out.println("[Client ] File content: " + proxy.retrieveFile(uri_1)); // 2nd time
    }
}