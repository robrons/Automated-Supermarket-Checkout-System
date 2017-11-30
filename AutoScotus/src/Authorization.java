import java.sql.*;

public class Authorization {
	
	JavaDB db;  
	
	public Authorization() {
		db = new JavaDB();
		try {
			Connection conn = db.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
