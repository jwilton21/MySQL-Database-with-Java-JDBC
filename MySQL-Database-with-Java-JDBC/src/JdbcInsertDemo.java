
// Java SQL library.
import java.sql.*;

// demo database.
public class JdbcInsertDemo
{
	//Assign database connection strings.
	static String dbUrl = "jdbc:mysql://localhost:3306/demo";
	public static String getDbUrl()
	{
		return dbUrl;
	}

	public static void setDbUrl(String dbUrl)
	{
		JdbcInsertDemo.dbUrl = dbUrl;
	}

	protected static String getUser()
	{
		return user;
	}

	protected static void setUser(String user)
	{
		JdbcInsertDemo.user = user;
	}

	protected static String getPass()
	{
		return pass;
	}

	protected static void setPass(String pass)
	{
		JdbcInsertDemo.pass = pass;
	}

	static String user = "student";
	static String pass = "student";

	// Throwing SQLException if user/pw/database info is incorrect.
	public static void main(String[] args) throws SQLException
	{
		// Initialise database objects- connection, statement, resultSet.
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		// Try to connect.
		try
		{
			// Connect to database.
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			// Create a SQL statement.
			myStmt = myConn.createStatement();

			// Insert a new employee.
			System.out.println("Inserting a new employee to the database\n");

			// Update employee table with new record.
			int rowsAffected = myStmt
					.executeUpdate("insert into employees " + "(last_name, first_name, email, department, salary) "
							+ "values " + "('Jones', 'Anna', 'anna.jones@foo.com', 'Legal',28000.00)");

			// Verify this was done- get list of employees with select all from.

			myRs = myStmt.executeQuery("select * from employees order by last_name");

			// Process results set. While result set has next record, print it out.
			while (myRs.next())
			{
				System.out.println(myRs.getString("last_name") + "," + myRs.getString("first_name"));
			}

		}
		// If exception thrown, pass to here and print stack trace if unsuccessful.

		// Should not reach here if connection/details are correct.
		catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (myRs != null)
			{
				// Close result set object.
				myRs.close();
			}
		}
		// Print out deleted records.
		System.out.println("Number of rows deleted: " + deleteFromEmployees(0, myStmt));

	}

	// Use to delete record from database.
	public static int deleteFromEmployees(int rowsAffected, Statement myStmt)
	{
		try
		{ // Assign rows deleted.
			rowsAffected = myStmt
					.executeUpdate("delete from employees " + "where last_name='Jones' and first_name='Anna'");
		} catch (SQLException e)
		{
			// If exception thrown then print stack trace.
			e.printStackTrace();

		} // Return number of deleted records back to main.
		return rowsAffected;

		// Get connection for database.

	}
}
