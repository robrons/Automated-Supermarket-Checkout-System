
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.*;

public class UserInterface  {
	
	static JFrame frame;
	static JPanel panel;
	static JTextField itemNumber; 
	static JTextField cashValue; 
	static JTextField cardNumber; 
	static JTextField restockItemNumber; 
	static JTextField restockItemQuantity; 
	static JButton restockQuantityButton; 
	static JButton restockButton;
	static JButton checkoutButton; 
	static JButton scanButton;
	static JButton restockScanButton;
	static JButton enterButton; 
	static JButton payButton; 
	static JButton totalButton; 
	static JButton subtotalButton; 
	static JButton cancelcheckoutButton;
	static JButton cashButton; 
	static JButton cardButton; 
	static JButton cancelpaymentButton; 
	static JavaDB instance; 
	static int offset = 10; 
	static double total = 0.0; 
	static HashMap<String, String> tempCheckOut; 
	
	public static void main(String[] args) {
		
		tempCheckOut = new HashMap<>(); 
			
		frame = new JFrame();
		
		panel = new JPanel();
		
		//Create an object for JavaDB connection
		instance = new JavaDB(); 
		
		//Adding Panel to Frame 
		panel.setBackground(Color.CYAN);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		//Panel Operations
		reset(); 
				
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
			
			tempCheckOut.clear();
			offset = 10; 
			total = 0.0; 
			
			JLabel welcome = new JLabel("Welcome to the Self-Checkout Station"); 
			
			welcome.setBounds(150, 40, 600, 50);
			
			welcome.setFont(new Font("Serif", Font.BOLD, 24));
			
			panel.add(welcome);
			
			checkoutButton = new JButton("START CHECKOUT");
			
			checkoutButton.setBounds(200, 120, 300, 80);
			
			panel.add(checkoutButton);
			
			
			
			//Restock Button 

			restockButton = new JButton("RESTOCK");
			
			restockButton.setBounds(200, 220, 300, 80);
			
			panel.add(restockButton);
			
			//Handlers and Listeners
			checkoutHandler chandler = new checkoutHandler();
			checkoutButton.addActionListener(chandler);
			
			restockHandler rshandler = new restockHandler(); 
			restockButton.addActionListener(rshandler);
			
		}
	
	//CHECKOUT FUNCTIONS
	
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
				
					tempCheckOut.put(s, op);
					
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
				
				//Event handler for card and cash buttons
				
				cardHandler chandler = new cardHandler();
				cardButton.addActionListener(chandler);
				
			    cashTransactionHandler cashTransaction = new cashTransactionHandler();				
			    cashButton.addActionListener(cashTransaction);
				
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
				
				//Returns to payment when cancel checkout is pressed
				totalHandler cphandler = new totalHandler();
				cancelpaymentButton.addActionListener(cphandler);
				
