package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * JSON-P : JSR 353: Java API for JSON Processing
 * JSON-B : JSR 367: Java API for JSON Binding 
 */
@ExtendWith(MockitoExtension.class)
public class JsonBindingTest {

    private static final String CHARLIE_AS_JSON = "{\"person-name\":\"Charlie\",\"email\":\"charlie@chaplin.com\",\"id\":1,\"registeredDate\":\"01-01-2020\",\"salary\":\"1000.0\"}";

    private static final String CHARLIE_NAME = "Charlie";

    private static final String CHARLIE_EMAIL = "charlie@chaplin.com";

    private static final BigDecimal CHARLIE_SALARY = BigDecimal.valueOf(1000);

    private static final LocalDate CHARLIE_REGISTER_DATE = LocalDate.of(2020, 1, 1);

    private Person charlie = new Person(1, CHARLIE_NAME, CHARLIE_EMAIL, 20, CHARLIE_REGISTER_DATE, CHARLIE_SALARY);

    private Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withLocale(Locale.US));

    @Test
    public void givenPerson_whenDeserializeASerializedPerson_thenGetSamePerson() {
        String charlieAsJson = jsonb.toJson(charlie);
        Person person = jsonb.fromJson(charlieAsJson, Person.class);
        assertEquals(charlie, person, "Deserialized JSON is not equal to Charlie");
    }

    @Test
    public void givenPersonAsJson_whenSerializeADesializedJsonb_thenGetbackOriginalJson() {
        Person person = jsonb.fromJson(CHARLIE_AS_JSON, Person.class); 
        String personAsJson = jsonb.toJson(person);
        assertTrue(CHARLIE_AS_JSON.equals(personAsJson), "Serialized person is not equal original JSON");
    }    
}