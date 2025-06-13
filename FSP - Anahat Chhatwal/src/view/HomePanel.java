package view;

// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.*;

// This class sets up the home panel for my application
public class HomePanel extends JPanel {

	// GUI components
	JLabel logoImage = new JLabel(new ImageIcon("images/logo.png"));
	RoundedButton loginButton = new RoundedButton("Login");
	RoundedButton signupButton = new RoundedButton("Sign Up");
	// Menu bar
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File ⤵");
	JMenuItem helpMenuItem = new JMenuItem("Help");
	JMenuItem quitMenuItem = new JMenuItem("Quit");
	JMenuItem homeMenuItem = new JMenuItem("Home");
	
	// Constructor
	public HomePanel(MainController mainController) {
		
		// Layout
		setBackground(Color.decode("#e6f2cf"));
        setLayout(null);
        
        // Logo image
        logoImage.setBounds(260, 100, 900, 405);
        add(logoImage);
        
        // Login button
        loginButton.setFont(new Font("Arial", Font.ITALIC, 26));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
        loginButton.setBounds(520, 570, 200, 70);
        loginButton.addActionListener(mainController);
        loginButton.setActionCommand("Login");
        add(loginButton);
        
        // Sign up button 
        signupButton.setFont(new Font("Arial", Font.ITALIC, 26));
        signupButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
        signupButton.setBounds(770, 570, 200, 70);
        signupButton.addActionListener(mainController);
        signupButton.setActionCommand("SignUp");
        add(signupButton);
        
		// Menu bar layout
		menuBar.setBackground(Color.decode("#e6f2cf"));
		menuBar.setOpaque(true);
		menuBar.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		Font menuFont = new Font("Arial", Font.BOLD, 14);
		// Menu items layout
		// File menu
		fileMenu.setOpaque(true);
		fileMenu.setFont(menuFont);
		fileMenu.setBackground(Color.decode("#e6f2cf"));
		fileMenu.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		// Help menu item
		helpMenuItem.setOpaque(true);
		helpMenuItem.setFont(menuFont);
		helpMenuItem.setBackground(Color.decode("#e6f2cf"));
		helpMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		helpMenuItem.setActionCommand("Help"); 
		helpMenuItem.addActionListener(mainController);
		// Quit menu item
		quitMenuItem.setFont(menuFont);
		quitMenuItem.setBackground(Color.decode("#e6f2cf"));
		quitMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		quitMenuItem.addActionListener(mainController);
		// Dashboard submenu item
		homeMenuItem.setFont(menuFont);
		homeMenuItem.setBackground(Color.decode("#e6f2cf"));
		homeMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		homeMenuItem.setActionCommand("Home"); 
		homeMenuItem.addActionListener(mainController);
		// Add submenu items to File menu
		fileMenu.add(homeMenuItem);
		// Add menu bar & menu items
		menuBar.add(fileMenu);
		menuBar.add(helpMenuItem);
		menuBar.add(quitMenuItem);
		
		setVisible(true);
	}
	
	// Getters & setters
	public JButton getLoginButton() {
		return loginButton;
	}
	public JButton getSignupButton() {
		return signupButton;
	}
	public JMenuBar getMenuBar() {
		return menuBar;
	}
}