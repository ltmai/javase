package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * JSON-P : JSR 353: Java API for JSON Processing (e.g. parse, generate, transform 
 *          and query) JSON messages. (JSON-P 1.0 in JavaEE 7)
 *          JSR 374 supersedes JSR 353 to support JSON Pointer and JSON Patch.
 *          (JSON-P 1.1 in JavaEE 8)
 * JSON-B : JSR 367: Java API for JSON Binding (converting POJO to/from JSON messages)
 *
 * @see https://javaee.github.io/jsonp/ (JSR 374)
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

    private Person chaplin = new Person(
            1, 
            "Charlie", 
            "charlie@chaplin.com", 
            20, 
            LocalDate.of(2020, 1, 1), 
            BigDecimal.valueOf(1000));

    private Person albert = new Person(
            2, 
            "Albert", 
            "albert@einstein.com", 
            20, 
            LocalDate.of(2021, 1, 1), 
            BigDecimal.valueOf(1001));

    // https://javaee.github.io/javaee-spec/javadocs/javax/json/bind/Jsonb.html
    // For optimal use, JsonbBuilder and Jsonb instances should be reused - for a typical use-case, only one 
    // Jsonb instance is required by an application. All the methods in this class are safe for use by multiple 
    // concurrent threads.
    private Jsonb jsonb = JsonbBuilder.create(new JsonbConfig()
                                             .withLocale(Locale.US)
                                             .withPropertyOrderStrategy(PropertyOrderStrategy.LEXICOGRAPHICAL));
    
    ////////////
    // JSON-B
    ////////////

    @Test
    public void givenPerson_whenDeserializeASerializedPerson_thenGetSamePerson() {
        String charlieAsJson = jsonb.toJson(chaplin);

        Person person = jsonb.fromJson(charlieAsJson, Person.class);

        assertEquals(chaplin, person, "Deserialized JSON is not equal to Charlie");
    }

    @Test
    public void givenPersonAsJson_whenSerializeADeserializedJsonb_thenGetbackOriginalJson() {
        Person person = jsonb.fromJson(CHARLIE_JSON_COMPACT, Person.class); 

        String personAsJson = jsonb.toJson(person, Person.class);

        assertTrue(CHARLIE_JSON_COMPACT.equals(personAsJson), "Serialized person is not equal original JSON");
    }    

    @Test
    public void givenCollection_whenDeserializeSerializedCollection_thenGetSameCollection() {
        List<Person> greatests = new ArrayList<>();
        greatests.add(chaplin);
        greatests.add(albert);

        String greatestsAsJson = jsonb.toJson(greatests);
        List<Person> people = jsonb.fromJson(greatestsAsJson, 
                                             new ArrayList<Person>(){}.getClass().getGenericSuperclass());

        assertTrue(Arrays.equals(greatests.toArray(), people.toArray()));
    }

    ////////////
    // JSON-P
    ////////////

    @Test
    public void givenJsonObject_whenCompareWithStringRepresentation_thenJsonPatchEmpty() {
        JsonObject expected = Json.createObjectBuilder()
            .add("b", 2)
            .add("a", 1)
            .build();

        try (JsonReader reader = Json.createReader(new ByteArrayInputStream("{\"a\":1,\"b\":2}".getBytes()))) {
            JsonObject actual = reader.readObject();
            JsonPatch diff = Json.createDiff(expected, actual);
            assertTrue(diff.toJsonArray().isEmpty(), diff.toString());
            assertEquals(actual.getInt("a"), 1);
            assertEquals(actual.getInt("b"), 2);
            assertFalse(actual.containsKey("c"));
        }
    }

    @Test
    public void givenJsonObject()
    {
        String json = "[{\"type\":\"ORDER\",\"configuration\":\"serial-number\"},{\"type\":\"CONSTANT\",\"configuration\":\"const\"}]";
        try (JsonReader reader = Json.createReader(new ByteArrayInputStream(json.getBytes()))) {
            var actual = reader.readArray();
            assertEquals(actual.size(), 2);
        }
    }

    @Test
    public void givenJsonString_whenParsed_thenReceiveCorrectValues() {
        final String PROPERTY_NAME = "name";
        final String PROPERTY_AGE = "age";
        final String VALUE_NAME = "Charlie";
        final int    VALUE_AGE = 70;
        final JsonObject json = Json.createObjectBuilder()
            .add(PROPERTY_NAME, VALUE_NAME)
            .add(PROPERTY_AGE, BigDecimal.valueOf(VALUE_AGE))
            .build();

        // JsonParser provides forward, read-only access to JSON data in a streamming way. This is the most 
        // efficient way for reading JSON data. This is the only way to parse and process JSON data that are
        // too big to be loaded in memory.
        // @see https://javadoc.io/doc/javax.json/javax.json-api/latest/overview-summary.html
        final JsonParser parser = Json.createParser(new StringReader(json.toString()));

        while (parser.hasNext()) {
            Event event = parser.next();
            if (event == Event.KEY_NAME) {
                String key = parser.getString();
                event = parser.next();
                switch (key) {
                    case PROPERTY_NAME:
                        String name = parser.getString();
                        assertEquals(VALUE_NAME, name);
                        break;
                    case PROPERTY_AGE: 
                        var age = parser.getInt();
                        assertEquals(VALUE_AGE, age);
                    default:
                        break;
                }
            }            
        }
        parser.close();
    } 
}
