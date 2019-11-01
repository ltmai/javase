import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by lini on 12/6/16.
 * Demonstrates 4 main kinds of methods references (can be used in place of lambda)
 * 1. Static method
 * 2. Instance method
 * 3. Instance method of an existing object
 * 4. Constructor reference
 */
public class MethodReference {
    /**
     * refers to static method Integer::parseInt as Function<T,R>
     */
    public void testStaticMethod() {
        Function<String, Integer> converterFunction = Integer::parseInt;
        Integer i = converterFunction.apply("101");
        System.out.println("Apply static method to input String 101: " + i.toString());
    };

    /**
     * Save a reference to an instance method of type as 1st parameter to Function<T,R>
     * In this case Person::getFirstName() is an instance method of Person which is 1st
     * parameter of Function<Person, String>
     */
    public void testInstanceMethod() {
        Function<Person, String> personToString = Person::getFirstName;
        String firstName=personToString.apply(new Person("Albert", "Einstein", "Scientist", 99));
        System.out.println("Apply instance method to person: " + firstName);
    }

    /**
     * private helper function to print person full name
     * @param p
     */
    private void printPersonFullname(Person p) {
        System.out.println(p.getFirstName() + " " + p.getLastName());
    }

    /**
     * save a reference to instance method of an existing object, e.g. System.out.
     * This is also helpful to use a private helper method as Functional object.
     * In this case, printPersonFullname() is a private method which can also be
     * used in place of lambda.
     * In this case the instance method is a private member of the application itself.
     */
    public void testInstanceMethodOfExistingObject() {
        Consumer<Object> printSystem=System.out::println;
        Consumer<Person> printPerson=this::printPersonFullname;
        printSystem.accept("Apply private method to  person: ");
        printPerson.accept(new Person("Stephan", "Hawking", "Scientist", 70));
    }

    /**
     * save a reference to object constructor
     */
    public void testConstructorReference() {
        Supplier<List<String>> listOfString = ArrayList::new;
        List<String> list = listOfString.get();
        System.out.println("Apply a constructor reference to create new instance of List<String>");
        list.add("> first");
        list.add("> second");
        list.add("> third");
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {
        MethodReference methodReference = new MethodReference();

        methodReference.testStaticMethod();
        methodReference.testInstanceMethod();
        methodReference.testInstanceMethodOfExistingObject();
        methodReference.testConstructorReference();
    }
}
