package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class SomeOtherTest {

   @Test
   void String_split_example () {
        var output = List.of("abc , def , ghi".split(","))
                     .stream().map(String::trim).collect(Collectors.toList());

        assertAll(
           ()->assertEquals(3, output.size()),
           ()->assertTrue(output.containsAll(List.of("abc", "def", "ghi")))
        );
   }

   @Test
   void regex_group_example() {
      final String JOB_ID="12356";
      final String COMP_ID="C: Body";

      Pattern pattern = Pattern.compile("(\\d+)-(C: [a-zA-Z]+)");
      Matcher matcher = pattern.matcher(String.format("%s-%s", JOB_ID, COMP_ID));

      assertTrue(matcher.matches());
      assertEquals(JOB_ID, matcher.group(1));
   }
}
