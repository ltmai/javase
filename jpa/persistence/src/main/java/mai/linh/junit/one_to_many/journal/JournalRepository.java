package mai.linh.junit.one_to_many.journal;


import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JournalRepository
 */
public class JournalRepository {

    @PersistenceContext(unitName = "Production")
    private EntityManager em;

    /**
     * supports unit test
     * @param em
     */
    public JournalRepository(EntityManager em) {
        this.em = em;
    }

	public void createJournal(Journal person) {
        em.persist(person);
    }

    public long journalCount() {
        return (long)em.createQuery("SELECT COUNT(*) FROM Journal").getSingleResult();
    }

    public Stream<Journal> findJournalbyCategory(String category) {
        return em.createQuery("SELECT j FROM Journal j WHERE j.category = :category", Journal.class)
                 .setParameter("category", category)
                 .getResultList().stream();
    }
}