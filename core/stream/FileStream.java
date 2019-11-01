import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.OptionalInt;

/**
 * Created by lini on 12/18/16.
 */
public class FileStream {
    static final String INPUT_FILE = "..\\resources\\input\\words.txt";
    /**
     * finds the maximal length of lines in file as OptionalInt
     */
    public void testFindMaxLineLength() {
        System.out.println("testFindMaxLineLength");
        Path input = Paths.get(INPUT_FILE);
        try {
            final OptionalInt max = Files.lines(input).mapToInt(String::length).max();
            if (max.isPresent()) {
                System.out.println("Max line length: " + max.getAsInt());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * finds the longest line in file
     */
    public void testFindLongestLine() {
        System.out.println("testFindLongestLine");
        Path input = Paths.get(INPUT_FILE);
        try {
            String longestLine = Files.lines(input)
                    .reduce((line1, line2)->{return (line1.length() > line2.length()) ? line1 : line2;})
                    .get();
            System.out.println("Longest line: " + longestLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * simpler way to find longest line in file
     */
    public void testFindLongestLineSimplest() {
        System.out.println("testFindLongestLineSimplest");
        Path input = Paths.get(INPUT_FILE);
        try {
            String longestLine = Files.lines(input)
                    .max(Comparator.comparingInt(String::length))
                    .get();
            System.out.println("Longest line (alternatively): " + longestLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileStream fs = new FileStream();
        fs.testFindMaxLineLength();
        fs.testFindLongestLine();
        fs.testFindLongestLineSimplest();
    }
}
