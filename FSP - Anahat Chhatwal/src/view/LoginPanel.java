package view;

// Imports
import javax.swing.*;
import controller.*;
import java.awt.*;
import java.awt.event.*;

// LoginPanel provides a login interface for users, including fields for email and password.
public class LoginPanel extends JPanel {
    
	// Instances
	MainController mainController;
	LoginController loginController;
	
	// GUI Components
	JPanel greenPanel = new JPanel();
	JLabel signupTitle = new JLabel("Hello Friend!");
	JLabel signupSubtitle = new JLabel("<html><center>Turn your vision into a winning venture;<br>sign up to get started!</center></html>");
	RoundedButton signupBtn = new RoundedButton("Sign Up");
    JLabel loginTitle = new JLabel("Login to PitchPerfect");
    JLabel loginSubtitle = new JLabel("Please enter your email and password below.");
	RoundedButton loginBtn = new RoundedButton("Login");
	JLabel logoImage = new JLabel(new ImageIcon("images/smalllogodark.png"));
	JLabel emailIcon = new JLabel(new ImageIcon("images/emailIcon.png"));
	JLabel passwordIcon = new JLabel(new ImageIcon("images/passwordIcon.png"));
	JLabel emailTitle = new JLabel("Email");
	JLabel passwordTitle = new JLabel("Password");
    JTextField email = new JTextField();
    JPasswordField password = new JPasswordField();
    JLabel forgotPasswordLabel = new JLabel("<html><u>Forgot Password?</u></html>");
    
    // Constructor
    public LoginPanel(MainController mainController, LoginController loginController) {
        this.mainController = mainController;
        this.loginController = loginController;
        
        // Layout
        setLayout(null);
        setBackground(Color.decode("#e6f2cf"));

        // Right panel title and subtitle
        greenPanel.setBackground(Color.decode("#4d5d32"));
        greenPanel.setBounds(970, 0, 500, 1080);
        greenPanel.setLayout(null);
        
        // Logo image
        logoImage.setBounds(0, 10, 150, 60);
        add(logoImage);

        // Sign up title
        signupTitle.setFont(new Font("SansSerif", Font.BOLD, 40));
        signupTitle.setForeground(Color.decode("#e6f2cf"));
        signupTitle.setBounds(120, 280, 500, 50);
        greenPanel.add(signupTitle);

        // Sign up subtitle
        signupSubtitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        signupSubtitle.setForeground(Color.decode("#e6f2cf"));
        signupSubtitle.setBounds(45, 355, 600, 60);
        greenPanel.add(signupSubtitle);

        // Sign up button
        signupBtn.setBounds(110, 450, 300, 60);
        signupBtn.setBackground(Color.decode("#e6f2cf"));
        signupBtn.setForeground(Color.decode("#4d5d32"));
        signupBtn.setBorder(BorderFactory.createLineBorder(Color.decode("#4d5d32"), 2));
        signupBtn.addActionListener(mainController);
        signupBtn.setActionCommand("Signup Change");
        greenPanel.add(signupBtn);

        // Login title
        loginTitle.setFont(new Font("SansSerif", Font.BOLD, 40));
        loginTitle.setForeground(Color.decode("#4d5d32"));
        loginTitle.setBounds(290, 190, 600, 50);
        add(loginTitle);
        
        // Login subtitle
        loginSubtitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        loginSubtitle.setForeground(Color.decode("#4d5d32"));
        loginSubtitle.setBounds(270, 240, 600, 50);
        add(loginSubtitle);

        // Email title
        emailTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        emailTitle.setBounds(340, 310, 400, 50);
        emailTitle.setForeground(Color.decode("#4d5d32"));
        emailIcon.setBounds(300, 310, 40, 50);
        email.setBounds(300, 360, 400, 50);
        add(emailIcon);
        add(emailTitle);
        add(email);

        // Password title
        passwordTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        passwordTitle.setBounds(340, 440, 400, 50);
        passwordTitle.setForeground(Color.decode("#4d5d32"));
        passwordIcon.setBounds(300, 440, 40, 50);
        password.setBounds(300, 490, 400, 50);
        add(passwordIcon);
        add(passwordTitle);
        add(password);

        // Login button
        loginBtn.setBounds(350, 590, 300, 60);
        loginBtn.setBackground(Color.decode("#4d5d32"));
        loginBtn.setForeground(Color.decode("#e6f2cf"));
        loginBtn.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
        loginBtn.addActionListener(mainController);
        loginBtn.setActionCommand("Login Final");
        add(loginBtn);
        
        // Forgot password hyperlink label
        forgotPasswordLabel.setForeground(Color.decode("#4d5d32"));
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.setBounds(440, 540, 200, 30); 
        add(forgotPasswordLabel);
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginController.handlePasswordReset(LoginPanel.this);
            }
        });

        add(greenPanel);
    }

    // Getters & setters
	public String getEmailText() {
	    return email.getText();
	}
	public String getPasswordText() {
	    return new String(password.getPassword());
	}
	public JTextField getEmail() {
		return email;
	}
	public JPasswordField getPassword() {
		return password;
	}
}