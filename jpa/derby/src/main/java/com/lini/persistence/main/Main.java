package com.lini.persistence.main;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.lini.persistence.entity.Employee;
import com.lini.persistence.manager.PersistenceManager;
import com.lini.utilities.Utils;

/**
 * This program demonstrates how JPA is used in Java SE
 * @author mail
 *
 */
public class Main {
	/**
	 * Add a new employee
	 * @param em
	 * @param first_name
	 * @param last_name
	 * @param birthday
	 * @return
	 */
	private static void addEmployee(EntityManager em, String first_name, String last_name, Date birthday) {	
		try {
			em.getTransaction().begin();
			Employee employee = new Employee();
			employee.setFirst_name(first_name);
			employee.setLast_name(last_name);
			employee.setBirthday(birthday);
			em.persist(employee);
			em.getTransaction().commit();
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Initial cause:" + Utils.getInitialCauseMessage(e));
			em.getTransaction().rollback();
		}
	}

	/**
	 * Query employees
	 * @param em
	 */
	public static void testQuery(EntityManager em) {
		try {
			em.getTransaction().begin();

			TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
			if (query.getResultList().isEmpty()) {
				System.out.println("No employee found");
			}
			else {
				System.out.println(query.getResultList().size() + " employees found");
			}
	
			em.getTransaction().commit();
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Initial cause:" + Utils.getInitialCauseMessage(e));
			em.getTransaction().rollback();
		}
	}

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * retrieve MANIFEST.MF attributes
		 */
		Package aPackage = Main.class.getPackage();
		String implementationVersion = aPackage.getImplementationVersion();
		String implementationVendor = aPackage.getImplementationVendor();
		System.out.println(implementationVendor + " - " + implementationVersion);
		
		EntityManager em = connectDb();

		addEmployee(em, "Mickey", "Mouse", Date.valueOf("1989-10-03"));		
		testQuery(em);

		disconnectDb(em);
	}

	/**
	 * disconnect and clean up
	 */
	private static void disconnectDb(EntityManager em) {
		em.close();
		PersistenceManager.INSTANCE.close();
	}

	/**
	 * setup Persistence Manager
	 */
	private static EntityManager connectDb() {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		em.setProperty("javax.persistence.lock.timeout", 10);
		em.setProperty("javax.persistence.query.timeout", 10);
		return em;
	}


}
