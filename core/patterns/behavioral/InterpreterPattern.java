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

        /**
         * Functional interface: whose instances are to be evaluated/interpreted/executed
         * For an input: w x z - + as in this example, the following anstract syntax tree
         * is built:
         * 
         *   Expr.plus(Expr.variable(w), Expr.minus(Expr.variable(x), Expr.variable(z)))
         */
        @FunctionalInterface
        public interface Expr {
            /**
             * The abstract functional method of Expr type, that starts the evaluation
             * of an Expr instance.
             * 
             * @param context
             * @return
             */
            int interpret(Map<String, Integer> context);

            /**
             * creates an instance of Expr, evaluated by applying operator (+) to the 
             * evaluated Expr parameters 
             * @param left
             * @param right
             * @return
             */
            static Expr plus(Expr left, Expr right) {
                return context -> left.interpret(context) + right.interpret(context);
            }

            /**
             * creates an instance of Expr, evaluated by applying operator (-) to the 
             * evaluated Expr parameters
             * 
             * @param left
             * @param right
             * @return
             */
            static Expr minus(Expr left, Expr right) {
                return context -> left.interpret(context) - right.interpret(context);
            }

            /**
             * creates an instance of Expr parameterized by variable 'v', that can be 
             * evaluated as: Expr(v) = ctx) => { ctx.getOrDefault(v, 0)
             * 
             * @param v
             * @return
             */
            static Expr variable(String v) {
                return context -> context.getOrDefault(v, 0);
            }
        }

        /**
         * Parses the input token using the current processing stack, the result is a
         * new instance of Expr created either from this token or from existing Exprs
         * on the stack.
         * @param token: input token to be parse
         * @param stack: input stack containing intermediate Expr during parsing process
         * @return an new instance of Expr
         */
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

        /**
         * Builds an abstract syntax tree (Expr) from input expression
         * @param expression
         * @return
         */
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

        // Java 9: Map<String, Integer> context = Map.of("w", 5, "x", 10, "z", 42);
        Map<String, Integer> context = new HashMap<String, Integer>();
        context.put("w", 05);
        context.put("x", 10);
        context.put("z", 42);

        // expected result = 5 + 10 - 42 = -27 
        int result = expr.interpret(context);
        System.out.println(result);
    }
}