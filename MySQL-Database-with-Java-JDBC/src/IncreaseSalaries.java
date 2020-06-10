import java.sql.*;

// This class performs an increase of x amount upon an employee's salary based upon department.
public class IncreaseSalaries {

	public static void main(String[] args) throws Exception {
		// Define connection objects/interface.
		Connection myConn = null;
		CallableStatement myStmt = null;

		try {
			// Get a connection to database, parameters.
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "student", "student");
			// parameters for prepared statement. 
			String theDepartment = "Engineering";
			int theIncreaseAmount = 10000;
			
			// Show salaries BEFORE calling method increaseSalaries.
			System.out.println("Salaries before the salary update\n");
			// Call method on our connection parameters and desired department.
			showSalaries(myConn, theDepartment);

			// Prepare the stored procedure call
			// Assign myStmt to myConn parameters and do callable statement. We can re-use this and set parameters below.
			myStmt = myConn
					.prepareCall("{call increase_salaries_for_department(?, ?)}");

			// Set the parameters. Where derpartment == x (engineering), do the increaseAmount.
			myStmt.setString(1, theDepartment);
			myStmt.setDouble(2, theIncreaseAmount);

			// Call stored procedure. 
			System.out.println("\n\nCalling stored procedure.  increase_salaries_for_department('" + theDepartment + "', " + theIncreaseAmount + ")");
			myStmt.execute();
			System.out.println("Finished calling stored procedure");

			// Show salaries AFTER showSalaries method called.
			System.out.println("\n\nSalaries AFTER\n");
			// Call method, pass in connection string, user,pw, desired department.
			showSalaries(myConn, theDepartment);
		
			// No exceptions if dbUrl, user, pw are correct.
		} catch (Exception exc) {
			// If issue, print stackTrace.
			exc.printStackTrace();
		} finally {
			// Close objects.
			close(myConn, myStmt, null);
		}
	}
	// showSalaries takes in connection string, url, user, pw. & desired department. 
	private static void showSalaries(Connection myConn, String theDepartment) throws SQLException {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// Prepare statement
			myStmt = myConn
					.prepareStatement("select * from employees where department=?");

			myStmt.setString(1, theDepartment);
			
			// Execute SQL query
			myRs = myStmt.executeQuery();

			// Process result set
			while (myRs.next()) {
				String lastName = myRs.getString("last_name");
				String firstName = myRs.getString("first_name");
				double salary = myRs.getDouble("salary");
				String department = myRs.getString("department");
				
				System.out.printf("%s, %s, %s, %.2f\n", lastName, firstName, department, salary);
				// 	TODO- some kind of order by on resultsET.
			}// Exception won't be thrown to here and caught if parameters of db are correct.
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			// Close.
			close(myStmt, myRs);
		}

	}
	// Close connection objects.
	private static void close(Connection myConn, Statement myStmt,
			ResultSet myRs) throws SQLException {
		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	}
	// Method to close sqlStatements and db resultSet.
	private static void close(Statement myStmt, ResultSet myRs)
			throws SQLException {

		close(null, myStmt, myRs);
	}
}
