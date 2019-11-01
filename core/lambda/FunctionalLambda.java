import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * demonstrates usages of standard functional interfaces defined in java.util.function
 *
 * https://docs.oracle.com/javase/8/docs/api/index.html?java/lang/FunctionalInterface.html
 */
public class FunctionalLambda {
    static List<Person> people;

    public static void preparePeopleList() {
        people = new ArrayList<>();
        people.add(new Person("Louis", "Amstrong", "Singer", 80));
        people.add(new Person("James", "Brown",    "Singer", 70));
        people.add(new Person("Bob"  , "Marley"  , "Singer", 60));
    }

    /**
     * demonstrates @FunctionalInterface Predicate<T>
     * Predicate represents an operation to test a condition.
     * interface Predicate<T> {
     *     public boolean test(T t);
     * }
     */
    public static void testPredicate() {
        System.out.println("testPredicate: filter people older than 60");
        Predicate<Person> predicateOver60 = (person -> person.getAge() > 60);
        people.stream().filter(predicateOver60).forEach(Person::print);
    }

    /**
     * demonstrates @FunctionalInterface Consumer<T>
     * Consumer<T> represents an operation that accepts a single input
     * argument and returns no result.
     * public interface Consumer<T> {
     *     void accept(T t);
     *     default Consumer<T>	andThen(Consumer<? super T> after);
     * }
     */
    public static void testConsumer() {
        System.out.println("testConsumer: chaining operations with Consumer");
        Consumer<Person> firstAction = (person -> System.out.print(">>> "));
        Consumer<Person> nextAction  = (person -> person.print());
        people.forEach(firstAction.andThen(nextAction));
    }

    /**
     * demonstrates @FunctionalInterface Function<T, R>
     * R Function<T, R> represents an operation on input that returns
     * result of another type.
     * public interface Function<T> {
     *      R apply(T t);
     * }
     */
    public static void testFunction() {
        System.out.println("testFunction: use Function with mapping");
        Function<Person, String> getName = (person -> { return person.getFirstName() + " " +  person.getLastName(); });
        people.stream().map(getName).forEach(System.out::println);
    }

    /**
     * demonstrates @FunctionalInterface Supplier<T>
     * Supplier represents a supplier of results (no input).
     */
    public static void testSupplier() {
        System.out.println("testSupplier");
        Random random = new Random();
        Supplier<Integer> randomIntSupplier = random::nextInt;
        System.out.println("Supply an random integer: " + randomIntSupplier.get()); 
    }

    /**
     * demonstrates @FunctionalInterface UnaryOperator<T>
     * UnaryOperator represents an operation on a single operand
     * that produces a result of the same type as its operand
     */
    public static void testUnaryOperator() {
        System.out.println("testUnaryOperator");
        UnaryOperator<String> uniOp = String::toUpperCase;
        System.out.println("Unary Operator converts string to upper: " + uniOp.apply("i am originally lower case"));
    }

    /**
     * demonstrates @FunctionalInterface BinaryOperator<T>
     * BinaryOperator represents an operation upon two operands of the
     * same type, producing a result of the same type as the operands.
     */
    public static void testBinaryOperator() {
        System.out.println("testBinaryOperator");
        BinaryOperator<String> biOp = String::concat;
        System.out.println("Binary Operator concatenates strings :" + biOp.apply("111", "222"));
    }

    /**
     * main
     * @param args
     */
    public static void main(String[] args) {
        preparePeopleList();
        testPredicate();
        testFunction();
        testConsumer();
        testSupplier();
        testUnaryOperator();
        testBinaryOperator();
    }


}
