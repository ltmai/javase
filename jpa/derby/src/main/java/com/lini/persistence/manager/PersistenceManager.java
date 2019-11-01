package com.lini.persistence.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Singleton Persistence Manager Factory
 * @author MaiL
 *
 */
public enum PersistenceManager {

	INSTANCE;
	
	private final String PRJ_PERSISTENCE_UNIT = "jpa-example";
	
	private EntityManagerFactory emFactory;
	
	/**
	 * constructs the singleton Entity Manager Factory
	 */
	private PersistenceManager() {
		emFactory = Persistence.createEntityManagerFactory(PRJ_PERSISTENCE_UNIT);
	}

	/**
	 * creates Entity Manager
	 * @return the singleton Entity Manager
	 */
	public EntityManager getEntityManager() {
		return emFactory.createEntityManager();
	}

	/**
	 * Closes Entity Manager Factory
	 */
	public void close() {
		emFactory.close();
	}
}