import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JavaDB {
	
	public Connection getConnection() throws Exception {
		
		try {
			String driver = "com.mysql.jdbc.Driver"; 
			String url = "jdbc:mysql://127.0.0.1:3306/autoscoutsdb"; 
			String username = "root"; 
			String password = "yourpassword";
			Class.forName(driver); 
			
			Connection conn = DriverManager.getConnection(url, username, password); 
			System.out.println("Connected to MySQL");
			return conn; 
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return null; 
	}
	
	
}
