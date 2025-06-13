package view;

// Imports
import javax.swing.*;
import java.awt.*;
import controller.*;

// This class sets up the the base frame for all the screens (panels) that will be added on it
public class PitchPerfectFrame extends JFrame {
	
	// Instances --> for controllers
	MainController mainController;
	LoginController loginController;
	PdfController pdfController;
	public PitchController pitchController;
	
	// Instances --> for view panels
	public HomePanel homePanel;
	public DashboardPanel dashboardPanel;
	public UploadPitchPanel uploadPitchPanel;
	public EditProfilePanel editProfilePanel;
	public SecurityPanel securityPanel;
	public HelpPanel helpPanel;
	ComparePitchesPanel comparePitchesPanel;
    public LoginPanel loginPanel;
    public SignUpPanel signUpPanel;

	// Menu bar
	JMenuBar menuBar = new JMenuBar();
	// Menu items
	JMenu fileMenu = new JMenu("File ⤵");
	JMenu analyzeMenu = new JMenu("Analyze ⤵");
	JMenu accountMenu = new JMenu("Account ⤵");
	JMenuItem helpMenuItem = new JMenuItem("Help");
	JMenuItem quitMenuItem = new JMenuItem("Quit");
	// Submenu items for File
	JMenuItem dashboardMenuItem = new JMenuItem("Dashboard");
	JMenuItem uploadPitchMenuItem = new JMenuItem("Upload Pitch");
	// Submenu items for Analyze
	JMenuItem runAnalysisMenuItem = new JMenuItem("Run Analysis");
	// Submenu items for Account
	JMenuItem logoutMenuItem = new JMenuItem("Logout");
	JMenuItem viewPitchesMenuItem = new JMenuItem("View My Pitches");
	JMenuItem editProfileMenuItem = new JMenuItem("Edit Profile");

	// Constructor
	public PitchPerfectFrame(MainController mainController, JPanel contentPanel, JMenuBar initialMenuBar, LoginController loginController, 
			PdfController pdfController, PitchController pitchController) {
		this.mainController = mainController;
		this.loginController = loginController;
		this.pdfController = pdfController;
		this.pitchController = pitchController;
	    
		// Instantiate all panels
        homePanel = new HomePanel(mainController);
        dashboardPanel = new DashboardPanel(mainController, loginController);
        uploadPitchPanel = new UploadPitchPanel(mainController, pdfController);
        editProfilePanel = new EditProfilePanel(mainController);
        securityPanel = new SecurityPanel(mainController);
        helpPanel = new HelpPanel(mainController);
        loginPanel = new LoginPanel(mainController, loginController);
        signUpPanel = new SignUpPanel(mainController);
		
        // Layout
		setIconImage(new ImageIcon("images/logo.png").getImage());
		setTitle("PitchPerfect");
		getContentPane().setBackground(Color.decode("#e6f2cf"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920, 1080);
		setLocationRelativeTo(null);
		
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
		// Analyze menu
		analyzeMenu.setOpaque(true);
		analyzeMenu.setFont(menuFont);
		analyzeMenu.setBackground(Color.decode("#e6f2cf"));
		analyzeMenu.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		// Account menu
		accountMenu.setOpaque(true);
		accountMenu.setFont(menuFont);
		accountMenu.setBackground(Color.decode("#e6f2cf"));
		accountMenu.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
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
		// Sashbaord submenu item
		dashboardMenuItem.setFont(menuFont);
		dashboardMenuItem.setBackground(Color.decode("#e6f2cf"));
		dashboardMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		dashboardMenuItem.addActionListener(mainController);
		// Upload pitches submenu item
		uploadPitchMenuItem.setFont(menuFont);
		uploadPitchMenuItem.setBackground(Color.decode("#e6f2cf"));
		uploadPitchMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		uploadPitchMenuItem.setActionCommand("Upload Pitch"); 
		uploadPitchMenuItem.addActionListener(mainController);
		// View pitches submenu item
		viewPitchesMenuItem.setFont(menuFont);
		viewPitchesMenuItem.setBackground(Color.decode("#e6f2cf"));
		viewPitchesMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		viewPitchesMenuItem.setActionCommand("View my pitches"); 
		viewPitchesMenuItem.addActionListener(mainController);
		// Edit profile submenu item
		editProfileMenuItem.setFont(menuFont);
		editProfileMenuItem.setBackground(Color.decode("#e6f2cf"));
		editProfileMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		editProfileMenuItem.setActionCommand("Edit Profile"); 
		editProfileMenuItem.addActionListener(mainController);
		// Logout submenu item
		logoutMenuItem.setFont(menuFont);
		logoutMenuItem.setBackground(Color.decode("#e6f2cf"));
		logoutMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		logoutMenuItem.setActionCommand("Logout"); 
		logoutMenuItem.addActionListener(mainController);
		// Run analysis submenu item
		runAnalysisMenuItem.setFont(menuFont);
		runAnalysisMenuItem.setBackground(Color.decode("#e6f2cf"));
		runAnalysisMenuItem.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#4d5d32")));
		runAnalysisMenuItem.setActionCommand("Run Analysis"); 
		runAnalysisMenuItem.addActionListener(mainController);
		// Add submenu items
		fileMenu.add(dashboardMenuItem);
		fileMenu.add(uploadPitchMenuItem);
		analyzeMenu.add(runAnalysisMenuItem);
		accountMenu.add(logoutMenuItem);
		accountMenu.add(viewPitchesMenuItem);
		accountMenu.add(editProfileMenuItem);
		// Add menu bar & menu items
		menuBar.add(fileMenu);
		menuBar.add(analyzeMenu);
		menuBar.add(accountMenu);
		menuBar.add(helpMenuItem);
		menuBar.add(quitMenuItem);
		
		setLayout(new BorderLayout());
		setContentPane(contentPanel);
	    setJMenuBar(initialMenuBar);
		setVisible(true);
	}
	
	// Getters & setters
	public JMenuBar getMainMenuBar() {
	    return menuBar;
	}
    public SignUpPanel getSignUpPanel() {
        return signUpPanel;
    }
    public EditProfilePanel getEditProfilePanel() {
        return this.editProfilePanel;
    }
    public SecurityPanel getSecurityPanel() {
        return securityPanel;
    }

    // Updates the view to the specified panel and updates the menu bar.
    public void updateView(JPanel panel, JMenuBar menuBar) {
        setContentPane(panel);
        setJMenuBar(menuBar);
        revalidate();
        repaint();
    }
}