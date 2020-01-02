import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Strategy Pattern allows you define a family of algorithms and
 * make them interchangable at run-time.
 * 
 * You can introduce new strategies without having to change the
 * context, HOWEVER the client code should be aware of the
 * available strategies (which is a downside of this pattern).
 * 
 * In the following examples:
 * 
 * Interface HashStrategy: The abstraction of hashing strategies. 
 * 
 * Class Md5Strategy, Sha256Strategy: two concrete implementation
 * of HashStrategy.
 * 
 * Class Context: holds the current strategy and lets you alter
 * the object's behavior at run-time.
 */
public class StrategyPattern {

    /**
     * Interface HashStrategy : defines the interface to calculate
     * hash of String input.  
     * 
     * Strategy pattern lets you extract the varying behavior into
     * a separate class hierarchy and thereby isolate the code, the
     * internal data and dependencies of various algorithms from the
     * rest of the code.
     */
    public interface HashStrategy {

        static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        }

        static String bytesToHex(byte[] bytes) {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xff & bytes[i]);
                if (hex.length() == 1) sb.append('0');
                    sb.append(hex);
            }
            return sb.toString();    
        }

        String hash(String input);
    }

    /**
     * MD5 algorithm
     */
    public static class Md5Strategy implements HashStrategy {

        private static final String ALGORITHM = "MD5";

        @Override
        public String hash(String input) {
            String md5;

            try {
                MessageDigest md = MessageDigest.getInstance(ALGORITHM);                
                md.update(HashStrategy.getSalt());
                byte[] bytes = md.digest(input.getBytes());
                md5 = HashStrategy.bytesToHex(bytes);
            } 
            catch (Exception e) {
                throw new RuntimeException(e);
            }

            return md5;
        }
    }

    /**
     * SHA-256 algorithm
     */
    public static class Sha256Strategy implements HashStrategy {

        private final String ALGORITHM = "SHA-256";

        @Override
        public String hash(String input) {
            String sha256;

            try {
                MessageDigest md = MessageDigest.getInstance(ALGORITHM);                
                byte[] bytes = md.digest(input.getBytes());
                sha256 = HashStrategy.bytesToHex(bytes);
            } 
            catch (Exception e) {
                throw new RuntimeException(e);
            }

            return sha256;
        }        
    }

    /**
     * Context: holds the current strategy
     * Strategy pattern lets you alter the object's behavior at runtime.
     */
    public static class Context {
    
        private HashStrategy strategy;

        public void setStrategy(HashStrategy strategy) {
            this.strategy = strategy;
        }

        public String encode(String input) {
            return strategy.hash(input);
        };
    }

    public static void main(String[] args) {
        Context context = new Context();
        HashStrategy md5 = new Md5Strategy();
        HashStrategy sha256 = new Sha256Strategy();

        String input = "password";
        System.out.println("Input: " + input);

        // encoding algorithm can be swapped at runtime:
        context.setStrategy(md5);
        System.out.println("MD5: " + context.encode("password"));        
        context.setStrategy(sha256);
        System.out.println("SHA-256: " + context.encode("password"));
    }
}