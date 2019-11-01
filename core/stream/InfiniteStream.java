import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.DoubleStream;

/**
 * Created by lini on 12/19/16.
 */
public class InfiniteStream {
    /**
     * to terminates an infinite stream when reading elements use either
     * findFirst()...........to find matching element
     * findAny().............to verify a condition (e.g. given element exists)
     * both returns an OptionalInt describing the first element of this stream,
     * or an empty OptionalInt if the stream is empty.
     * Note findAny() may or may not return the first matching element, if the
     * condition is matched, it is free to return any element in the stream
     * (non-deterministic). This is to allow maximal performance in parallel
     * operations;
     * System.out.println(IntStream.range(0, 100).parallel().findAny());
     */
    public void testTerminateAnInfiniteStream() {
        System.out.println("testTerminiateAnInfiniteStream");
        Random random = new Random();
        OptionalInt firstElementGreaterThan100= random.ints()
                .peek(i->{System.out.println("Next random int: " + i);})
                .filter(i->i>256)
                .findFirst();
        if (firstElementGreaterThan100.isPresent()) {
            System.out.println("First element greater than 100 is: "  + firstElementGreaterThan100.getAsInt());
        }
    }

    /**
     * demonstrates infinite stream, basically just like finite stream.
     * Note that
     * - forEach is a terminal operator (does not mean the stream terminates)
     * - forEach consumes the elements but does not terminiate the stream
     */
    public void testUsingInfiniteStream() {
        System.out.println("testUsingInfiniteStream: press Ctrl+C to stop");
        Random random = new Random();
        DoubleStream.generate(random::nextDouble)
                .map(d->d+1)
                .peek(i->{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .forEach(d->System.out.println("Processing input: " + d));
    }

    public static void main(String[] args) {
        InfiniteStream is = new InfiniteStream();
        is.testTerminateAnInfiniteStream();
        is.testUsingInfiniteStream();
    }
}
