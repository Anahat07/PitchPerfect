package view;

// Imports
import javax.swing.*;
import controller.*;
import java.awt.*;
import java.util.Arrays;

// SignUpPanel is the user interface panel for new users to create an account in the PitchPerfect app.
public class SignUpPanel extends JPanel {
    
	// Instance
	MainController mainController;
	
	// GUI Components
	JPanel greenPanel = new JPanel();
	JLabel loginTitle = new JLabel("Welcome Back!");
	JLabel loginSubtitle = new JLabel("<html><center>Ready to elevate your pitch?<br>Log in to pick up where you left off.</center></html>");
	RoundedButton loginBtn = new RoundedButton("Login");
    JLabel signupTitle = new JLabel("Create Account");
    JLabel signupSubtitle = new JLabel("Sign up using your email.");
	RoundedButton signupBtn = new RoundedButton("Sign Up");
	JLabel logoImage = new JLabel(new ImageIcon("images/smalllogolight.png"));
	JLabel nameIcon = new JLabel(new ImageIcon("images/nameIcon.png"));
	JLabel emailIcon = new JLabel(new ImageIcon("images/emailIcon.png"));
	JLabel passwordIcon = new JLabel(new ImageIcon("images/passwordIcon.png"));
	JLabel nameTitle = new JLabel("Name");
	JLabel emailTitle = new JLabel("Email");
	JLabel passwordTitle = new JLabel("Password");
    JTextField name = new JTextField();
    JTextField email = new JTextField();
    JPasswordField password = new JPasswordField();
    
	// Constructor
    public SignUpPanel(MainController mainController) {
        this.mainController = mainController;
        
        // Layout
        setLayout(null);
        setBackground(Color.decode("#e6f2cf"));

        // Left panel
        greenPanel.setBackground(Color.decode("#4d5d32"));
        greenPanel.setBounds(0, 0, 500, 1080);
        greenPanel.setLayout(null);
        
        // Logo image
        logoImage.setBounds(0, 10, 150, 60);
        greenPanel.add(logoImage);

        // Login title
        loginTitle.setFont(new Font("SansSerif", Font.BOLD, 40));
        loginTitle.setForeground(Color.decode("#e6f2cf"));
        loginTitle.setBounds(90, 280, 500, 50);
        greenPanel.add(loginTitle);

        // login subtitle
        loginSubtitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        loginSubtitle.setForeground(Color.decode("#e6f2cf"));
        loginSubtitle.setBounds(55, 355, 600, 60);
        greenPanel.add(loginSubtitle);

        // Login button
        loginBtn.setBounds(90, 450, 300, 60);
        loginBtn.setBackground(Color.decode("#e6f2cf"));
        loginBtn.setForeground(Color.decode("#4d5d32"));
        loginBtn.setBorder(BorderFactory.createLineBorder(Color.decode("#4d5d32"), 2));
        loginBtn.addActionListener(mainController);
        loginBtn.setActionCommand("Login Change");
        greenPanel.add(loginBtn);

        // Signup title
        signupTitle.setFont(new Font("SansSerif", Font.BOLD, 40));
        signupTitle.setForeground(Color.decode("#4d5d32"));
        signupTitle.setBounds(840, 90, 600, 50);
        add(signupTitle);
        
        // Signup subtitle
        signupSubtitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        signupSubtitle.setForeground(Color.decode("#4d5d32"));
        signupSubtitle.setBounds(865, 130, 600, 50);
        add(signupSubtitle);

        // Name title
        nameTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        nameTitle.setBounds(840, 180, 400, 50);
        nameTitle.setForeground(Color.decode("#4d5d32"));
        nameIcon.setBounds(800, 180, 40, 40);
        name.setBounds(800, 230, 400, 50);
        add(nameIcon);
        add(nameTitle);
        add(name);

        // Email title
        emailTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        emailTitle.setBounds(840, 310, 400, 50);
        emailTitle.setForeground(Color.decode("#4d5d32"));
        emailIcon.setBounds(800, 310, 40, 50);
        email.setBounds(800, 360, 400, 50);
        add(emailIcon);
        add(emailTitle);
        add(email);

        // Password title
        passwordTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        passwordTitle.setBounds(840, 440, 400, 50);
        passwordTitle.setForeground(Color.decode("#4d5d32"));
        passwordIcon.setBounds(800, 440, 40, 50);
        password.setBounds(800, 490, 400, 50);
        add(passwordIcon);
        add(passwordTitle);
        add(password);

        // Sign up button
        signupBtn.setBounds(850, 570, 300, 60);
        signupBtn.setBackground(Color.decode("#4d5d32"));
        signupBtn.setForeground(Color.decode("#e6f2cf"));
        signupBtn.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
        signupBtn.addActionListener(mainController);
        signupBtn.setActionCommand("SignUp Final");
        add(signupBtn);

        add(greenPanel);
    }

    // Getters & setters
    public String getNameText() {
        return name.getText().trim();
    }
    public String getEmailText() {
        return email.getText().trim();
    }
    public String getPasswordText() {
        char[] passwordChars = password.getPassword();
        String passwordString = new String(passwordChars).trim();
        Arrays.fill(passwordChars, ' ');
        return passwordString;
    }
}