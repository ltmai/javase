package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * JPA unit test
 * The following is necessary for JPA unit test:
 * 1. Annotate test class with @ExtendWith(JpaUnitTestExtension.class)
 * 2. A public static EntityManager field annotated with @JpaUnitTest
 * 3. The Entity classes are provided in test\resources\META-INF\persistence.xml
 * 4. In @BeforeEach create a new instance of the object that uses EntityManager
 */
@ExtendWith(JpaUnitTestExtension.class)
@ExtendWith(MockitoExtension.class)
public class JpaUnitTest {

    /**
     * This EntityManager must be public static and annotated with 
     * @JpaUnitTestEm in order to be injected by JpaUnitTestExtension
     */
    @Spy
    @JpaUnitTestEm
    public EntityManager em;

    @InjectMocks
    private PersonRepository personRepository;

    /**
     * @BeforeEach runs before BeforeTestExecutionCallback::beforeTestExecution
     * and by default JUnit 5 will create a new instance of the test class for 
     * each test, so we cannot inject the EntityManager into a instance member
     * of the test class inside beforeTestExecution, because it is too late. 
     * The simpler alternative is to make the injected EntityManager static so
     * it can be initialized before all tests are executed (effectively before
     * all @BeforeEach methods).
     */
    @BeforeEach
    public void beforeEach() {
        System.out.println("@BeforeEach");
    }
    
    /**
     * DAO Unit Test
     */
    @Test
    public void jpaUnitTest() {
        // given
        Person charlie = new Person();
        charlie.setId(1889L);
        charlie.setFirstName("Charlie");
        charlie.setLastName("Chaplin");
        charlie.setDateOfBirth(Date.valueOf("1889-04-16"));        
        // when
        personRepository.createPerson(charlie);
        // then
        Person found = personRepository.findPersonById(1889L);
        assertEquals("Charlie", found.getFirstName(), "Newly added entity cannot be found");
    }
}