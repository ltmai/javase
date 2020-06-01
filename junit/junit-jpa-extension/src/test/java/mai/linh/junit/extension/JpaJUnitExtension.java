package mai.linh.junit.extension;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.platform.commons.support.AnnotationSupport;

/**
 * JUnit 5 extension that initialize a in-memory H2 database for testing JPA code.
 * It prepares an EntityManagerFactory and an EntityManager for each test class. 
 * The EntityManager is then injected into a public member of JUnit test instance 
 * that is decorated with @JpaJUnitEm. These resources are closed when all the test
 * methods complete (via AfterAllCallback).
 * If there was a JUnit 5 Extension point like Test Container Predestroy we could 
 * initialized a single EntityManagerFactory for the whole test phase (e.g. in a 
 * Maven build). Without this capability, unfortunately we have to create a new one 
 * for each test class, which is less than efficient. As a result, if we have a SQL 
 * load script to insert data into the test database, it will be executed for each 
 * test class.     
 */
public class JpaJUnitExtension 
    implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback, TestInstancePostProcessor {

    private static final Namespace EMF = Namespace.create("EntityManagerFactory");
    private static final Namespace EM = Namespace.create("EntityManager");

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Logger.getLogger("org.hibernate").setLevel(Level.WARNING);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-test");
        context.getRoot().getStore(EMF).put(context.getRequiredTestClass(), factory);  
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        EntityManagerFactory factory = context.getRoot().getStore(EMF).get(context.getRequiredTestClass(), EntityManagerFactory.class);  
        EntityManager em = factory.createEntityManager();

        context.getRoot().getStore(EM).put(context.getRequiredTestClass(), em);

        injectEntityManagerIntoTestInstance(testInstance, em);
    }

    private void injectEntityManagerIntoTestInstance(Object testInstance, EntityManager em) throws IllegalAccessException {
        AnnotationSupport.findPublicAnnotatedFields(testInstance.getClass(), EntityManager.class, JpaJUnitEm.class)
                         .get(0).set(testInstance, em);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        EntityManager em = context.getRoot().getStore(EM).get(context.getRequiredTestClass(), EntityManager.class);
        EntityTransaction transaction = em.getTransaction();
        
        if (transaction.isActive()) {
            throw new Exception("Transaction is already active!");
        }
        transaction.begin();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        EntityManager em = context.getRoot().getStore(EM).get(context.getRequiredTestClass(), EntityManager.class);
        EntityTransaction transaction = em.getTransaction();

        if (transaction.isActive()) {
            transaction.rollback();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        EntityManagerFactory factory = context.getRoot().getStore(EMF).get(context.getRequiredTestClass(), EntityManagerFactory.class);
        EntityManager em = context.getRoot().getStore(EM).get(context.getRequiredTestClass(), EntityManager.class);
        em.close();
        factory.close();
    }
}