
// Java SQL library.
import java.sql.*;

// This class creates, sets and executes a prepared SQL statement for the employees table in mySQL Demo database.
public class Driver
{
	// Throwing SQLException if user/pw/database info is incorrect.
	public static void main(String[] args) throws SQLException
	{
		// Initialise database objects- connection, statement, resultSet.
		Connection myConn = null;
		PreparedStatement myStmt = null;
		PreparedStatement myStmtDelete = null;
		ResultSet myRs = null;

		// Try to connect.
		try
		{
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "student", "student");

			// 2. Prepare statement, calling constructor, pass in SQL. Where salary is > AND
			// dept = x.
			myStmt = myConn.prepareStatement("select * from employees where salary > ? and department=?");

			// 3. Set the parameters for our statement- before executing.
			myStmt.setDouble(1, 24000);
			myStmt.setString(2, "HR");

			// 4. Execute SQL query
			myRs = myStmt.executeQuery();

			// 5. Return result set (all from database where above applicable conditions are
			// met.
			display(myRs);

			// REUSE prepared statement for other cases.

			// New criteria to be entered into myStmt parameters.
			// Print out from result set where salary > 3500 AND dept = engineering.
			System.out.println("\n\nReusing the prepared statement:  salary > 35000,  department = Engineering");

			// 6. Set parameters for our new SQL statement.
			myStmt.setDouble(1, 35000);
			myStmt.setString(2, "Engineering");

			// 7. Execute SQL query
			myRs = myStmt.executeQuery();

			// 8. Call method display() to show results.
			display(myRs);

			// 9. Call delete on record.
			deleteRecord(myStmtDelete, myConn);

			// Catch exception if thrown. Should not reach here.
		} catch (Exception exc)
		{ // Print stack trace if applicable.
			exc.printStackTrace();
		} finally
		{
			if (myRs != null)
			{ // Close result set.
				myRs.close();
			}

			if (myStmt != null)
			{ // Close prepared statement.
				myStmt.close();
			}

			if (myConn != null)
			{ // Close connection.
				myConn.close();
			}
		}
	}

	// Display results method.
	private static void display(ResultSet myRs) throws SQLException
	{ // While resultset has next row do:
		while (myRs.next())
		{ // Obtain values from database.
			String lastName = myRs.getString("last_name");
			String firstName = myRs.getString("first_name");
			double salary = myRs.getDouble("salary");
			String department = myRs.getString("department");
			// Print out values from database in this order.
			System.out.printf("%s, %s, %.2f, %s\n", lastName, firstName, salary, department);
		}

	}
	// This method deletes records.
	private static void deleteRecord(Statement myStmtDelete, Connection myConn) throws SQLException
	{
		try
		{	// Create the sql statement.
			myStmtDelete = myConn.createStatement();
			// Execute the statement.
			myStmtDelete.execute("DELETE FROM EMPLOYEES WHERE SALARY >= 80000");

			// Catch sql exception.
		} catch (SQLException e)
		{ // Print stack trace if applicable.
			e.printStackTrace();
		} finally
		{	// Another try catch block to catch class exceptions.
			try
			{ // Close objects.
				myStmtDelete.close();
				myConn.close();
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}

		}
	}
}
