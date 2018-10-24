
public class Company {

	public static void main(String[] args) {
		Database db = new Database();
		
		try {
			db.connect();
			System.out.println("Connected to database!!!");
			
			String query ="SELECT * FROM Employee"; /// RUN queries in sql first!!!
			
			
			
			db.disconnect();
		}catch (Exception e) {
			System.out.println("Error");
		}
	}

}
