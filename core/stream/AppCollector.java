import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lini on 12/20/16.
 * A Collector performs a mutable reduction on a stream
 * – Accumulates input elements into a mutable result container
 * – Results container can be a List, Map, String, etc
 * Use the collect() method to terminate the stream
 * Collectors utility class has many methods that can create a Collector
 */
public class AppCollector {
    /**
     * demonstrates creating collector by definition
     * <R> R collect(Supplier<R> supplier,
     *               BiConsumer<R, ? super T> accumulator,
     *               BiConsumer<R, R> combiner);
     * The code collects elements greater than 3 into a set of Integers (no duplication)
     */
    public void testCollectorSimple() {
         System.out.println("=====testCollectorSimple: creating a collector by definition");
         System.out.println("collect elements greater than 3 into a set of Integers");
         List<Integer> list = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 4, 4, 5, 6, 7, 7, 8, 9));
         HashSet<Integer> result = list.stream().collect(
                 HashSet::new,
                 (c, e)->{ if (e > 3) c.add(e); },
                 HashSet::addAll);
         result.stream().forEach(System.out::println);
    }

    /**
     * demonstrates creating collector even more succintly with method reference.
     * The code converts strings in stream into an ArranyList<String>.
     * ArrayList::new       supplier to instantiate a new ArrayList
     * ArrayList::add       accumulator to add element into result container
     * ArrayList::addAll    combiner to combine partial results (in parallel reduction)
     */
    public void testCollectorSimpler() {
        System.out.println("=====testCollectorSimpler: creating a collector using method reference");
        System.out.println("converts strings in stream into an ArrayList<String>");
        Stream<String> ss = Stream.of("apple", "orange", "banana", "peach");
        List<String> strings = ss.collect(
                ArrayList::new,
                ArrayList::add,
                ArrayList::addAll);
        strings.stream().forEach(System.out::println);
    }

    /**
     * demonstrates Collectors::collectingAndThan(Collector, Function) to make
     * the return list unmodifiable.
     *
     * static <T> Collector<T,?,List<T>>	Collectors::toList()
     * returns a Collector that accumulates the input elements into a new List
     */
    public void testCollectingAndThen() {
        System.out.println("=====testCollectingAndThan: using Collectors.toList()");
        System.out.println("return a unmodifiable list of elements with more than 3 characters");
        Stream<String> ss = Stream.of("x", "xx", "xxx", "xxxx", "xxxxx", "xxxxxx");
        List<String> result = ss.filter(s->s.length() > 3)
                                .collect(Collectors.collectingAndThen(Collectors.toList(),
                                                                      Collections::unmodifiableList));
        result.stream().forEach(System.out::println);
        try {
            //result.add("zzz");
            result.set(1, "zzz");
        } catch (Exception e) {
            System.out.println("You can modify neither elements nor the unmodifiable list ifself");
            e.printStackTrace();
        }
    }
    /**
     * demonstrates Collectors::groupingBy
     */
    public void testGroupingBy() {

    }
    /**
     * demonstrates Collectors::GroupingByConcurrent
     */
    public void testGroupingByConcurrent() {

    }
    /**
     * demonstrates Collectors::
     */
    public void testMapping() {

    }
    /**
     * demonstrates Collectors::
     */
    public void testPartioningBy() {

    }

    public static void main(String[] args) {
        AppCollector app = new AppCollector();
        // app.testCollectorSimple();
        app.testCollectorSimpler();
        // app.testCollectingAndThen();
        // app.testGroupingBy();
        // app.testGroupingByConcurrent();
        // app.testMapping();
        // app.testPartioningBy();
    }
}
