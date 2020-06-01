package mai.linh.junit;

import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mai.linh.junit.person.Person;

/**
 * PersonRepository
 */
public class PersonRepository {

    @PersistenceContext(unitName = "Production")
    private EntityManager em;

    /**
     * supports unit test
     * @param em
     */
    public PersonRepository(EntityManager em) {
        this.em = em;
    }

	public void createPerson(Person person) {
        em.persist(person);
    }

    public Person findPersonById(Long id) {
        return em.find(Person.class, id);
    }

    public Stream<Person> findPersonByLastName(String lastName) {
        return em.createQuery("SELECT p FROM Person p WHERE p.lastName = :lastName", Person.class)
                 .setParameter("lastName", lastName)
                 .getResultList().stream();
    }
}