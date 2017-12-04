import java.sql.*;

public class Authorization {
	
	JavaDB db;  
	String cardNumber, pinNumber; 
	Boolean credit;
	ResultSet result=null; 
	String output;
	boolean flag;
	public Authorization(String cardNumber) {
		this.cardNumber = cardNumber; 
		flag = false;
		
		db = new JavaDB();
		try {
			credit = false;

			Connection conn = db.getConnection();
            
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE cardNumber=" + cardNumber);
			
			result = statement.executeQuery();
						
			while(result.next()) {
				flag = true;
				credit = result.getBoolean("credit");
				pinNumber = result.getString("pinNumber"); 
			}
			
			
		} catch (Exception e) {

			e.printStackTrace();
		} 
	}
	
	public boolean isValid() {
		return flag;
	}
	
	public boolean isCredit() {
		return credit; 
	}
	
	public boolean isCorrectPin(String pin) {
		return pinNumber.equals(pin); 
	}
	
	
}
