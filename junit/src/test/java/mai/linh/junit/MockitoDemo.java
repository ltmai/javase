package mai.linh.junit;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * MockitoDemo
 */
@ExtendWith(MockitoExtension.class)
public class MockitoDemo {

    /**
     * Calulator#devide should throw ArithmeticException when deviding by zero
     */
    @Test
    public void stubbingMethodsWithExceptions() {
        Calculator calculator = Mockito.mock(Calculator.class);
        // given
        // alternatively: doThrow(ArithmeticException.class).when(calculator.devide(anyInt(), anyInt()));        
        given(calculator.devide(anyInt(), eq(0))).willThrow(new ArithmeticException());
        // then   
        assertThrows(ArithmeticException.class, () -> { calculator.devide(1, 0); });
    }

    @Test
    public void behaviorDrivenDevelopmentStyle() {
        @SuppressWarnings("rawtypes")
        List list = Mockito.mock(List.class);
        // given
        given(list.size()).willReturn(100);
        // then
        then(list.size()).isEqualTo(100);
    }
}