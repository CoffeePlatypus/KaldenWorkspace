import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Company {

	public static void main(String[] args) {
		Database db = new Database();
		
		try {
			db.connect();
			System.out.println("Connected to database!!!");
			/*
			 * Simple query
			 */
			String query ="SELECT SSN, Salary,FirstName FROM Employee"; /// RUN queries in sql first!!!
			ResultSet results = db.runQuery(query);

			while(results.next()) {
//				for(int i = 1; i<4; i++) {
//					System.out.print(results.getString(i)+" ");
//				}
//				System.out.println();
				System.out.printf("%s | %s | %s\n", results.getString(1), results.getString(2), results.getString(3));
			}
			/*
			 * Dynamic query
			 */
//			String query ="SELECT SSN, Salary,FirstName FROM Employee WHERE SSN = ?"; /// RUN queries in sql first!!!
//			ResultSet results = db.runQuery(query, "123456789");
//
//			while(results.next()) {
//				System.out.printf("%s | %s | %s\n", results.getString(1), results.getString(2), results.getString(3));
//			}
			
			/*
			 * Insert new employee
			 */
			String insert ="INSERT INTO Employee(SSN, Salary, FirstName, LastName) VALUES (?, ?, ?,?);"; /// RUN queries in sql first!!!
			
			Employee e = new Employee("489432976", 15664.1, "Namef", "sir");
//			db.insertEmployee(insert, e);
			
				
			db.disconnect();
		}catch (Exception e) {
			System.out.println("Error");
		}
	}

}
