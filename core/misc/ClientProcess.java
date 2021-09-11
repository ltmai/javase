import java.io.*;
import java.net.*;
import java.util.Date;

public class ClientProcess {

    private static final int DEFAULT_PORT=5000;
    private static final String DEFAULT_HOST="localhost";
    
    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;

    public ClientProcess(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        System.out.println(String.format("Connection to %s:%s", host, port));
        try(Socket socket = new Socket(host, port)) {
            OutputStream output = socket.getOutputStream();
            // Wrap the OutputStream in a PrintWriter to send data in text format.
            // Argument true indicates that the writer flushes the data after each
            // method call (auto flush).
            PrintWriter writer = new PrintWriter(output, true);
            
            // ClientSocket can not detect if the connection has been closed or
            // whether the server is still reachable. A protocol between client
            // and server code may be necessary.     
            for (int i = 1; i <= 10; i++) {
                System.out.println("Sending " + i);
                writer.println(new Date());
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(String.format("Syntax: ClientProcess [<host:localhost> <port:%d>]", DEFAULT_PORT));
        }
 
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;

        if (args.length > 0) {
            host = args[0];
        }

        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        new ClientProcess(host, port).start();
    }
}
