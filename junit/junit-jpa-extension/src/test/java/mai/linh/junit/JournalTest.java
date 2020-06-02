package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import mai.linh.junit.extension.JpaJUnitEm;
import mai.linh.junit.extension.JpaJUnitExtension;
import mai.linh.junit.extension.TestNameExtension;
import mai.linh.junit.journal.Journal;

@ExtendWith(JpaJUnitExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(TestNameExtension.class)
public class JournalTest {

    @JpaJUnitEm
    public EntityManager em;

    private JournalRepository journalRepository;

    @BeforeEach
    public void beforeAll() {
        journalRepository = new JournalRepository(em);
    }

    @Test
    public void whenJournalExists_thenItCanBeFound() {
        // given
        // when
        Journal journalHuman = journalRepository.getJournalByCategory("HUMAN").findFirst().get();
        Journal journalMachine = journalRepository.getJournalByCategory("MACHINE").findFirst().get();
        // then
        assertEquals("Message with one parameter FIRST", journalHuman.toString());
        assertEquals("Message with two parameters FIRST and SECOND", journalMachine.toString());
    }

    @Test
    public void whenAddingJournal_thenNewJournalCreated() {
        // given
        Journal journal = new Journal();
        journal.setCategory("CATEGORY");
        journal.setMessage("Message with parameter %1 and %2 and %3");
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, "FIRST");
        parameters.put(2, "SECOND");
        parameters.put(3, "THIRD");
        journal.setParameters(parameters);
        // when
        journalRepository.persist(journal);
        journal = journalRepository.getJournalByCategory("CATEGORY").findFirst().get();
        // then
        assertEquals("Message with parameter FIRST and SECOND and THIRD", journal.toString());
    }
}