import java.util.Date;
import java.io.PrintStream;

public class HexDump {

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

    public static void main(String[] args) {
        byte[] buffer = new Date().toString().getBytes();
        hexdump(buffer, buffer.length, System.out, "HexDump");
    }
}
