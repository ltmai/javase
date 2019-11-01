import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by lini on 12/20/16.
 */
public class AppReduce {
    /**
     * T	reduce(T identity,
     *             BinaryOperator<T> accumulator)
     * starting with an initial value (default result) accumulates input
     * elements together into a single result of the same type
     */
    public void testReduceWithDefaultResult() {
        System.out.println("testReduceWithDefaultResult");
        IntStream intStream = IntStream.rangeClosed(0, 9);
        int maxInt = intStream.reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println("Max value: " + maxInt);
    }

    /**
     * <U> U    reduce(U identity,
     *                 BiFunction<U,? super T,U> accumulator,
     *                 BinaryOperator<U> combiner)
     * accumulates input elements together into a result of different
     * type specified by parameter identity
     */
    public void testReduceConcatStrings() {
        System.out.println("testReduceConcatStrings");
        Stream<String> ss = Stream.of("One", "Two", "Three", "Four", "Five", "Six");
        StringBuilder result = ss.reduce(
                new StringBuilder(),
                (StringBuilder sb, String s)->sb.append(s),
                (sb1, sb2)->sb1.append(sb2));
        System.out.println("Concatenated: " + result.toString());
    }

    /**
     * Optional<T>	reduce(BinaryOperator<T> accumulator)
     * accumulates input elements into a single result of the same type if any
     */
    public void testReduceWithoutDefaultDefaultResult() {
        System.out.println("testReduceWithoutDefaultDefaultResult");
        IntStream is = IntStream.of();
        OptionalInt result = is.reduce(Integer::sum);
        if (result.isPresent()) {
            System.out.println("Sum of all values: " + result.getAsInt());
        } else {
            System.out.println("Reduce of an empty stream gives no result");
        }
    }

    public static void main(String[] args) {
        AppReduce ar = new AppReduce();
        ar.testReduceWithDefaultResult();
        ar.testReduceWithoutDefaultDefaultResult();
        ar.testReduceConcatStrings();
    }
}
