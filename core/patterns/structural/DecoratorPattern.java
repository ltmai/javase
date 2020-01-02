/**
 * DecoratorPattern
 * 
 * Decorator pattern lets you attach new behaviors to existing object without
 * changing it.
 * 
 * In the following example:
 * 
 * Interface Pizza: the interface to objects that can be decorated.
 * 
 * Class SimplePizza: a concrete implementation of decoration-able.
 * 
 * Class PizzaWithCheese: a concrete decorator that extends the interface.
 * 
 * Class PizzaWithSalami: a concrete decorator that extends the interface.
 */
public class DecoratorPattern {

    /**
     * Pizza
     */
    public static interface Pizza {
        public String make();
    }

    /**
     * Pizza implementation
     */
    public static class SimplePizza implements Pizza {
        @Override
        public String make() {
            return "Pizza";
        }
    }

    /**
     * Decorator: add Cheese
     */
    public static class PizzaWithCheese implements Pizza {
        private Pizza pizza;
    
        public PizzaWithCheese(Pizza pizza) {
            this.pizza = pizza;
        };

        @Override
        public String make() {
            return pizza.make() + " with Cheese";
        }
    }

    /**
     * Decorator: add Salami
     */
    public static class PizzaWithSalami implements Pizza {
        private Pizza pizza;
        
        public PizzaWithSalami(Pizza pizza) {
            this.pizza = pizza;
        };

        @Override
        public String make() {
            return pizza.make() + " with Salami";
        }
    }

    public static void main(String[] args) {
        System.out.println(new PizzaWithSalami(new PizzaWithCheese(new SimplePizza())).make());
    }
}