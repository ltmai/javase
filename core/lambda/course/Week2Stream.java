import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by lini on 12/10/16.
 */
public class Week2Stream {
    private static final String INPUT_FILE = "..\\..\\resources\\input\\SonnetI.txt";
    private static final String WORD_REGEXP = "[- .:,]+";

    /**
     * Run the exercises to ensure we got the right answers
     *
     * @throws java.io.IOException
     */
    public void runExercises() throws IOException {
        System.out.println("JDK 8 Lambdas and Streams MOOC Lesson 2");
        System.out.println("Running exercise 1 solution...");
        exercise1();
        System.out.println("Running exercise 2 solution...");
        exercise2();
        System.out.println("Running exercise 3 solution...");
        exercise3();
        System.out.println("Running exercise 4 solution...");
        exercise4();
        System.out.println("Running exercise 5 solution...");
        exercise5();
        System.out.println("Running exercise 6 solution...");
        exercise6();
        System.out.println("Running exercise 7 solution...");
        exercise7();
    }

    /**
     * Exercise 1
     *
     * Create a new list with all the strings from original list converted to
     * lower case and print them out.
     */
    private void exercise1() {
        List<String> list = Arrays.asList(
                "The", "Quick", "BROWN", "Fox", "Jumped", "Over", "The", "LAZY", "DOG");

        /* YOUR CODE HERE */
        list.stream().map(String::toLowerCase).forEach(System.out::println);
    }

    /**
     * Exercise 2
     *
     * Modify exercise 1 so that the new list only contains strings that have an
     * odd length
     */
    private void exercise2() {
        List<String> list = Arrays.asList(
                "The", "Quick", "BROWN", "Fox", "Jumped", "Over", "The", "LAZY", "DOG");

        /* YOUR CODE HERE */
        list.stream().map(String::toLowerCase)
                     .filter(s->s.length()%2==1)
                     .forEach(System.out::println);
    }

    /**
     * Exercise 3
     *
     * Join the second, third and forth strings of the list into a single string,
     * where each word is separated by a hyphen (-). Print the resulting string.
     */
    private void exercise3() {
        List<String> list = Arrays.asList(
                "The", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");

        /* YOUR CODE HERE */
        Stream<String> stream = Stream.of(list.get(1),list.get(2),list.get(3));
        System.out.println(stream.reduce((s1,s2)->s1+"-"+s2).toString());
    }

    /**
     * Count the number of lines in the file using the BufferedReader provided
     */
    private void exercise4() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(INPUT_FILE), StandardCharsets.UTF_8)) {
            /* YOUR CODE HERE */
            System.out.println("Number of lines: " + reader.lines().count());
        }
    }

    /**
     * Using the BufferedReader to access the file, create a list of words with
     * no duplicates contained in the file.  Print the words.
     *
     * HINT: A regular expression, WORD_REGEXP, is already defined for your use.
     */
    private void exercise5() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(INPUT_FILE), StandardCharsets.UTF_8)) {
            /* YOUR CODE HERE */
            reader.lines().map(String::toLowerCase)
                          .flatMap(line -> Stream.of(line.split(WORD_REGEXP)))
                          .distinct()
                          .forEach(System.out::println);
        }
    }

    /**
     * Using the BufferedReader to access the file create a list of words from
     * the file, converted to lower-case and with duplicates removed, which is
     * sorted by natural order.  Print the contents of the list.
     */
    private void exercise6() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(INPUT_FILE), StandardCharsets.UTF_8)) {
            /* YOUR CODE HERE */
            reader.lines().map(String::toLowerCase)
                    .flatMap(line -> Stream.of(line.split(WORD_REGEXP)))
                    .distinct()
                    .sorted(String::compareTo)
                    .forEach(System.out::println);
        }
    }

    /**
     * Modify exercise6 so that the words are sorted by length
     */
    private void exercise7() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(INPUT_FILE), StandardCharsets.UTF_8)) {
            /* YOUR CODE HERE */
            reader.lines().map(String::toLowerCase)
                    .flatMap(line -> Stream.of(line.split(WORD_REGEXP)))
                    .distinct()
                    .sorted(Comparator.comparing(String::length))
                    .forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        try {
            new Week2Stream().runExercises();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
