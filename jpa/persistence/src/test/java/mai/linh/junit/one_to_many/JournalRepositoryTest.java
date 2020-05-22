package mai.linh.junit.one_to_many;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import mai.linh.junit.extension.JpaUnitTestEm;
import mai.linh.junit.extension.JpaUnitTestExtension;
import mai.linh.junit.one_to_many.journal.Journal;
import mai.linh.junit.one_to_many.journal.JournalRepository;

/**
 * JPA unit test
 * The following is necessary for JPA unit test:
 * 1. Annotate test class with @ExtendWith(JpaUnitTestExtension.class)
 * 2. A public EntityManager field annotated with @JpaUnitTest and @Spy
 * 3. The entity classes must be provided in test\resources\META-INF\persistence.xml
 */
@ExtendWith(JpaUnitTestExtension.class)
@ExtendWith(MockitoExtension.class)
public class JournalRepositoryTest {

    @Spy
    @JpaUnitTestEm
    public EntityManager em;

    @InjectMocks
    private JournalRepository journalRepository;

    @Test
    public void whenJournalExists_thenItCanBeFound() {
        // given
        // when
        Optional<Journal> firstEntry = journalRepository.findJournalbyCategory("HUMAN").findFirst();
        // then
        assertAll("Journal of category HUMAN",
            ()->assertTrue(firstEntry.isPresent()),
            ()->assertTrue(firstEntry.get().getParameters().get(1).equals("FIRST"))
        );
    }
    
    @Test
    public void whenInsertJournal_thenNewJournalEntryCreated() {
        // given
        Journal journal = new Journal();
        journal.setCategory("CATEGORY");
        journal.setMessage("Message with parameter %1 and %2");
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, "FIRST");
        parameters.put(2, "SECOND");
        journal.setParameters(parameters);
        // when
        journalRepository.createJournal(journal);
        Journal newJournal = journalRepository.findJournalbyCategory("CATEGORY").findFirst().get();
        String message = buildMessageWithParameters(newJournal.getMessage(), newJournal.getParameters());
        // then
        assertEquals("Message with parameter FIRST and SECOND", message);
    }

    private String buildMessageWithParameters(String message, Map<Integer, String> parameters) {
        String result = message;
        for (java.util.Map.Entry<Integer, String> entry : parameters.entrySet()) {
            result = result.replace("%" + entry.getKey(), entry.getValue());
        }
        return result;
    }
}