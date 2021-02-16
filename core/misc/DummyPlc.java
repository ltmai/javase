import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
 
/**
 * Simple PLC emulator
 */
public class DummyPlc 
{
    private static String TELEGRAM_TYPE_ACKN = "ACKN";
    private static int    DEFAULT_TELEGRAM_LENGTH = 136;
    private static int    DEFAULT_PLC_PORT_NUMBER = 5000;
 
    /**
     * Utility function 
     * Replaces substring by another string at given index
     * @param s destination string
     * @param r replacement string
     * @param i index 
     * @return resulting string
     */
	public static String replaceAt(String s, String r, int i) 
    {
    	if (s == null) 
    	{
    		return null;
    	} 
    	
    	if (r == null) 
    	{
    		return s;
    	}
    	
    	if ((i < 0) || i > s.length())
    	{
    		return s;
    	}

    	return s.substring(0, i) + r + s.substring(i + r.length());    	
    }
    
    /**
     * Utility function
     * prints a hex dump of a buffer
     * @param buffer
     * @param len
     */
    public static void hexdump(byte[] buffer, int len, PrintStream ps)
    {
        int  i = 0;
        char[] text = new char[16];
     
        ps.println("Hexdump " + len + " bytes");
     
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
    
    /**
     * sends acknowledge telegram on receipt
     * 0136ALIVEMESF02176..................................................................................................................ETX_
     * 0136ACKNF021EMES76..................................................................................................................ETX_
     * @param telegram : telegram to acknowledge
     * @param output   : outgoing data stream
     */
    public static void ack(String telegram, OutputStream output) throws IOException
    {
        String emes  = telegram.substring(8,12);
        String plc   = telegram.substring(12,16);
        String reply = telegram;
        
        reply = replaceAt(reply, TELEGRAM_TYPE_ACKN, 4); 
        reply = replaceAt(reply, plc, 8); 
        reply = replaceAt(reply, emes, 12); 
        
        System.out.println(LocalDateTime.now() + ": " + telegram);
        System.out.println(LocalDateTime.now() + ": " + reply);
        
        output.write(reply.getBytes());
    }


    /**
     * Main
     */
    public static void main(String[] args) 
    {
        if (args.length < 1) 
        {
            System.out.println("Syntax: DummyPlc [<port number=5000> <telegram length>=136]");
        }
 
        int port = DEFAULT_PLC_PORT_NUMBER;

        if (args.length > 0)
        {
            port = Integer.parseInt(args[0]);
        }

        int teleLength = DEFAULT_TELEGRAM_LENGTH;
        
        if (args.length > 1) 
        {
            teleLength = Integer.parseInt(args[1]);
        }
 
        try (ServerSocket serverSocket = new ServerSocket(port)) 
        {
            while (true) 
            {
                System.out.println("Listening on port " + port);
                
                Socket socket = serverSocket.accept();
                System.out.println("New client connected from " + socket.getInetAddress());

                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
 
                int len = 0;
                byte[] buffer = new byte[teleLength];
                
                do
                {
                    System.out.println("Waiting for telegram");
                    len = input.read(buffer, 0, teleLength);
                    System.out.println("Bytes read: " + len);
                    if (len > 0)
                    {
                        hexdump(buffer, len, System.out);
                    }
                    if (len >= teleLength)
                    {
                        String telegram = new String(buffer);
                        ack(telegram, output);
                    }
                } while (len > 0);
                
                socket.close();
            }
        } 
        catch (IOException ex) 
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();            
        }
        System.out.println("Program terminated");
    }
}