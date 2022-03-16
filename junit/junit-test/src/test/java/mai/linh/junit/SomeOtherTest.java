package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class SomeOtherTest {
   @Test
   void splitString () {
        var output = List.of("abc , def , ghi".split(",")).stream().map(String::trim).collect(Collectors.toList());

        assertAll(
           ()->assertEquals(3, output.size()),
           ()->assertTrue(output.containsAll(List.of("abc", "def", "ghi")))
        );
   }
}
