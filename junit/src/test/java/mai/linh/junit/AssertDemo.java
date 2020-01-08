package mai.linh.junit;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Built-in Junit assertion demo
 */
@ExtendWith(TestNameExtension.class)
@ExtendWith(MockitoExtension.class)
public class AssertDemo {

    @Test
    void standardAssertions() {
        assertEquals(2, 1 + 1, () -> "messageSupplier");
        assertTrue(1 < 2, () -> "messageSupplier");
        assertNotNull("");
        assertAll("All of the following", 
            () -> assertEquals(2, 1 + 1), 
            () -> assertEquals(3, 1 + 2)
        );
    }

    @Test
    void exceptionTesting() {
        Calculator calculator = new Calculator();
        Exception exception = assertThrows(ArithmeticException.class, () -> { calculator.devide(1, 0); });
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    void timeoutNotExceeded() {
        assertTimeout(ofMinutes(2), () -> {
        });
    }

    @Test
    void timeoutNotExceededWithResult() {
        String actualResult = assertTimeout(ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutNotExceededWithMethodReference() {
        String actualGreeting = assertTimeout(ofMinutes(2), AssertDemo::greeting);
        assertEquals("Hello, World!", actualGreeting);
    }

    @Test
    void timeoutExceededWithPreemptiveTermination() {
        assertTimeoutPreemptively(ofMillis(10), () -> {
            Thread.sleep(10);
        });
    }

    private static String greeting() {
        return "Hello, World!";
    }
}