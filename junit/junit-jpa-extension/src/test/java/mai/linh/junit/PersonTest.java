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

import mai.linh.junit.extension.JpaJUnitEm;
import mai.linh.junit.extension.JpaJUnitExtension;
import mai.linh.junit.extension.TestNameExtension;
import mai.linh.junit.person.Person;

@ExtendWith(JpaJUnitExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(TestNameExtension.class)
public class PersonTest {

    @Spy
    @JpaJUnitEm
    public EntityManager em;

    @InjectMocks
    private PersonRepository personRepository;

    @Test
    public void whenPersonExists_thenHeCanBeFound() {
        // given
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