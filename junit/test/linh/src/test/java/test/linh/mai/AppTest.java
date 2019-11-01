package test.linh.mai;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(DummyExtension.class)
public class AppTest {

    private static final String INPUT_STRING = "input";
    private static final String INPUT_UPPERCASE = "INPUT";
    private static final String DUMMY_ELEMENT = "dummy";

    @Mock
    private Service serviceMock;

    @InjectMocks
    private App app;

    @Captor
    private ArgumentCaptor<String> argumentCaptor;

    @BeforeAll
    static void setUp() {
    }

    @BeforeEach
    void setUpTest(TestInfo testInfo) {
    }

    /**
     * Parameterized test with @ValueSource
     */
    @Tag("Parameterized")
    @DisplayName("Parameterized test with @ValueSource")
    @ParameterizedTest(name = "run #{index} with argument [{arguments}]")
    @ValueSource(strings = { "Hello", "JUnit" })
    public void withValueSource(String word, TestInfo info, TestReporter reporter) {
        reporter.publishEntry(info.getDisplayName(), "Word: " + word);
        assertNotNull(word);
    }

    /**
     * assumeTrue() to test a pre-condition before continuing with test
     */
    @Test
    @DisplayName("Assumme precondition before executing test")
    void whenPreconditionNotSatisfied_TestWillNotContinue() {
        boolean preconditionSatisfied = (2 > 1);
        assumeTrue(preconditionSatisfied);

        // the folowing will not be executed at all if the precondition is not satisfied
        System.out.println("Pre-condition satisfied, continue with test");
        assertAll(() -> assertEquals(1, 1, "1 is not equal to 1"));
    }

    /**
     * Simple test with Mock object
     */
    @Test
    @DisplayName("Simple test with Mock object")
    void whenServiceToUpper_thenCorrectResult() {
        when(serviceMock.toUpper(INPUT_STRING)).thenReturn(INPUT_STRING.toUpperCase());

        String output = app.toUpper( INPUT_STRING);
        assertEquals(INPUT_UPPERCASE, output);

        // verify that the method was called
        verify(serviceMock).toUpper(Mockito.anyString());
    }

    /**
     * Mockito Mock vs. Spy: When Mockito creates a mock, it does so from the Class
     * of a Type, not from an actual instance. The mock simply creates a bare-bones
     * shell instance of the Class, entirely instrumented to track interactions with
     * it.
     * 
     * On the other hand, the spy will wrap an existing instance. It will still
     * behave in the same way as the normal instance. The only difference is that it
     * will also be instrumented to track all the interactions with it.
     * 
     * when(...).thenReturn(...) vs. doReturn(...).when(...): They behave only
     * differently when it comes to spied object:
     * 
     * when(...) thenReturn(...) makes a real method call just before the specified
     * value will be returned. So if the called method throws an Exception you have
     * to deal with it / mock it etc. Of course you still get your result (what you
     * define in thenReturn(...))
     * 
     * doReturn(...) when(...) does not call the method at all.
     */
    @Test
    public void whenUsingSpy_thenObjectIsSpied() 
    {
        List<String> list = new ArrayList<String>();
        List<String> spyList = Mockito.spy(list);

        // The following would result in IndexOutOfBoundsException since
        // the real method call is made on the list which is empty:
        // Mockito.when(spyList.get(Mockito.anyInt())).thenReturn(DUMMY_ELEMENT);
        // assertEquals(spyList.get(0), DUMMY_ELEMENT);

        assertEquals(0, spyList.size());
        // doReturn(...) when(...) does not call the real method 
        doReturn(DUMMY_ELEMENT).when(spyList).get(Mockito.anyInt());
        assertEquals(DUMMY_ELEMENT, spyList.get(100));

        verify(spyList, Mockito.atLeastOnce()).get(Mockito.anyInt());
    }

    /**
     * Use ArgumentCaptor to capture parameters
     */
    @Test
    public void testCaptor() 
    {       
        Service serviceMock = Mockito.mock(Service.class);
        serviceMock.toUpper(INPUT_STRING);
        
        verify(serviceMock).toUpper(argumentCaptor.capture());
        final String capturedArgument = argumentCaptor.getValue();
        assertThat(capturedArgument, Matchers.equalTo(INPUT_STRING));
    }
}