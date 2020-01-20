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
        if (em == null)
            System.out.println("@InjectMocks NULL");
        else
            System.out.println("@InjectMocks");
        this.em = em;
    }

	public void createPerson(Person person) {
        em.persist(person);
    }

    public Person findPersonById(Long id) {
        return em.find(Person.class, id);
    }
}