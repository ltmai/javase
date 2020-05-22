package mai.linh.junit.extension;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * @author https://github.com/ltmai
 */
public class JpaUnitTestExtension
        implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    private static final String PERSISTENCE_UNIT_NAME = "jpa-test";

    private static EntityManagerFactory emf;

    private static EntityManager em;

    private static EntityTransaction tx;

    /**
     * Set up Persistence Unit properties
     * @return the Persistence Unit properties
     */
    private Map<String, String >jpaProperties() {
        Map<String, String> properties = new HashMap<String, String>();

        properties.put("javax.persistence.jdbc.url", "jdbc:h2:mem:test:jpa;DB_CLOSE_ON_EXIT=FALSE");
        properties.put("javax.persistence.jdbc.driver", "org.h2.Driver");
        properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
        properties.put("eclipselink.logging.parameters", "true");
        properties.put("eclipselink.logging.level.sql", "WARNING");
        properties.put("eclipselink.logging.level", "WARNING");

        return properties;
    }

    /**
     * Instantiate the in-memory EntityManager for testing as static
     * instance variable. This is not thread-safe if the tests are
     * executed concurrently however enough in most cases.
     */
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, jpaProperties());
        em = emf.createEntityManager();
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        em.close();        
        emf.close();
    }
    
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Object testInstance = context.getTestInstance().orElseThrow(() -> new Exception("No test instance found"));
        Class<?> testClass = context.getTestClass().orElseThrow(() -> new Exception("No test class found"));
            
        for (Field f : testClass.getDeclaredFields()) {
            if (f.getAnnotation(JpaUnitTestEm.class) != null) {
                f.set(testInstance, em);
                break;
            }
        }

        tx = em.getTransaction();
        tx.begin();
    }
    
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        em.flush();
        if (tx.isActive()) {
            tx.rollback();
        }
        em.clear();
    }
}