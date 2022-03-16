import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by lini on 12/22/16.
 */
public class AppStream {
    /**
     * demonstrates concatenating streams, if one of the operands
     * is a parallel stream, the result is also a parallel stream
     */
    public void testStreamConcat() {
        System.out.println("testStreamConcat: concatenate 2 streams");
        IntStream s1 = IntStream.rangeClosed(1,5).parallel();
        IntStream s2 = IntStream.rangeClosed(6,10);
        IntStream.concat(s1,s2).forEach(System.out::println);
    }

    /**
     * demonstrates using a Consumer that does nothing to set breakpoint
     */
    public void testStreamDebugging() {
        System.out.println("testStreamDebugging: use a no-op lambda to set breakpoint");
        List<String> sortedWords = Stream.of("one two", "two three", "three four", "four five", "five five")
                .flatMap(line -> {
                    return Stream.of(line.split(" "));
                })
                .peek(s -> { /*SET A BREAKPOINT HERE*/ })
                .map(String::toLowerCase)
                .distinct()
                .sorted((String x, String y) -> (x.length() - y.length()))
                .collect(Collectors.toList());
        System.out.println("Words sorted by length: ");
        sortedWords.stream().forEach(System.out::println);
    }

    /**
     * demonstrates return inside lambda does not break stream processing
     * @param args
     */
    public void testReturnInsideForEach() {
        System.out.println("testReturnInsideForEach");
        Stream.of("one", "two", "three", "four").forEach(s -> {
           System.out.print(" >>>>> " + s);
           if (s.equals("three")) {
               System.out.println();
               return;
           }
           System.out.println(" <<<<< ");
        });
    }

    public static void main(String[] args) {
        AppStream as = new AppStream();
        //as.testStreamConcat();
        //as.testStreamDebugging();
        as.testReturnInsideForEach();
    }
}
