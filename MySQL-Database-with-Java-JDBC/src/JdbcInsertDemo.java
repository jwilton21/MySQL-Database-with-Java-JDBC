// Java SQL library.
import java.sql.*;
// demo database.
public class JdbcInsertDemo {
	// Throwing SQLException if user/pw/database info is incorrect.
	public static void main(String[] args) throws SQLException {
		// Initialise database objects- connection, statement, resultSet.
		Connection myConn = null;
		Statement myStmt =  null;
		ResultSet myRs = null;

		// Assign database connection strings.
		String dbUrl = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String pass = "student";
		// Try to connect.
		try {
			// Connect to database.
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			// Create a SQL statement.
			myStmt = myConn.createStatement();

			// Insert a new employee.
			System.out.println("Insert a new employee to the database\n");

			// Print out employee information before we do an update.

			displayEmployee(myConn,"Anna","Jones");

			// SQL insert into employees table.
			int rowsAffected = myStmt.executeUpdate(
					"insert into employees " +
							"(last_name, first_name, email, department, salary) " +
							"values " +
					"('Jones', 'Anna', 'anna.jones@foo.com', 'Legal',28000.00)");

			// Verify this was done- get list of employees with select all from.

			myRs = myStmt.executeQuery("select * from employees order by last_name");

			// Process results set.
			while(myRs.next()) {
				System.out.println(myRs.getString("last_name")+","+myRs.getString("first_name"));
			}
		}
		// If exception thrown, pass to here and print stack trace if unsuccessful. 

		// Should not reach here if connection/details are correct.
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if (myRs !=null) {
				myRs.close();
			}
		}

	}
	// Helper method which will print out the record before the update.
	private static void displayEmployee(Connection myConn, String string, String string2) 
	{
		System.out.println("Before update: \n");
	}
	
	// TO DO-- Helper method which will de-dupe records. Too many runs we add dupes.
	//private static void deDupeRecords()

}
