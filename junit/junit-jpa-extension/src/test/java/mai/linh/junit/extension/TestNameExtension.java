package mai.linh.junit.extension;

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
 * @see https://blog.codefx.org/design/architecture/junit-5-extension-model
 */
public class TestNameExtension implements BeforeAllCallback, BeforeTestExecutionCallback, AfterAllCallback {
    
    private static final Namespace NAMESPACE = Namespace.create("mai", "linh", "DummyExtension");
    
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println("[Extension]====================================================");
    }  
    
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("[Extension]====================================================");
    }

    /**
     * print parent display name for test runs annotated with @ParameterizedTest
     * At run-time each test run of a @ParameterizedTest is one of its children.
     * The child displays its own display name which is in form "Run #1 do test"
     * but the display name of the unit test is not shown. This extension checks 
     * for each test run if it has a parent with @ParameterizedTest, if it does
     * then print the parent display name (once).
     * 
     * The ExtensionContext interface is an instance of which is passed to every 
     * extension point’s method. It allows extensions to access information 
     * regarding the running test and also to interact with the Jupiter machinery.
     * 
     * Extensions have to be stateless. Any state they need to maintain has to be 
     * written to and loaded from the store that the extension context makes 
     * available. A store is a namespaced, hierarchical, key-value data structure.
     */
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        List<? extends Object> annotationFilter = Arrays.asList(ParameterizedTest.class);

        for (Annotation annotation : context.getElement().get().getAnnotations()) {
            if (annotationFilter.contains(annotation.annotationType())) {
                context.getParent().ifPresent(parentCtx -> {
                    if (parentCtx.getStore(NAMESPACE).get(parentCtx.getDisplayName()) == null) {
                        parentCtx.getStore(NAMESPACE).put(parentCtx.getDisplayName(), Boolean.TRUE);
                        System.out.println("[Extension] Test: " + parentCtx.getDisplayName());
                    }
                });
            }
        }
        
        System.out.println("[Extension] Test: " + context.getDisplayName()); // test run
    }

}
