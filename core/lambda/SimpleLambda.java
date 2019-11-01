import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.text.DateFormatter;

/**
 * demonstrates implementing several @FunctionalInterface with lambda
 */
public class SimpleLambda {
    static List<Person> people = new ArrayList<>();

    public static void preparePeopleList() {
        people = new ArrayList<>();
        people.add(new Person("Louis", "Amstrong", "Singer", 80));
        people.add(new Person("James", "Brown",    "Singer", 70));
        people.add(new Person("Bob"  , "Marley"  , "Singer", 60));
    }

    /**
     * demonstrates implementing the functional interface Runnable with lambda
     */
    public static void testRunnableLambda() {
        System.out.println("testRunnableLambda: start a thread with lambda");
        Runnable r = () -> System.out.println("Runnable lambda");

        Thread t = new Thread(r);
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * demonstrates implementing the @FunctionalInterface Comparable with lambda
     */
    public static void testComparatorLambda() {
        System.out.println("testComparatorLambda: sort people after first name");
        Collections.sort(people, (Person p1, Person p2) -> p1.getFirstName().compareTo(p2.getFirstName()));
        people.forEach(Person::print);
        //alternatively refactored as follows
        //people.stream().sorted(Comparator.comparing(Person::getFirstName)).forEach(Person::print);
    }

    /**
     * demonstrates creating thread-local variable using ThreadLocal.withInitial()
     * DateFormatter is not thread-safe, a ThreadLocal<DateFormatter> instance is
     * however thread-safe.
     */
    public static void testThreadLocal() {
        System.out.println("testThreadLocal: create thead local variable");
        @SuppressWarnings("unused")
		ThreadLocal<DateFormatter> formatter = ThreadLocal.withInitial(DateFormatter::new);
    }

    public static void main(String[] args) {
        preparePeopleList();
        testRunnableLambda();
        testComparatorLambda();
        testThreadLocal();
    }
}
