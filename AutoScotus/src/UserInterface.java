
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;

import javax.swing.*;

public class UserInterface  {
	
	static JButton checkoutButton;
	static JFrame frame;
	static JPanel panel;
	static JTextField itemNumber; 
	static JTextField cardNumber; 
	static JButton scanButton;
	static JButton enterButton; 
	static JButton totalButton; 
	static JButton subtotalButton; 
	static JButton cancelcheckoutButton;
	static JButton cashButton; 
	static JButton cardButton; 
	static JButton cancelpaymentButton; 
	static JavaDB instance; 
	static int offset = 10; 
	static double total = 0.0; 
	
	public static void main(String[] args) {
			
		frame = new JFrame();
		
		panel = new JPanel();
		
		//Create an obeject for JavaDB connection
		instance = new JavaDB(); 
		
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
				
				welcome.setBounds(150, 40, 600, 50);
				
				welcome.setFont(new Font("Serif", Font.BOLD, 24));
				
				panel.add(welcome);
				
		//Setting up Frame
		frame.setSize(new Dimension(700, 600));
		
		frame.setVisible(true);
		
		frame.setTitle("AutoScouts System");
		
		frame.setLocationRelativeTo(null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	//Function that Resets the JPanel Back to the Initial State
	public static void reset() {
		
			panel.removeAll();
			panel.revalidate();
			panel.repaint();
			
			//Reset values 
			
			offset = 10; 
			total = 0.0; 
			
			JLabel welcome = new JLabel("Welcome to the Self-Checkout Station"); 
			
			welcome.setBounds(150, 40, 600, 50);
			
			welcome.setFont(new Font("Serif", Font.BOLD, 24));
			
			panel.add(welcome);
			
			panel.add(checkoutButton);
			
			
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
				itemNumber.setText(" Enter the Item Number to Scan");
				itemNumber.setBounds(10, 520, 290, 20);
				panel.add(itemNumber);
				
				//Clears default text when mouse is clicked on the JTextField
				
				itemNumber.addMouseListener(new MouseAdapter(){
		            @Override
		            public void mouseClicked(MouseEvent e){
		                itemNumber.setText("");
		            }
		        });
				
				//Addition of Cancel Checkout button to enter the data
				
				cancelcheckoutButton = new JButton(); 
				cancelcheckoutButton.setText("Cancel Checkout");
				cancelcheckoutButton.setBounds(310, 520, 130, 19);
				panel.add(cancelcheckoutButton);
				
				//Addition of Scan button to enter the data
				
				scanButton = new JButton(); 
				scanButton.setText("Scan");
				scanButton.setBounds(440, 520, 70, 19);
				panel.add(scanButton);
				
				//Addition of Subtotal Button to calculate the Total
				
				subtotalButton = new JButton(); 
				subtotalButton.setText("SubTotal");
				subtotalButton.setBounds(510, 520, 90, 19);
				panel.add(subtotalButton);
				
				//Addition of Total Button to calculate the Total
				
				totalButton = new JButton(); 
				totalButton.setText("Total");
				totalButton.setBounds(600, 520, 70, 19);
				panel.add(totalButton);
				
				//Action handler addition to scan button  
				
				cancelcheckoutHandler cchandler = new cancelcheckoutHandler();
				cancelcheckoutButton.addActionListener(cchandler);
				
				
				//Action handler addition to scan button  
				
				scanHandler shandler = new scanHandler();
				scanButton.addActionListener(shandler);
				
				//Action handler addition to total button  
				
				totalHandler thandler = new totalHandler();
				totalButton.addActionListener(thandler);

				//Action handler addition to subtotal button  
				
				subtotalHandler sbhandler = new subtotalHandler();
				subtotalButton.addActionListener(sbhandler);
			}
		}
	}
	
	//Event handler class for the cancel checkout button 
	private static class cancelcheckoutHandler implements ActionListener {
				
		public void actionPerformed(ActionEvent event) {
					
			//Calls reset function to reset the checkout system 
					reset(); 
					 
			}
				
	}
	
	//Event handler class for the scan button 
	private static class scanHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
			panel.repaint();
			
		    String s = itemNumber.getText(); 
		    
		    String op = null; 
			   
		    //SQL Command on object instance to get the information from inventory 
		    
