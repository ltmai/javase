import java.io.*;
import java.net.*;
 
/**
 * Pick Display emulator
 */
public class ServerProcess 
{
    private static int DEFAULT_PORT_NUMBER = 5000;
    private static int READ_BUFFER_SIZE = 128;
   
    /**
     * Utility function
     * prints a hex dump of a buffer
     * @param buffer
     * @param len
     */
    public static void hexdump(byte[] buffer, int len, PrintStream ps, String msg)
    {
        int  i = 0;
        char[] text = new char[16];
     
        ps.println(msg + " (" + len + " bytes)");
     
        while (i < len) {
            ps.format("%05x | ", i);
            for (int j = 0; j < 16; j++) {
                if (j == 8)
                    ps.print(" |");
                if (i < len) {
                    ps.format(" %02X", buffer[i]);
                    if (buffer[i] < 0x20)
                        text[j] = '.';
                    else
                        text[j] = (char)(buffer[i]);
                    }
                else {
                    text[j] = ' ';
                    System.out.print(" __");
                }
                i++;
            }
            ps.print(" | ");
            ps.println(text);
        }
    }    
    
    private int port;

    public ServerProcess(int port) {
        this.port = port;        
    }

    private void process(byte[] data, int length, InputStream inStream, OutputStream outStream) throws IOException {
        hexdump(data, length, System.out, "Received telegram");
    }

    public void start() {
        byte[] buffer = new byte[READ_BUFFER_SIZE];

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) 
            {
                System.out.println("Listening at port " + port);
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("New connected from " + clientSocket.getInetAddress());
                    InputStream inStream = new BufferedInputStream(clientSocket.getInputStream());
                    OutputStream outStream = clientSocket.getOutputStream();
                    
                    int len = 0;
                    do {
                        len = inStream.read(buffer, 0, READ_BUFFER_SIZE);
                        if (len != -1) {
                            process(buffer, len, inStream, outStream);
                        }
                    } while (len > 0);
    
                    System.out.println("Connection closed");
                }
                catch (IOException ex) {
                    ex.printStackTrace();            
                }        
            }
        } 
        catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    /**
     * Main
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(String.format("Syntax: ServerProcess [<port=%d>]", DEFAULT_PORT_NUMBER));
        }
 
        int port = DEFAULT_PORT_NUMBER;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new ServerProcess(port).start();
    }
}