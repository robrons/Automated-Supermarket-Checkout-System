import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JavaDB {
	
	//Establishes connection to the SQL Database
	
	public Connection getConnection() throws Exception {
		
		try {
			String driver = "com.mysql.jdbc.Driver"; 
			String url = "jdbc:mysql://127.0.0.1:3306/autoscoutsdb"; 
			String username = "root"; 
			String password = ""; //Enter your DataBase Password
			Class.forName(driver); 
			
			Connection conn = DriverManager.getConnection(url, username, password); 
			System.out.println("Connected to MySQL");
			return conn; 
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return null; 
	}
	
	// Retrieves data from SQL for inventory 
	
	public String getInventory(String sql) throws Exception {
		
		String output = ""; 
		
		try {
			Connection conn = getConnection();
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			ResultSet result = statement.executeQuery(); 
						
			while(result.next()) {
				String description = result.getString("item");
				Float pf = result.getFloat("price"); 
				String price = pf.toString(); 
				
				output = description + "   $" + price; 
			}
			
			return output; 
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return null; 
	}
	
	
}
