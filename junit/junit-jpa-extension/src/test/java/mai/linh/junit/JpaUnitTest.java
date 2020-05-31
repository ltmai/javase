package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import mai.linh.junit.extension.JpaUnitTestEm;
import mai.linh.junit.extension.JpaUnitTestExtension;

/**
 * JPA unit test
 * The following is necessary for JPA unit test:
 * 1. Annotate test class with @ExtendWith(JpaUnitTestExtension.class)
 * 2. A public EntityManager field annotated with @JpaUnitTest and @Spy
 * 3. The entity classes must be provided in test\resources\META-INF\persistence.xml
 */
@ExtendWith(JpaUnitTestExtension.class)
@ExtendWith(MockitoExtension.class)
public class JpaUnitTest {

    @Spy
    @JpaUnitTestEm
    public EntityManager em;

    @InjectMocks
    private PersonRepository personRepository;

    @Test
    public void whenPersonExists_thenHeCanBeFound() {
        // given
        // Person 'Einstein' was created in init-data.sql
        // when
        Optional<Person> einstein = personRepository.findPersonByLastName("Einstein").findFirst();
        // then
        assertTrue(einstein.isPresent());
    }  

    @Test
    public void whenAddingPerson_thenNewPersonCreated() {
        // given
        Person charlie = new Person();
        charlie.setFirstName("Charlie");
        charlie.setLastName("Chaplin");
        charlie.setDateOfBirth(Date.valueOf("1889-04-16"));        
        // when
        personRepository.createPerson(charlie);
        Person found = personRepository.findPersonByLastName(charlie.getLastName()).findFirst().get();
        // then
        assertEquals("Charlie", found.getFirstName(), "Newly added entity cannot be found");
    }

  
}