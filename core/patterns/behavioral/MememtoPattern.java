import java.util.ArrayList;
import java.util.List;

/**
 * Mememto pattern
 * This is the example of Mememto from Wikipedia.
 * https://en.wikipedia.org/wiki/Memento_pattern
 * 
 * The memento pattern is a software design pattern that provides the ability 
 * to restore an object to its previous state (undo via rollback).
 * 
 * The memento pattern is implemented with three objects: 
 * - the originator
 * - a caretaker and 
 * - a memento. 
 * 
 * The originator is some object that has an internal state. 
 * The caretaker is going to do something to the originator, but wants to be 
 * able to undo the change. 
 * The caretaker first asks the originator for a memento object. Then it does 
 * whatever operation (or sequence of operations) it was going to do. To roll 
 * back to the state before the operations, it returns the memento object to 
 * the originator. The memento object itself is an opaque object (one which 
 * the caretaker cannot, or should not, change). When using this pattern, care 
 * should be taken if the originator may change other objects or resources - 
 * the memento pattern operates on a single object.
 */
public class MememtoPattern {

    /**
     * Originator
     */
    static class Originator {
        private String state;
        // The class could also contain additional data that is not part of the
        // state saved in the memento..
     
        public void set(String state) {
            this.state = state;
            System.out.println("Originator: Setting state to " + state);
        }
     
        public Memento saveToMemento() {
            System.out.println("Originator: Saving to Memento.");
            return new Memento(this.state);
        }
     
        public void restoreFromMemento(Memento memento) {
            this.state = memento.getSavedState();
            System.out.println("Originator: State after restoring from Memento: " + state);
        }
     
        /**
         * Mememto
         */
        public static class Memento {
            private final String state;
    
            public Memento(String stateToSave) {
                state = stateToSave;
            }
     
            // accessible by outer class only
            private String getSavedState() {
                return state;
            }
        }
    }

    /**
     * Caretaker
     */
    static class Caretaker {
        public void test() {
            List<Originator.Memento> savedStates = new ArrayList<Originator.Memento>();
     
            Originator originator = new Originator();
            originator.set("State1");
            originator.set("State2");
            savedStates.add(originator.saveToMemento());
            originator.set("State3");
            // We can request multiple mementos, and choose which one to roll back to.
            savedStates.add(originator.saveToMemento());
            originator.set("State4");
     
            originator.restoreFromMemento(savedStates.get(1));   
        }
    }

    public static void main(String[] args) {
        new Caretaker().test();
    }
}