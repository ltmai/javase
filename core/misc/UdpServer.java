import java.io.PrintStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * UdpServer
 */
public class UdpServer {

    private static final int DEFAULT_UDP_PORT = 9000;

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

    public UdpServer(int port) {
        this.port = port;
    }

    public void start() {
        boolean running = true;
        byte[] buffer = new byte[256];

        try (DatagramSocket socket = new DatagramSocket(port);) {
            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                hexdump(buffer, packet.getLength(), System.out, "Received message");
            }                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: UDPServer [<port number=9000>]");
        }
 
        int port = DEFAULT_UDP_PORT;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new UdpServer(port).start();        
    }
}