			try {
				op = instance.getInventory("SELECT item, price FROM inventory WHERE ID=" + s);
				
					//Display the results into the JPanel 
					
					String[] splitted = op.split("\\s+"); 
					
					//Conversion of price to double and addition into total 
					
					total += Double.parseDouble(splitted[splitted.length-1].replace("$", ""));
										
				    JLabel text = new JLabel(op); 
				    
					text.setBounds(10, 50 + offset, 600, 30);
					
					text.setFont(new Font("Serif", Font.ITALIC, 24));
					
					panel.add(text);
					
					offset += 30; 
						
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			 
			}
		
		}
	
	//Event handler class for the subtotal button 
			private static class subtotalHandler implements ActionListener {
				
				public void actionPerformed(ActionEvent event) {
					
					panel.repaint();
					
					//Prints Total Value and Tax
					double tax = total * .0825; 
					
					//Set precision of double
					Double truncatedDouble = BigDecimal.valueOf(tax)
						    .setScale(3, RoundingMode.HALF_UP)
						    .doubleValue();
					
					JLabel text = new JLabel("Sub Total: $" + total + " Tax: $" + truncatedDouble);
					
				    
					text.setBounds(10, 50 + offset, 600, 30);
					
					text.setFont(new Font("Serif", Font.ITALIC, 24));
					
					panel.add(text);
				    
					offset += 30; 
					 
					}
				
				}
	
	   //Event handler class for the total button 
		private static class totalHandler implements ActionListener {
			
			public void actionPerformed(ActionEvent event) {
				
				panel.repaint();
				panel.removeAll();
				panel.revalidate();
				
				//Prints Total Value
				
				double tax = total * .0825; 
				
				//Set precision of double
				Double truncatedDouble = BigDecimal.valueOf(total + tax)
					    .setScale(3, RoundingMode.HALF_UP)
					    .doubleValue();
			    
				JLabel text = new JLabel("Total: $" + truncatedDouble); 
			    
				text.setBounds(10, 10, 600, 30);
				
				text.setFont(new Font("Serif", Font.ITALIC, 24));
				
				panel.add(text);
			    
				//Addition of Credit Button to calculate the Total
				
				cardButton = new JButton(); 
				cardButton.setText("Pay By Credit Card / Debit Card");
				cardButton.setBounds(10, 70, 220, 50);
				panel.add(cardButton);
				
				//Addition of Debit Button to calculate the Total

				cashButton = new JButton(); 
				cashButton.setText("Pay By Cash");
				cashButton.setBounds(230, 70, 220, 50);
				panel.add(cashButton);
				
				//Addition of cancelPayment Button to calculate the Total

				/*cancelpaymentButton = new JButton(); 
				cancelpaymentButton.setText("Cancel Payment");
				cancelpaymentButton.setBounds(10, 140, 200, 50);
				panel.add(cancelpaymentButton);*/
				
				//Addition of cancelcheckoutButton Button to calculate the Total
				
				cancelcheckoutButton.setBounds(450, 70, 220, 50);
				panel.add(cancelcheckoutButton);
					
				//Handlers
				
				//Event handler to  
				
				cardHandler chandler = new cardHandler();
				cardButton.addActionListener(chandler);
				 
				}
			
			}
		
		//Event Handler Class that implements Pay by Credit/ Debit Card usecase
		
		private static class cardHandler implements ActionListener {
			
			public void actionPerformed(ActionEvent event) {
				
				panel.repaint();
				panel.removeAll();
				panel.revalidate();
				
				//Card Check out text label 
				
				JLabel cardPrompt = new JLabel("Card Checkout"); 
				cardPrompt.setFont(new Font("Serif", Font.BOLD, 24));
				cardPrompt.setBounds(10, 10, 300, 50);
				panel.add(cardPrompt);
			
				//Scan entry using item number text field
				
				cardNumber = new JTextField();
				cardNumber.setText(" Please Enter the Card Number");
				cardNumber.setBounds(10, 70, 290, 20);
				panel.add(cardNumber);
				
				//Clears default text when mouse is clicked on the JTextField
				
				cardNumber.addMouseListener(new MouseAdapter(){
		            @Override
		            public void mouseClicked(MouseEvent e){
		            	cardNumber.setText("");
		            }
		        });
				
				//Enter Button to Scan Card
				enterButton = new JButton(); 
				enterButton.setText("Enter");
				enterButton.setBounds(301, 70, 80, 19);
				panel.add(enterButton);
				
				//Cancel Checkout Button 
				cancelcheckoutButton.setBounds(10, 100, 180, 19);
				panel.add(cancelcheckoutButton); 
				
				//Cancel Payment Button 
				cancelpaymentButton = new JButton(); 
				cancelpaymentButton.setText("Cancel Payment");
				cancelpaymentButton.setBounds(200, 100, 180, 19);
				panel.add(cancelpaymentButton); 
				
				//Event Handling 
				
				//Returns to payment when cancel checkout is pressed
				totalHandler cphandler = new totalHandler();
				cancelpaymentButton.addActionListener(cphandler);
				
				
				
			}
			
		}
		
		
		//class Restock / Print Inventory("Roei")
		
		//class View/Update Product ("Austin")

		//class Daily Report (Michael)
		
		
	
}
