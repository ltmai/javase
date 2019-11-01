package com.lini.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This is a simple wrapper of java.sql.DriverManager.
 * DriverManagerConnection is the basic service for managing a set of JDBC drivers.
 * NOTE: The DataSource interface, new in the JDBC 2.0 API, provides another way to 
 * connect to a data source. The use of a DataSource object is the preferred means 
 * of connecting to a data source. 
 * @author MaiL
 *
 */
public class DriverManagerConnection {
	private String userName;
	private String password;
	private String dbName;
	/**
	 * 
	 * @param userName	database user to connect
	 * @param password	database password to connect
	 * @param dbName	database name
	 */
	public DriverManagerConnection(String userName, String password, String dbName) {
		this.userName = userName;
		this.password = password;
		this.dbName = dbName;
	}

	/**
	 * Attempts to establish a new connection to the given database URL.
	 * @return
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", password);
			conn = DriverManager.getConnection("jdbc:derby:" + dbName + ";create=true", connectionProps);
			System.out.println("Connected to database");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}	

	/**
	 * Closes the given connection
	 * @param conn
	 * @throws SQLException
	 */
	public void closeConnection(Connection conn) throws SQLException {
		if (conn.isClosed()) {
			System.out.println("Connection already closed");
		} 
		else {
			conn.close();
			System.out.println("Connection closed");
		}
	}
}
