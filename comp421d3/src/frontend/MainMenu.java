package frontend;

import java.awt.*;
import java.util.*;

import javax.naming.directory.SearchControls;
import javax.swing.JFrame;

public class MainMenu extends JFrame {
	
	/**
	 * Creates new form LoginButton
	 */
	public MainMenu() {
		initComponents();
	}

	/**
	 * Initializes all the Components
	 */
	@SuppressWarnings("unchecked")

	private void initComponents() {

		MainPanel = new javax.swing.JPanel();
		UsernameField = new javax.swing.JTextField();
		UsernameLabel = new javax.swing.JLabel();
		PasswordLabel = new javax.swing.JLabel();
		PasswordField = new javax.swing.JPasswordField();
		MessageLabel = new javax.swing.JLabel();
		LoginButton = new javax.swing.JButton();
		NewUserButton = new javax.swing.JButton();
		SearchButton = new javax.swing.JButton();
		ExitButton = new javax.swing.JButton();
		ForgotPasswordCheckBox = new javax.swing.JCheckBox();
		ButtonGroup = new javax.swing.ButtonGroup();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Welcome to the Bookstore!");

		MainPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		MainPanel.setLayout(null);

		UsernameField.setFont(new java.awt.Font("Tahoma", 1, 14));
		UsernameField.setForeground(new java.awt.Color(0, 0, 0));

		MainPanel.add(UsernameField);
		UsernameField.setBounds(125, 150, 390, 30);

		UsernameLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); 
		UsernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		UsernameLabel.setText("Username:");
		MainPanel.add(UsernameLabel);
		UsernameLabel.setBounds(30, 154, 90, 20);

		PasswordLabel.setFont(new java.awt.Font("Tahoma", 1, 16));
		PasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		PasswordLabel.setText("Password:");
		MainPanel.add(PasswordLabel);
		PasswordLabel.setBounds(30, 190, 90, 20);

		PasswordField.setFont(new java.awt.Font("Tahoma", 0, 14)); 
		PasswordField.setForeground(new java.awt.Color(0, 0, 0));

		MainPanel.add(PasswordField);
		PasswordField.setBounds(125, 190, 390, 30);

		MessageLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
		MessageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		MainPanel.add(MessageLabel);
		MessageLabel.setBounds(125, 230, 390, 25); //120,23,370,23

		LoginButton.setBackground(new java.awt.Color(255, 153, 0));
		LoginButton.setFont(new java.awt.Font("Tahoma", 1, 14)); 
		LoginButton.setText("Login");
		LoginButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				LoginButtonActionPerformed(evt);
			}
		});
		MainPanel.add(LoginButton);
		LoginButton.setBounds(50, 310, 250, 40);

		NewUserButton.setBackground(new java.awt.Color(0, 255, 255));
		NewUserButton.setFont(new java.awt.Font("Tahoma", 1, 14));
		NewUserButton.setText("New User");
		NewUserButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				NewUserButtonActionPerformed(evt);
			}
		});
		MainPanel.add(NewUserButton);
		NewUserButton.setBounds(110, 360, 260, 40);

		SearchButton.setBackground(new java.awt.Color(255, 153, 153));
		SearchButton.setFont(new java.awt.Font("Tahoma", 1, 14)); 
		SearchButton.setText("Search Catalogue");
		SearchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SearchButtonActionPerformed(evt);
			}
		});
		MainPanel.add(SearchButton);
		SearchButton.setBounds(170, 410, 270, 40);

		ExitButton.setBackground(new java.awt.Color(255, 247, 0));
		ExitButton.setFont(new java.awt.Font("Tahoma", 1, 14)); 
		ExitButton.setText("Exit");
		ExitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ExitButtonActionPerformed(evt);
			}
		});
		MainPanel.add(ExitButton);
		ExitButton.setBounds(240, 460, 270, 40);

		ForgotPasswordCheckBox.setForeground(new java.awt.Color(255, 153, 0));
		ForgotPasswordCheckBox.setText("Forgot Password");

		MainPanel.add(ForgotPasswordCheckBox);
		ForgotPasswordCheckBox.setBounds(320, 320, 125, 25);

		ButtonGroup.add(ForgotPasswordCheckBox);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
				);

		pack();
	}

	/**
	 * 
	 * @param evt
	 * If the guest check box is selected, Login as a guest. If the forgetPassword check box is selected, prompts the user for his secret answer
	 * Otherwise, displays relevant warning messages or displays the Home screen if the login process is valid
	 */
	private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            

		// Declare variables
		String username;
		String password;
		char [] p;

		// Store the user's inputs into string variables
		username = UsernameField.getText();
		p = PasswordField.getPassword();
		password = new String(p);

		// Displays the Home screen for guest users if the Guest Check box is selected, regardless of anything else
	}
	/**
	 * @param evt
	 * Displays an Account creation screen and disposes the Login screen 
	 */
	private void NewUserButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              

	} 

	/**
	 * 
	 * @param evt
	 * Goes to search window
	 */
	private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
		
	}                                                

	/**
	 * 
	 * @param evt
	 * Exits the program
	 */
	private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           

		System.exit(0);

	}              

	public void clearButtonGroup(){
		ButtonGroup.clearSelection();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainMenu().setVisible(true);
			}
		});
	}
	
	// Variables declaration - do not modify
	private javax.swing.JButton ExitButton;
	private javax.swing.JCheckBox ForgotPasswordCheckBox;
	private javax.swing.ButtonGroup ButtonGroup;
	private javax.swing.JButton SearchButton;
	private javax.swing.JButton LoginButton;
	private javax.swing.JPanel MainPanel;
	private javax.swing.JLabel MessageLabel;
	private javax.swing.JButton NewUserButton;
	private javax.swing.JPasswordField PasswordField;
	private javax.swing.JLabel PasswordLabel;
	private javax.swing.JTextField UsernameField;
	private javax.swing.JLabel UsernameLabel;
	// End of variables declaration
}
