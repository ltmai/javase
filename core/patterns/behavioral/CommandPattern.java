import java.util.Arrays;
import java.util.List;

/**
 * The Command pattern suggests that modules encapsulate Request
 * together with all necessary information to process that Request. 
 * 
 * This transformation lets you parameterize methods with different 
 * requests, delay or queue a request's execution, and support undoable 
 * operations
 * 
 * In the following example:
 * 
 * Interface Command: the interface to all Commands.
 * 
 * Class ExpensiveTask: A concrete Command implementation.
 * 
 * Class ComplexTask: A concrete Command implementation.
 */
public class CommandPattern {

    /**
     * Abstract Command
     */
    public interface Command {
        void execute();
    }

    /**
     * Concrete command
     * Encapsulates the necessary parameters and procedure to be executed
     */
    public static class ExpensiveTask implements Command {
        private String input;

        /**
         * Constructor creates an instance of ExpensiveTask with
         * all required parameters to execute the task.
         * @param input
         */
        public ExpensiveTask(String input) {
            this.input = input;
        }

        /**
         * Execute the task
         */
        @Override
        public void execute() {
            System.out.println("[Expensive-Task] Processing " + input);
        }
    }

    /**
     * Concrete command
     * Encapsulates the necessary parameters and procedure to be executed
     */
    public static class ComplexTask implements Command {
        private List<Integer> input;

        /**
         * Constructor creates an instance of ComplexTask with
         * all required parameters to execute the task
         * @param input
         */
        public ComplexTask(List<Integer> input) {
            this.input = input;
        }

        /**
         * Execute the concrete task
         */
        @Override
        public void execute() {
            System.out.println("[Complex-Task] Processing " + input);
        }
    }

    public static void main(String[] args) {
        Command cmd1 = new ExpensiveTask("EXPENSIVE");
        Command cmd2 = new ComplexTask(Arrays.asList(1,2,3,4,5));

        Arrays.asList(cmd1, cmd2).forEach(cmd -> cmd.execute());
    }
}