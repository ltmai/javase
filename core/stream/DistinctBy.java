import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Function;


class DistinctBy {

    static class Person {
        private String name;
        private long age;

        public Person(String name, long age) {
            this.name = name;
            this.age = age;
        }

        public void setName(String name) { this.name = name; } 
        public void setAge(long age) { this.age = age; } 
        public String getName() { return name; }
        public long getAge() { return age; }

        public String toString() {
            return String.format("Person [name=%s, age=%d]", name, age);
        }
    }

    public static <K,T> Predicate<K> createDistinctBy(Function<K, T> getter) {
        return new Predicate<K>() {
            private List<T> values = new ArrayList<>();

            public boolean test(K obj) {
                T value = getter.apply(obj);
                if (values.contains(value)) return false;
                values.add(value);
                return true;
            }
        };
    }

    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();

        people.add(new Person("Alex", 20));
        people.add(new Person("Alex", 21));
        people.add(new Person("Ben",  22));
        people.add(new Person("Ben",  23));
        people.add(new Person("Clara",24));
        people.add(new Person("Clara",25));
        people.add(new Person("Dora", 26));

        var distinctByName = createDistinctBy(Person::getName);
        var distinctByAge  = createDistinctBy(Person::getAge);

        System.out.println("------ Person distinct by name:");
        people.stream().filter(distinctByName).forEach(System.out::println);

        System.out.println("------ Person distinct by age:");
        people.stream().filter(distinctByAge).forEach(System.out::println);
    }
}
