package test.linh.mai;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * DummyExtension This implementation is for demonstration purpose only. It may
 * not make sense or optimal.
 * 
 * @see https://junit.org/junit5/docs/5.0.3/api/org/junit/jupiter/api/extension/package-summary.html
 */
public class DummyExtension implements BeforeAllCallback, BeforeTestExecutionCallback, AfterAllCallback {

    private static final Namespace NAMESPACE = Namespace.create("mai", "linh", "DummyExtension");

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("====================================================");
    }

    /**
     * print parent display name for test runs annotated with @ParameterizedTest
     * At run-time each test run of a @ParameterizedTest is one of its children.
     * The child displays its own display name which is in form "Run #1 do test"
     * but the display name of the unit test is not shown. This extension checks 
     * for each test run if it has a parent with @ParameterizedTest, if it does
     * then print the parent display name (once).
     */
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        List<? extends Object> filter = Arrays.asList(ParameterizedTest.class);

        for (Annotation annotation : context.getElement().get().getAnnotations()) {
            if (filter.contains(annotation.annotationType())) {
                context.getParent().ifPresent(parentCtx -> {
                    if (parentCtx.getStore(NAMESPACE).get(parentCtx.getDisplayName()) == null) {
                        parentCtx.getStore(NAMESPACE).put(parentCtx.getDisplayName(), Boolean.TRUE);
                        System.out.println(parentCtx.getDisplayName());
                    }
                });
            }
        }
        System.out.println(context.getDisplayName());
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println("====================================================");
    }   
}