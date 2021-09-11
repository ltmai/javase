import java.io.IOException;
import java.util.zip.CRC32;
import java.io.BufferedReader;
import java.io.FileReader;

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
                "C:\\Workspace\\project-xyz\\project-xyz-flyway\\src\\main\\resources\\db\\migration\\oracle\\V1.0\\V1_0_1__db_setup.sql"));
    }
}