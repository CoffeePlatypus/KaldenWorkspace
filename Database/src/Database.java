import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/* 1 - load sql driver (JDBC) into project - add to build path
 * 2 - set up database (script)
 * 		- create database
 *      - create tables
 *      - (opt) pre populate with data
 * 3 - connect to database
 * 4 - insert/modify/delete data
 * 5 - query data
 * 6 - disconnect from database
 */
public class Database {
	// set up database path string
//	private String url = "jdbc:mysql://localhost/databaseName?user=username&password=psswd"; // for mysql
	private String url = "jdbc:sqlite:C:/sqlite/Company.db"; // ends with .db 
	private Connection  connection;
	
	public Database() {
		
	}
	
	public void connect() throws SQLException {
		connection = DriverManager.getConnection(url);
	}
	
	public void disconnect() throws SQLException {
		connection.close();
	}
	
	public ResultSet runQuery(String q) throws SQLException{
		PreparedStatement statement = connection.prepareStatement(q);
		return statement.executeQuery();
	}
	
	public ResultSet runQuery(String q, String arg) throws SQLException{
		PreparedStatement statement = connection.prepareStatement(q);
		statement.setString(1, arg);
		return statement.executeQuery();
	}
	
	public void insertEmployee(String q, Employee e) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(q);
		statement.setString(1, e.getSsn());
		statement.setDouble(2, e.getSalary());
		statement.setString(3, e.getFirstName());
		statement.setString(4, e.getLastName());
		statement.executeUpdate();
	}
	
	
	
}
