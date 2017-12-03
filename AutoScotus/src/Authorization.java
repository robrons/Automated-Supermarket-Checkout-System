import java.sql.*;

public class Authorization {
	
	JavaDB db;  
	String cardNumber, pinNumber; 
	Boolean credit;
	
	public Authorization(String cardNumber) {
		this.cardNumber = cardNumber; 
		String output; 
		
		db = new JavaDB();
		try {
			credit = false;

			Connection conn = db.getConnection();
            
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE cardNumber=" + cardNumber);
			
			ResultSet result = statement.executeQuery(); 
						
			while(result.next()) {
				credit = result.getBoolean("credit");
				pinNumber = result.getString("pinNumber"); 
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public boolean isCredit() {
		return credit; 
	}
	
	public boolean isCorrectPin(String pin) {
		return pinNumber.equals(pin); 
	}
	
	
}
