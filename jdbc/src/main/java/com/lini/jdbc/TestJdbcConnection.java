package com.lini.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author mail
 * 
 * Run: java -cp target\jdbc-derby-x.jar com.lini.jdbc.TestJdbcConnection
 */
public class TestJdbcConnection {
	private static final String DB_PASSWORD = "app";
	private static final String DB_USERNAME = "app";
	private static final String DB_FILEPATH = "JavaDbEmbeddedTest";
	
	/**
	 * testDriverManagerConnection
	 * @throws SQLException 
	 */
	private static void testDriverManagerConnection() throws SQLException {
		DriverManagerConnection test = new DriverManagerConnection(DB_USERNAME, DB_PASSWORD, DB_FILEPATH);
		Connection conn = test.getConnection();
		
		try {
			conn.setAutoCommit(false);
			/**
			 * do SQL operations here
			 */
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			System.out.println("Transaction rollbacked");
		} finally {
			conn.setAutoCommit(true);
		}
		
		test.closeConnection(conn);
	}

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			testDriverManagerConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
