package mai.linh.junit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}