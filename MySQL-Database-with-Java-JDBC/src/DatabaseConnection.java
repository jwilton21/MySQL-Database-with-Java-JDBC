// Import all the java sql libraries.
import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.FileInputStream;
import java.sql.ResultSet;
// Instead of code re-use, load in db connection info from a properties file. Good for security.
public class DatabaseConnection
{

	public static void main(String[] args) throws SQLException
	{	// Set db objects, connection, sql statement, result set.
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try
		{
			// Loading properties file from system.
			Properties props = new Properties();
			props.load(new FileInputStream("C:/Users/jasmi/OneDrive/General Documents and Scans/GitHub/JDBC MYSQL/demo.properties.txt"));

			// Reading in the properties.
			String user = props.getProperty("user");
			String password = props.getProperty("password");
			String dbUrl = props.getProperty("dbUrl");

			// Connecting to database output message.
			System.out.println("Connecting to the database...");
			System.out.println("Database URL: " +dbUrl);
			System.out.println("User: " + user);

			// Get connection to database.
			myConn = DriverManager.getConnection(dbUrl,user,password);

			System.out.println("\nConnection successful!\n");

			// Create a SQL statement.
			myStmt = myConn.createStatement();

			// Execute SQL statement.
			myRs = myStmt.executeQuery("SELECT * FROM EMPLOYEES");

			// Process result set.
			// While result set has next entry, do print out first name, last name.
			while(myRs.next())
			{
				System.out.println(myRs.getString("first_name") +","+ myRs.getString("last_name"));
			}
			// Code should not reach here if no exception thrown from db connection information. Close db objects when done.
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			close(myConn, myStmt, myRs);
		}


	}

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException
	{	
		if(myRs !=null) 
		{
			myRs.close();
		}

		if(myStmt !=null)
		{
			myStmt.close();
		}

		if(myConn !=null)
		{
			myConn.close();
		}

	}

}


