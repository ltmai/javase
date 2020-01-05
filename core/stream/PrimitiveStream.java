import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Created by lini on 12/17/16.
 * Demonstrates operations with primitive streams: StreamInt, StreamDouble, StreamLong
 */
public class PrimitiveStream {
    /**
     * The stream terminal operations are either for
     * 1. Object stream: e.g. count(), max(Comparator), min(Comparator)
     * 2. Primitive stream: e.g. average(), sum()
     *
     * mapToInt() returns an IntStream to avoid boxing/unboxing
     */
    public static void testIntStream() {
        OptionalInt maxValue=IntStream.of (11, 2, 33, 14, 95, 46, 7, 58, 29, 60).max();
        if (maxValue.isPresent())
        {
            System.out.println("Max value: " + maxValue.getAsInt());
        }
    }

    public static void testDoubleStream() {
        double sum = DoubleStream.of(11.0, 2.0, 33.0, 14.0, 95.0, 46.0, 7.0, 58.0, 29.0, 60.0).sum();
        System.out.println("Total Sum: " + sum);
    }

    public static void testLongStream() {
        OptionalDouble avr = LongStream.of(1L, 2L, 3L, 4L, 5L).average();
        System.out.println("Average: " + avr.orElse(0L));
    }

    public static void main(String[] args) {
        testIntStream();
        testDoubleStream();
        testLongStream();
    }
}
