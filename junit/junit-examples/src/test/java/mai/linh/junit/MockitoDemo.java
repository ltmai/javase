package mai.linh.junit;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * MockitoDemo
 */
@ExtendWith(MockitoExtension.class)
public class MockitoDemo {


    @Mock
    private List<String> mockedList;
    
    /**
     * Calulator#devide should throw ArithmeticException when deviding by zero
     */
    @Test
    public void stubbingMethodsWithExceptions() {
        // given
        Calculator calculator = Mockito.mock(Calculator.class);
        // alternatively:
        // doThrow(ArithmeticException.class).when(calculator.devide(anyInt(), anyInt()));
        given(calculator.devide(anyInt(), eq(0))).willThrow(new ArithmeticException());
        // then
        assertThrows(ArithmeticException.class, () -> {
            calculator.devide(1, 0);
        });
    }

    @Test
    public void behaviorDrivenDevelopmentStyle() {
        // given
        given(mockedList.size()).willReturn(100);
        // then
        then(mockedList.size()).isEqualTo(100);
    }

    /**
     * use argThat() with custom ArgumentMatcher to verify arguments
     */
    @Test
    public void customArgumentMatcher() {
        mockedList.addAll(Arrays.asList("one", "two"));
        verify(mockedList).addAll(argThat(list -> list.size() == 2));      
    }
}