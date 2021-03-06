package mai.linh.junit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(TestNameExtension.class)
@DisplayNameGeneration(SimpleNameGenerator.class)
public class JUnit5Demo {

    private static final String INPUT_STRING = "input";
    private static final String DUMMY_ELEMENT = "dummy";

    @Mock
    private StringUtils utilsMock;

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
     * @ValueSource is an ArgumentsSource which provides access to an array of literal values.
     * Supported types include: shorts, bytes, ints, longs, floats, doubles, chars, strings, and classes.
     * 
     * TestInfo: allows you to get information related to a test suite or test method, such as display name, tags, test class.
     * TestReporter: is used to print test information to stout or stderr.
     */
    @Tag("Parameterized")
    @DisplayName("Parameterized test with @ValueSource")
    @ParameterizedTest(name = "Run #{index} with argument [{arguments}]")
    @ValueSource(floats = { 1.12f, 2.34f })
    public void ParameterizedTest_withValueSource(Float f, TestInfo info, TestReporter reporter) {
        assertNotNull(f);
    }

    /**
     * assumeTrue() to test a pre-condition before continuing with test
     */
    @Test
    @DisplayName("Assumme precondition before executing test")
    void whenPreconditionNotSatisfied_TestWillNotContinue() {
        boolean preconditionSatisfied = (1 > 2);
        assumeTrue(preconditionSatisfied);

        // the folowing will not be executed at all if the precondition is not satisfied
        System.out.println("Pre-condition satisfied, continue with test");
    }

    /**
     * Mockito Mock vs. Spy: When Mockito creates a mock, it does so from the Class
     * of a Type, not from an actual instance. The mock simply creates a bare-bones
     * shell instance of the Class, entirely instrumented to track interactions with
     * it.
     * 
     * On the other hand, the spy will <b>wrap an existing instance</b>. It will still
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
    public void usingMockitoSpy_toWrappAnExistingObject() 
    {
        // given
        List<String> emptyList = new ArrayList<String>();
        List<String> spyList = Mockito.spy(emptyList);

        // The following would result in IndexOutOfBoundsException since
        // Mockito.when(...).thenReturn(...) makes the real method call 
        // on the list which is empty:
        // Mockito.when(spyList.get(Mockito.anyInt())).thenReturn(DUMMY_ELEMENT);
        // assertEquals(spyList.get(0), DUMMY_ELEMENT);
        // Since doReturn(...) when(...) does not call the real method 
        // we can use it with the spied object (wrapper of an existing one).

        assertEquals(0, spyList.size());
        doReturn(DUMMY_ELEMENT).when(spyList).get(Mockito.anyInt());

        // then
        assertEquals(DUMMY_ELEMENT, spyList.get(100));
        verify(spyList, Mockito.atLeastOnce()).get(Mockito.anyInt());
    }

    /**
     * 
     */
    @Test
    public void usingMockitoMock_toCreateMockObject() {
        // given
        @SuppressWarnings("rawtypes")
        List emptyList = Mockito.mock(List.class);
        // when
        Mockito.when(emptyList.get(Mockito.anyInt())).thenReturn(DUMMY_ELEMENT);
        // then
        assertEquals(DUMMY_ELEMENT, emptyList.get(100));
        verify(emptyList, Mockito.atLeastOnce()).get(Mockito.anyInt());
    }

    /**
     * Use ArgumentCaptor to capture parameters
     */
    @Test
    public void usingArgumentCaptor_toCaptureMethodParameters() 
    {       
        // given
        StringUtils serviceMock = Mockito.mock(StringUtils.class);
        // when
        serviceMock.replaceAt(INPUT_STRING, "replacement", 0);
        // then
        verify(serviceMock).replaceAt(argumentCaptor.capture(), anyString(), anyInt());
        assertThat(argumentCaptor.getValue(), Matchers.equalTo(INPUT_STRING));
    }

    @Disabled("Disabled until bug #12345 is fixed")
    @Test    
    public void disabledTest_willNotBeRun() {
    }    
}