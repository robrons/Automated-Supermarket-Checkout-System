import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class cardTransactionHandler implements ActionListener {
    
	double total;  
	JPanel panel; 
	String number, pin; 
	JTextField cardNumber, pinNumber;
	LinkedList<String> tempCheckout; 
	
	public cardTransactionHandler(double total, JPanel panel, JTextField cardNumber, LinkedList<String> tempCheckout) {
		this.total = total; 
		this.panel = panel; 
		this.cardNumber = cardNumber; 
		this.tempCheckout = tempCheckout; 
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		this.number = cardNumber.getText();
	    Authorization authenticate = new Authorization(number);
	    
	    if(authenticate.isCredit()) {
	    	panel.remove(cardNumber);	    	
	    	panel.remove(1);
	    	panel.repaint();
	    	pinNumber = new JTextField();
	    	pinNumber.setText(" ENTER PIN");
	    	pinNumber.setBounds(10, 70, 80, 20);
	    	panel.add(pinNumber);
	    	
	    	//Clears default text when mouse is clicked on the JTextField
			
			pinNumber.addMouseListener(new MouseAdapter(){
	            @Override
	            public void mouseClicked(MouseEvent e){
	            	pinNumber.setText("");
	            }
	        });
			
			JButton pinEnterButton = new JButton(); 
			pinEnterButton.setText("Enter");
			pinEnterButton.setBounds(301, 70, 80, 19);
			panel.add(pinEnterButton);
			
			pinEnterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					pin = pinNumber.getText(); 
			    	if(authenticate.isCorrectPin(pin)) {
			    		transact(); 
			    	}
				}});

			
	    }
		
		
	}
	
	public void transact() {
		
		
	   panel.removeAll(); 
	   panel.revalidate();
	   panel.repaint();
	   
	   String conformation = getSaltString();
	   

		JLabel message = new JLabel("Receipt"); 
		
		message.setBounds(10, 10, 600, 50);
		
		message.setFont(new Font("Serif", Font.BOLD, 24));
		
		panel.add(message);
		
		Iterator<String> iter = tempCheckout.iterator(); 
		
		int offset = 50; 
		
		while(iter.hasNext()) {
			
			JLabel text = new JLabel(iter.next()); 
			    
			text.setBounds(10, 20 + offset, 600, 30);
				
			text.setFont(new Font("Serif", Font.ITALIC, 24));
				
			panel.add(text);
			
			offset+= 30; 
		}
		
		JLabel textTotal = new JLabel("Total: $" + total); 
	    
		textTotal.setBounds(10, offset + 40, 600, 30);
			
		textTotal.setFont(new Font("Serif", Font.BOLD, 24));
			
		panel.add(textTotal);

		JLabel confCode = new JLabel("Your Authorization number is: " + getSaltString());
		confCode.setBounds(10, offset + 70, 650, 30);	
		confCode.setFont(new Font("Serif", Font.BOLD, 24));
		panel.add(confCode);
		
		JLabel cardInfo = new JLabel("Your card XXXX-XXXX-XXXX-" + number.substring(0, 4) + " has been charged $" + total);
		cardInfo.setBounds(10, offset + 100, 650, 30);	
		cardInfo.setFont(new Font("Serif", Font.PLAIN, 24));
		panel.add(cardInfo);

	}
	
	protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
