import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.CRC32;

class FlywayChecksumCalculator {
    public static int checksum(String filepath) {

        final CRC32 crc32 = new CRC32();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                crc32.update(sCurrentLine.getBytes("UTF-8"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return (int) crc32.getValue();
    }

    public static void main(String[] args) {
        System.out.println(checksum(
                "C:\\V7_34_2__mfs_setup.sql"));
    }
}