package mai.linh.junit.extension;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.platform.commons.support.AnnotationSupport;

public class JpaUnitTestExtension 
    implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback, TestInstancePostProcessor {

    private static final class PersistenceCtx {

        private static Map<String, String> jpaProperties() {
            final Map<String, String> properties = new HashMap<String, String>();

            properties.put("javax.persistence.jdbc.url", "jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=TRUE");
            properties.put("javax.persistence.jdbc.driver", "org.h2.Driver");

            return properties;
        }

        public static final EntityManagerFactory EMFACTORY = Persistence.createEntityManagerFactory("jpa-test",
                jpaProperties());

        public static final Map<Object, EntityManager> CONNECTIONS = new HashMap<>();
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        EntityManager em = PersistenceCtx.EMFACTORY.createEntityManager();

        PersistenceCtx.CONNECTIONS.put(testInstance, em);

        AnnotationSupport.findPublicAnnotatedFields(testInstance.getClass(), EntityManager.class, JpaUnitTestEm.class)
                         .get(0).set(testInstance, em);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Object testInstance = context.getTestInstance().orElseThrow(null);
        PersistenceCtx.CONNECTIONS.get(testInstance).getTransaction().rollback();        
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Object testInstance = context.getTestInstance().orElseThrow(null);
        PersistenceCtx.CONNECTIONS.get(testInstance).getTransaction().begin();
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        PersistenceCtx.CONNECTIONS.values().forEach(EntityManager::close);
        PersistenceCtx.EMFACTORY.close();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
    }
}