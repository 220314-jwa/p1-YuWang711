package database.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 *  DatabaseUtil should follow singleton design pattern
 * 	Where is Singleton pattern used?
 *	It is used to provide global point of access to the object.
 *	In terms of practical use Singleton patterns are used in logging,
 *	caches, thread pools, configuration settings, device driver objects.
 */

/*
 *  Static Synchronized method is also a method of synchronizing a method in java
 *  such that no two threads can act simultaneously static upon the synchronized 
 *  method. 
 */

/*
 *  Why used here? There is no need for a second connection to the database
 *  from the same server.
 */

public class DatabaseUtil {
	private static DatabaseUtil dbUtil;
	private static Properties databaseProps;
	
	private DatabaseUtil() {
		databaseProps = new Properties();
		try {
			InputStream propertiesFileStream = DatabaseUtil.class.
					getClassLoader().getResourceAsStream("database.properties");
			databaseProps.load(propertiesFileStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized DatabaseUtil getConnectionUtil() {
		if(dbUtil == null) {
			dbUtil = new DatabaseUtil();
		}
		return dbUtil;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(databaseProps.getProperty("driver"));
			conn = DriverManager.getConnection(
					databaseProps.getProperty("databaseUrl"),
					System.getenv("DB_USER"),
					System.getenv("DB_PASS"));
			//getConnection doesn't work
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

}
