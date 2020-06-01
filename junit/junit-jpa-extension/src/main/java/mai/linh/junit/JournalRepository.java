package mai.linh.junit;

import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mai.linh.junit.journal.Journal;

public class JournalRepository {

    @PersistenceContext(unitName = "Production")
    private EntityManager em;

	public JournalRepository(EntityManager em) {
        this.em = em;
    }

	public Stream<Journal> getJournalByCategory(String category) {
		return em.createQuery("SELECT j FROM Journal j WHERE j.category = :category", Journal.class)
                 .setParameter("category", category)
                 .getResultStream();
	}

	public void persist(Object journal) {
        em.persist(journal);
	}
    
}