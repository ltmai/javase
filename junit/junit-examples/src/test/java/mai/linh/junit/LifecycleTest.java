package mai.linh.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(LifecycleExtension.class)
public class LifecycleTest {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("*** beforeAll ***");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("*** beforeEach ***");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("*** afterEach ***");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("*** afterAll ***");
    }

    @Test
    public void lifecycleTest1() {
        System.out.println("Running test 1");
    }

    @Test
    public void lifecycleTest2() {
        System.out.println("Running test 2");
    }
}