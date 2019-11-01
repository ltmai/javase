import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

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
    public void testIntStream() {
        System.out.println("testIntStream");
        List<Integer> list = Arrays.asList(11, 2, 33, 14, 95, 46, 7, 58, 29, 60);

        OptionalInt maxValue=list.stream().mapToInt(i->i.intValue()).max();
        if (maxValue.isPresent())
        {
            System.out.println("Max value: " + maxValue.getAsInt());
        }
    }

    public void testDoubleStream() {
        System.out.println("testDoubleStream");
        List<Double> list = Arrays.asList(11.0, 2.0, 33.0, 14.0, 95.0, 46.0, 7.0, 58.0, 29.0, 60.0);

        double sum= list.stream().mapToDouble(i->i.doubleValue()).sum();
        System.out.println("Total Sum: " + sum);
    }

    public static void main(String[] args) {
        PrimitiveStream ps = new PrimitiveStream();
        ps.testIntStream();
        ps.testDoubleStream();
    }
}
