package mai.linh.junit;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class UUIDGeneratorTest {

    private static UUID createUUID(Map<String, String> attributes)
    {
        return UUID.nameUUIDFromBytes(attributes.entrySet()
                                                .stream()
                                                .map(entry -> entry.getKey() + "=" + entry.getValue())
                                                .sorted(Comparator.naturalOrder())
                                                .collect(Collectors.joining(";"))
                                                .getBytes());
    }
   
    @Test
    void test()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("option", null);
        map1.put("model", "a8");
        map1.put("color", "red");


        Map<String, String> map2 = new HashMap<>();
        map2.put("model", "a8");
        map2.put("color", "red");
        map2.put("option", null);

        long execTime = ExecutionTimer.time(()->{
            System.out.println(createUUID(map1));
            System.out.println(createUUID(map2));
        });
        System.out.println("Execution time: " + execTime + " (ms)");
    }
}
