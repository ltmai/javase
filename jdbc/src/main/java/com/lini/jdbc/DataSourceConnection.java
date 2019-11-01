package com.lini.jdbc;

/**
 * Objects instantiated by classes that implement the DataSource represent a particular DBMS 
 * or some other data source, such as a file. If a company uses more than one data source, it 
 * will deploy a separate DataSource object for each of them. The DataSource interface is 
 * implemented by a driver vendor. It can be implemented in three different ways:
 * + A basic DataSource implementation produces standard Connection objects that are not pooled 
 * or used in a distributed transaction.
 * + A DataSource implementation that supports connection pooling produces Connection objects 
 * that participate in connection pooling, that is, connections that can be recycled.
 * + A DataSource implementation that supports distributed transactions produces Connection 
 * objects that can be used in a distributed transaction, that is, a transaction that accesses 
 * two or more DBMS servers.
 *
 * A JDBC driver should include at least a basic DataSource implementation. For example, the 
 * Java DB JDBC driver includes the implementation org.apache.derby.jdbc.ClientDataSource and 
 * for MySQL, com.mysql.jdbc.jdbc2.optional.MysqlDataSource. 
 * @author MaiL
 *
 */
public class DataSourceConnection {
	public DataSourceConnection() {
		
	}
}
