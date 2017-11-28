
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

import javax.swing.*;

public class UserInterface  {
	
	static JButton checkoutButton;
	static JFrame frame;
	static JPanel panel;
	static JTextField itemNumber; 
	static JButton scanButton;
	static Connection conn; 

	public static void main(String[] args) {
	
		JavaDB instance = new JavaDB(); 
		
		//MySQL Connection
		try {
			conn = instance.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		frame = new JFrame();
		
		panel = new JPanel();
		
		//Adding Panel to Frame 
		panel.setBackground(Color.CYAN);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		//Panel Operations
				checkoutButton = new JButton("Start Checkout");
				
				checkoutButton.setBounds(205, 200, 300, 100);
				
				panel.add(checkoutButton);
				
				checkoutHandler chandler = new checkoutHandler();
				
				//Enters the scanning item phase when the this button is pressed
				checkoutButton.addActionListener(chandler);
		
				JLabel welcome = new JLabel("Welcome to the Self-Checkout Station"); 
				
				welcome.setBounds(124, 40, 600, 50);
				
				welcome.setFont(new Font("Serif", Font.BOLD, 24));
				
				panel.add(welcome);
				
		//Setting up Frame
		frame.setSize(new Dimension(700, 600));
		
		frame.setVisible(true);
		
		frame.setTitle("AutoScouts System");
		
		frame.setLocationRelativeTo(null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	//Even handler class for Start Checkout button 
	
	private static class checkoutHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
			if(event.getSource() == checkoutButton) {
				panel.removeAll();
				panel.revalidate();
				panel.repaint();
				
				//Scan your item text label 
				
				JLabel scanPrompt = new JLabel("Please Scan Your Items: "); 
				scanPrompt.setFont(new Font("Serif", Font.PLAIN, 24));
				scanPrompt.setBounds(10, 10, 300, 50);
				panel.add(scanPrompt);
				
				//Scan entry using item number text field
				
				itemNumber = new JTextField();
				itemNumber.setText("Enter the Item Number to Scan");
				itemNumber.setBounds(10, 520, 500, 20);
				panel.add(itemNumber);
				
				//Clears default text when mouse is clicked on the JTextField
				
				itemNumber.addMouseListener(new MouseAdapter(){
		            @Override
		            public void mouseClicked(MouseEvent e){
		                itemNumber.setText("");
		            }
		        });
				
				//Scan button to enter the data
				
				scanButton = new JButton(); 
				scanButton.setText("Scan");
				scanButton.setBounds(550, 520, 90, 19);
				panel.add(scanButton);
				
				//Action handler addition to scan button  
				
				scanHandler shandler = new scanHandler();
				scanButton.addActionListener(shandler);

			}
		}
	}
	
	
	//Event handler class for the scan button 
	private static class scanHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
			panel.repaint();
			
		    String s = itemNumber.getText(); 
			   
			   JLabel text = new JLabel(s); 
				
				text.setBounds(124, 40, 600, 50);
				
				text.setFont(new Font("Serif", Font.BOLD, 24));
				
				panel.add(text);
			}
		
		}
		
	
}
