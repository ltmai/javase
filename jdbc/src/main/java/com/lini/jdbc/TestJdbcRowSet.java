package com.lini.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.sun.rowset.JdbcRowSetImpl;

/**
 * JdbcRowSet is of type connected RowSet object: Always being connected to a database, 
 * a JdbcRowSet object is most similar to a ResultSet object and is often used as a 
 * wrapper to make an otherwise non-scrollable and read-only ResultSet object scrollable 
 * and updatable. 
 * As a JavaBeans component, a JdbcRowSet object can be used, for example, in a GUI tool 
 * to select a JDBC driver. A JdbcRowSet object can be used this way because it is 
 * effectively a wrapper for the driver that obtained its connection to the database.
 * 
 * Disconnected RowSets include:
 * - CachedRowSet
 * - WebRowSet
 * - JoinRowSet
 * - FilteredRowSet
 * 
 * 4 ways to create a rowset:
 * The first 2 require a Connection and that the connection should be closed afterwards 
 * Connection conn = connectionMgr.getConnection();
 * 		RowSet rowSet = getJdbcRowSetFromResultSet(conn, query);
 * 		RowSet rowSet = getJdbcRowSetFromConnection(conn, query);
 * 		RowSet rowSet = getJdbcRowSetFromDefaultConstructor(DB_FILEPATH, DB_USERNAME, DB_PASSWORD, query);
 * 		RowSet rowSet = getJdbcRowSetFromRowSetFactory(DB_FILEPATH, DB_USERNAME, DB_PASSWORD, query);
 * 
 * Implementations (by Oracle):
 * com.sun.rowset.JdbcRowSetImpl
 * com.sun.rowset.CachedRowSetImpl
 * com.sun.rowset.WebRowSetImpl
 * com.sun.rowset.FilteredRowSetImpl
 * com.sun.rowset.JoinRowSetImpl
 * 
 * The best way to create a rowset is with RowSetFactory with which you may create any of 
 * the above rowset types (see getJdbcRowSetFromRowSetFactory)
 * @author MaiL
 *
 */
@SuppressWarnings("restriction")
public class TestJdbcRowSet {
	private static final String DB_PASSWORD = "app";
	private static final String DB_USERNAME = "app";
	private static final String DB_FILEPATH = "C:\\dev\\code\\java\\jpa\\JavaDbEmbeddedTest";
	
	/**
	 * test JDBC row set
	 * presents 4 ways to create JDBC Rowset
	 * @throws SQLException
	 */
	public void testJdbcRowSet() throws SQLException {
		String query = "SELECT * FROM coffees";
		RowSet rowSet = getJdbcRowSetFromRowSetFactory(DB_FILEPATH, DB_USERNAME, DB_PASSWORD, query);
		
		rowSet.close();
	}
	
	/**
	 * Java 7 and later (prefered way)
	 * @param dbFilepath
	 * @param dbUsername
	 * @param dbPassword
	 * @param query
	 * @return JDBC Rowset for given query
	 * @throws SQLException 
	 */
	private RowSet getJdbcRowSetFromRowSetFactory(String dbFilepath,
			String dbUsername, String dbPassword, String query) throws SQLException 
	{
		RowSetFactory myRowSetFactory = null;
	    JdbcRowSet jdbcRs = null;

	    myRowSetFactory = RowSetProvider.newFactory();
	    jdbcRs = myRowSetFactory.createJdbcRowSet();

	    jdbcRs.setUrl("jdbc:derby:" + dbFilepath);
	    jdbcRs.setUsername(dbUsername);
	    jdbcRs.setPassword(dbPassword);

	    jdbcRs.setCommand(query);
	    jdbcRs.execute();
	    
		return jdbcRs;
	}

	/**
	 * get JDBC row set from default constructor (Java 6)
	 * @param dbFilepath
	 * @param dbUsername
	 * @param dbPassword
	 * @param query
	 * @return JDBC Rowset for given query
	 * @throws SQLException 
	 */
	@SuppressWarnings("unused")
	private RowSet getJdbcRowSetFromDefaultConstructor(String dbFilepath,
			String dbUsername, String dbPassword, String query) throws SQLException 
	{
		RowSet jdbcRs = new JdbcRowSetImpl();
	    jdbcRs.setCommand(query);
	    jdbcRs.setUrl("jdbc:derby:" + dbFilepath);
	    jdbcRs.setUsername(dbUsername);
	    jdbcRs.setPassword(dbPassword);
	    jdbcRs.execute();
		return jdbcRs;
	}

	/**
	 * get JDBC row set from connection (Java 6)
	 * @param conn
	 * @param query
	 * @return JDBC Rowset for given query
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private RowSet getJdbcRowSetFromConnection(Connection conn, String query) throws SQLException 
	{
		RowSet jdbcRs = new JdbcRowSetImpl(conn);
		jdbcRs.setCommand(query);
		jdbcRs.execute();
		return jdbcRs;
	}
	
	/**
	 * get JDBC row set from a result set (Java 6)
	 * @param conn
	 * @return JDBC Rowset for given query
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private RowSet getJdbcRowSetFromResultSet(Connection conn, String query) throws SQLException 
	{
		java.sql.Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery(query);
		return new JdbcRowSetImpl(rs);
	}

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) { 
		try {
			new TestJdbcRowSet().testJdbcRowSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
