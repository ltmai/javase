package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * ServiceTest
 */
 @ExtendWith(DummyExtension.class)
 public class ServiceTest {

    /**
     * System Under Test (SUT)
     */
    private Service service = new Service();

    /**
     * Parameteried test using @CsvSource
     */
    @Tag("Parameterized")
    @DisplayName("@ParameterizedTest using @CsvSource")
    @ParameterizedTest(name = "Run #{index} : {0} replaced at position {2} by {1} becomes {3}")
    @CsvSource({
            // inputString  ,replacementString  ,position   ,expectedResult
            "____456789     ,0123               , 0         ,0123456789", 
            "0123456789     ,6789               , 6         ,0123456789",
            "0123456789     ,                   , 5         ,0123456789", 
            "               ,abcd               , 5         ,          ",
            "0123456789     ,abcd               ,-1         ,0123456789", 
            "0123456789     ,abcd               ,12         ,0123456789",})
    public void whenReplaceSubStringAtValidPosition_thenReturnsExpectedResults(String inputString, String replacementString, int position, String expectedResult) {
        assertEquals(expectedResult, 
                     service.replaceAt(inputString, replacementString, position),
                     () -> "replace " + inputString + " at position " + position + " should return " + expectedResult);
    }
    
    /**
     * Test with expected exception (invalid index leads to replacement string is
     * longer than substring to be replaced, results in exception)
     */
    @Test
    @DisplayName("@Test with expected exception StringIndexOutOfBoundsException")
    void whenReplaceSubStringAtInvalidPosition_thenExceptionIsThrown() {
        String inputString = "0123456___";
        String replacementString = "6789";
        int idx = inputString.length() - replacementString.length() + 1;

        Throwable exception = assertThrows(StringIndexOutOfBoundsException.class,
                () -> service.replaceAt(inputString, replacementString, idx));
        assertEquals("String index out of range: -1", exception.getMessage());
    }
}