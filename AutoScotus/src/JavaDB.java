import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;

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
	
	public LinkedList<ArrayList<String>> getCheckoutRestock(String sql) throws Exception {
			
			LinkedList<ArrayList<String>> output = new LinkedList<ArrayList<String>>(); 
			
			try {				
				Connection conn = getConnection();
				
				PreparedStatement statement = conn.prepareStatement(sql);
				
				ResultSet result = statement.executeQuery(sql);

				while(result.next()) {
					ArrayList<String> al = new ArrayList<>(); 
					
					al.add(result.getString(1)); 
					al.add(result.getString(2)); 
					al.add(result.getString(3));
					al.add(result.getString(4)); 
					al.add(result.getString(5)); 
					
					output.add(al); 
				}
				
				return output; 
				
			} catch(Exception e) {
				System.out.println(e);
			}
			
			return null; 
		} 
	
	//Function to decrease the item quantity's after checkout 
	
	public void updateInventory(String sql) {
		try {
			Connection conn = getConnection();
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.executeUpdate();
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	
}
