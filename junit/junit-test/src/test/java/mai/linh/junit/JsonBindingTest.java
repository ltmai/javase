package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyOrderStrategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * JSON-P : JSR 353: Java API for JSON Processing
 * JSON-B : JSR 367: Java API for JSON Binding 
 * @see https://javaee.github.io/jsonb-spec/docs/user-guide.html#json-binding-api
 */
@ExtendWith(MockitoExtension.class)
public class JsonBindingTest {
    // JSON elements are sorted lexicographically for easy comparison
    private static final String CHARLIE_AS_JSON = 
        String.join(System.getProperty("line.separator"),
                    "{",
                    "   \"email\":\"charlie@chaplin.com\",",
                    "   \"id\":1,",
                    "   \"person-name\":\"Charlie\",",
                    "   \"registeredDate\":\"01-01-2020\",",
                    "   \"salary\":\"1000.0\"",
                    "}");

    private static final String CHARLIE_JSON_COMPACT = CHARLIE_AS_JSON.replaceAll("[\\s\\n]*","");

    private static final String CHARLIE_NAME = "Charlie";

    private static final String CHARLIE_EMAIL = "charlie@chaplin.com";

    private static final BigDecimal CHARLIE_SALARY = BigDecimal.valueOf(1000);

    private static final LocalDate CHARLIE_REGISTER_DATE = LocalDate.of(2020, 1, 1);

    private Person charlie = new Person(1, CHARLIE_NAME, CHARLIE_EMAIL, 20, CHARLIE_REGISTER_DATE, CHARLIE_SALARY);

    // https://javaee.github.io/javaee-spec/javadocs/javax/json/bind/Jsonb.html
    // For optimal use, JsonbBuilder and Jsonb instances should be reused - for a typical use-case, only one 
    // Jsonb instance is required by an application. All the methods in this class are safe for use by multiple 
    // concurrent threads.
    private Jsonb jsonb = JsonbBuilder.create(new JsonbConfig()
                                             .withLocale(Locale.US)
                                             .withPropertyOrderStrategy(PropertyOrderStrategy.LEXICOGRAPHICAL));

    @Test
    public void givenPerson_whenDeserializeASerializedPerson_thenGetSamePerson() {
        String charlieAsJson = jsonb.toJson(charlie);
        Person person = jsonb.fromJson(charlieAsJson, Person.class);
        assertEquals(charlie, person, "Deserialized JSON is not equal to Charlie");
    }

    @Test
    public void givenPersonAsJson_whenSerializeADeserializedJsonb_thenGetbackOriginalJson() {
        Person person = jsonb.fromJson(CHARLIE_JSON_COMPACT, Person.class); 
        String personAsJson = jsonb.toJson(person, Person.class);
        assertTrue(CHARLIE_JSON_COMPACT.equals(personAsJson), "Serialized person is not equal original JSON");
    }    

    @Test
    public void givenJsonObject_whenCompareWithStringRepresentation_thenJsonPatchEmpty() {
        JsonObject actual = Json.createObjectBuilder().add("b", 2).add("a", 1).build();

        try (JsonReader reader = Json.createReader(new ByteArrayInputStream("{\"a\":1,\"b\":2}".getBytes()))) {
            JsonObject expected = reader.readObject();
            JsonPatch diff = Json.createDiff(expected, actual);
            assertTrue(diff.toJsonArray().isEmpty(), diff.toString());
        }
    }

    @Test
    public void testMappingGenericCollection() {
        List<Person> people = new ArrayList<>();
        people.add(charlie);

        String peopleAsJson = jsonb.toJson(people);
        people = jsonb.fromJson(peopleAsJson, new ArrayList<Person>(){}.getClass().getGenericSuperclass());
        people.get(0).equals(charlie);
    }
}