				//Event Handling 
				cardTransactionHandler cardTransaction = new cardTransactionHandler(total, panel, cardNumber, tempCheckOut, cancelcheckoutButton, instance); 
				enterButton.addActionListener(cardTransaction);
				
			}
			
		}
		
		public static class cashTransactionHandler implements ActionListener {

		

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				panel.repaint();
				panel.removeAll();
				panel.revalidate();
				
				//Card Check out text label 
				
				JLabel cashPrompt = new JLabel("Cash Checkout"); 
				cashPrompt.setFont(new Font("Serif", Font.BOLD, 24));
				cashPrompt.setBounds(10, 10, 300, 50);
				panel.add(cashPrompt);
			
				//Scan entry using item number text field
				
				cashValue = new JTextField();
				cashValue.setText(" Deposit your Fund");
				cashValue.setBounds(10, 70, 130, 20);
				panel.add(cashValue);
				
				//Clears default text when mouse is clicked on the JTextField
				
				cashValue.addMouseListener(new MouseAdapter(){
		            @Override
		            public void mouseClicked(MouseEvent e){
		            	cashValue.setText("");
		            }
		        });
				
				//Enter Button to Scan Card
				payButton = new JButton(); 
				payButton.setText("Pay");
				payButton.setBounds(150, 70, 80, 19);
				panel.add(payButton);
				
				//Cancel Checkout Button 
				cancelcheckoutButton.setBounds(10, 100, 180, 19);
				panel.add(cancelcheckoutButton); 
				
				//Cancel Payment Button 
				cancelpaymentButton = new JButton(); 
				cancelpaymentButton.setText("Cancel Payment");
				cancelpaymentButton.setBounds(200, 100, 180, 19);
				panel.add(cancelpaymentButton); 
				
				//Returns to payment when cancel checkout is pressed
				totalHandler cphandler = new totalHandler();
				cancelpaymentButton.addActionListener(cphandler);
				
				//Event Handling 
				cashTransaction cash = new cashTransaction(total, panel, cashValue, tempCheckOut, cancelcheckoutButton, instance); 
				payButton.addActionListener(cash);
				
			}
			
		}
		
		
		//RESTOCK FUNCTIONS
		
		public static class restockHandler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.removeAll(); 
				panel.revalidate(); 
				panel.repaint(); 
				
				JLabel restockPrompt = new JLabel("Enter Items to be Restocked: "); 
				restockPrompt.setFont(new Font("Serif", Font.BOLD, 24));
				restockPrompt.setBounds(10, 10, 400, 50);
				panel.add(restockPrompt);
				
				restockItemNumber = new JTextField();
				restockItemNumber.setText(" Enter the Restock Item Number to Scan");
				restockItemNumber.setBounds(10, 70, 290, 20);
				panel.add(restockItemNumber);
				
				
				//Clears default text when mouse is clicked on the JTextField
				
				restockItemNumber.addMouseListener(new MouseAdapter(){
		            @Override
		            public void mouseClicked(MouseEvent e){
		            	restockItemNumber.setText("");
		            }
		        });
				
				restockScanButton = new JButton(); 
				restockScanButton.setText("Scan");
				restockScanButton.setBounds(300, 70, 120, 19);
				panel.add(restockScanButton);
				
				//Event Handlers 
				restockButtonHandler restock  = new restockButtonHandler(); 
				restockScanButton.addActionListener(restock);
				
			}
			
		}
		
		
		public static class restockButtonHandler implements ActionListener {
			
			String number;
	
			LinkedList<ArrayList<String>> output = new LinkedList<ArrayList<String>>(); 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				number = restockItemNumber.getText(); 
				try {
					output = instance.getCheckoutRestock("select * from inventory where ID="+number);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 	
				
				if(!output.isEmpty()) {
					panel.remove(restockItemNumber);
					panel.remove(restockScanButton);
					ArrayList<String> opAL = new ArrayList<String>(); 
					opAL = output.get(0); 
					restockItemQuantity = new JTextField();
					restockItemQuantity.setText(" Enter Quantity for the Item: " + opAL.get(1));
					restockItemQuantity.setBounds(10, 70, 310, 20);
					panel.add(restockItemQuantity);
					panel.repaint();
					
					restockItemQuantity.addMouseListener(new MouseAdapter(){
			            @Override
			            public void mouseClicked(MouseEvent e){
			            	restockItemQuantity.setText("");
			            }
			        });
					
					restockQuantityButton = new JButton(); 
					restockQuantityButton.setText("Restock");
					restockQuantityButton.setBounds(320, 70, 120, 19);
					panel.add(restockQuantityButton);
					
					restockUpdate restockupdate = new restockUpdate(); 
					restockQuantityButton.addActionListener(restockupdate);
					
				} else {
					panel.remove(restockItemNumber);
					panel.remove(restockScanButton);
					JLabel restockPrompt = new JLabel("Insert New Item: "); 
					restockPrompt.setFont(new Font("Serif", Font.ROMAN_BASELINE, 24));
					restockPrompt.setBounds(10, 50, 400, 50);
					panel.add(restockPrompt);
					
					JTextField describe = new JTextField(); 
					describe.setText(" Enter Item Description:");
					describe.setBounds(10, 100, 200, 20);
					panel.add(describe); 
					
					describe.addMouseListener(new MouseAdapter(){
			            @Override
			            public void mouseClicked(MouseEvent e){
			            	describe.setText("");
			            }
			        });
					
					JTextField price = new JTextField(); 
					price.setText(" Enter Item Price:");
					price.setBounds(10, 130, 200, 20);
					panel.add(price); 
					
					price.addMouseListener(new MouseAdapter(){
			            @Override
			            public void mouseClicked(MouseEvent e){
			            	price.setText("");
			            }
			        });
					
					JTextField discount = new JTextField(); 
					discount.setText(" Enter Discount Information:");
					discount.setBounds(10, 160, 200, 20);
					panel.add(discount); 
					
					discount.addMouseListener(new MouseAdapter(){
			            @Override
			            public void mouseClicked(MouseEvent e){
			            	discount.setText("");
			            }
			        });
					
					JTextField quantity = new JTextField(); 
					quantity.setText(" Enter Quantity Information:");
					quantity.setBounds(10, 190, 200, 20);
					panel.add(quantity); 
					
					quantity.addMouseListener(new MouseAdapter(){
			            @Override
			            public void mouseClicked(MouseEvent e){
			            	quantity.setText("");
			            }
			        });
					
					panel.repaint();
					
					JButton insert = new JButton(); 
					insert.setText("INSERT");
					insert.setBounds(10, 220, 90, 20);
					panel.add(insert); 
					
					//Event Handling
					insert.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
						instance.updateInventory("insert into inventory(item, price, discount, quantity) values(\""+describe.getText()+"\","+price.getText()+","+discount.getText()+","+quantity.getText()+")"); 
						System.out.println("insert into inventory(item, price, discount, quantity) values(\""+describe.getText()+"\","+price.getText()+","+discount.getText()+","+quantity.getText()+")");
						
						panel.removeAll();
						panel.removeAll();
						panel.repaint();
						
						JLabel promp = new JLabel(); 
						promp.setText("Item Successfully Inserted");
						promp.setFont(new Font("Serif", Font.ROMAN_BASELINE, 24));
						promp.setBounds(10, 70, 620, 30);
						panel.add(promp); 
						
						JButton contButton = new JButton(); 
						contButton.setBounds(300, 71, 150, 30);
						contButton.setText("CONTINUE");
						panel.add(contButton);
						

						JButton exitButton = new JButton(); 
						exitButton.setBounds(500, 71, 150, 30);
						exitButton.setText("EXIT");
						panel.add(exitButton);
						
						//Action Handling 
						
						restockHandler restock = new restockHandler(); 
						contButton.addActionListener(restock);
						
						exitButton.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								reset(); 
							}
							
						});
						
						}
						
					});
					
				
				}
								
			}
			
		}
		
		public static class restockUpdate implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				instance.updateInventory("update inventory set quantity="+restockItemQuantity.getText()+" where ID="+restockItemNumber.getText());
				panel.remove(restockQuantityButton);
				panel.remove(restockItemQuantity);
				panel.repaint();
				
				JLabel promp = new JLabel(); 
				promp.setText("Item Successfully Updated");
				promp.setFont(new Font("Serif", Font.ROMAN_BASELINE, 24));
				promp.setBounds(10, 70, 620, 30);
				panel.add(promp); 
				
				JButton contButton = new JButton(); 
				contButton.setBounds(300, 71, 150, 30);
				contButton.setText("CONTINUE");
				panel.add(contButton);
				

				JButton exitButton = new JButton(); 
				exitButton.setBounds(500, 71, 150, 30);
				exitButton.setText("EXIT");
				panel.add(exitButton);
				
				
				//Action Handling 
				
				restockHandler restock = new restockHandler(); 
				contButton.addActionListener(restock);
				
				exitButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						reset(); 
					}
					
				});
				
				
			} 
			
		}
		
		
		
		
		
		//class Restock / Print Inventory("Roei")
		
		//class View/Update Product ("Austin")

		//class Daily Report (Michael)
		
		
	
}
