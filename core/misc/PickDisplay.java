import java.io.PrintStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Pick Display emulator
 */
public class PickDisplay 
{
    private static int    DEFAULT_PORT_NUMBER = 5000;
   
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

    public PickDisplay(int port) {
        this.port = port;        
    }

    private void sendConfirmation(byte displayNr, OutputStream outStream) throws IOException {
        byte[] confirmTelegram = new byte[3];
        confirmTelegram[0] = displayNr;
        confirmTelegram[1] = 0x01; // rest length
        confirmTelegram[2] = 0x31; // confirmation code
        hexdump(confirmTelegram, 3, System.out, "Logical confirm");
        outStream.write(confirmTelegram);
    }

    private void sendStatusChange(byte displayNr, OutputStream outStream) throws IOException {
        if (displayNr != 0x7F) { // broadcast
            byte[] statusTelegram = new byte[7];
            statusTelegram[0] = displayNr;
            statusTelegram[1] = 0x05; // rest length
            statusTelegram[2] = 0x00; // status change
            statusTelegram[3] = 0x01; // status byte (foot)
            statusTelegram[4] = 0x00; // status value
            statusTelegram[5] = 0x00; // status value
            statusTelegram[6] = 0x00; // status value
            hexdump(statusTelegram, 7, System.out, "Status changed");
            outStream.write(statusTelegram);
        }
    }

    private void processTelegram(byte[] telegram, int length, InputStream inStream, OutputStream outStream) throws IOException {
        hexdump(telegram, length, System.out, "Received telegram");
        
        byte displayNr = telegram[0];        
        System.out.println("Display number " + Byte.toString(displayNr));

        sendConfirmation(displayNr, outStream);

        sendStatusChange(displayNr, outStream);
    }

    public void start() {
        Socket socket = null;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) 
            {
                System.out.println("Listening on port " + port);                
                
                socket = serverSocket.accept();

                System.out.println("New client connected from " + socket.getInetAddress());

                InputStream inStream = new BufferedInputStream(socket.getInputStream());
                OutputStream outStream = socket.getOutputStream();
 
                int len = 0;
                byte[] buffer = new byte[10];
                
                do {
                    len = inStream.read(buffer, 0, 10);
                    System.out.println("\nReceived bytes: " + len);
                    if (len == 10) {
                        processTelegram(buffer, len, inStream, outStream);
                    }
                } while (len > 0);
            }
        } 
        catch (IOException ex) {
            ex.printStackTrace();            
        }
        finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Main
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: PickDisplay [<port number=5000>]");
        }
 
        int port = DEFAULT_PORT_NUMBER;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new PickDisplay(port).start();
    }
}