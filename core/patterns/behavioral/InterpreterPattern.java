import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * Interpreter pattern
 * This is the example of interpreter pattern from Wikipedia
 * https://en.wikipedia.org/wiki/Interpreter_pattern
 * 
 * What solution does the Interpreter design pattern describe?
 * 
 * - Define a grammar for a simple language by defining an Expression class 
 *   hierarchy and implementing an interpret() operation.
 * - Represent a sentence in the language by an abstract syntax tree (AST) 
 *   made up of Expression instances.
 * - Interpret a sentence by calling interpret() on the AST.
 * 
 * The expression objects are composed recursively into a composite/tree 
 * structure that is called abstract syntax tree (see Composite pattern).
 * 
 * The Interpreter pattern doesn't describe how to build an abstract syntax 
 * tree. This can be done either manually by a client or automatically by a 
 * parser.
 */
public class InterpreterPattern {

    static class Interpreter {
        @FunctionalInterface
        public interface Expr {
            int interpret(Map<String, Integer> context);

            static Expr number(int number) {
                return context -> number;
            }

            static Expr plus(Expr left, Expr right) {
                return context -> left.interpret(context) + right.interpret(context);
            }

            static Expr minus(Expr left, Expr right) {
                return context -> left.interpret(context) - right.interpret(context);
            }

            static Expr variable(String name) {
                return context -> context.getOrDefault(name, 0);
            }
        }

        private static Expr parseToken(String token, ArrayDeque<Expr> stack) {
            Expr left, right;

            switch (token) {
            case "+":
                right = stack.pop();
                left = stack.pop();
                return Expr.plus(left, right);
            case "-":
                right = stack.pop();
                left = stack.pop();
                return Expr.minus(left, right);
            default:
                return Expr.variable(token);
            }
        }

        public static Expr parse(String expression) {
            ArrayDeque<Expr> stack = new ArrayDeque<Expr>();

            for (String token : expression.split(" ")) {
                stack.push(parseToken(token, stack));
            }
            return stack.pop();
        }
    }

    public static void main(String[] args) {
        Interpreter.Expr expr = Interpreter.parse("w x z - +");
        // Map<String, Integer> context = Map.of("w", 5, "x", 10, "z", 42);
        Map<String, Integer> context = new HashMap<String, Integer>();
        context.put("w", 05);
        context.put("x", 10);
        context.put("z", 42);

        int result = expr.interpret(context);
        System.out.println(result);
    }
}