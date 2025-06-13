package controller;

// Imports
import model.*;
import view.*;

// The ViewController class manages screen navigation and transitions between panels.
public class ViewController {

	// Fields
    private PitchPerfectFrame pitchPerfectFrame;
    private LoginController loginController;
    private DashboardPanel dashboardPanel;
    private UploadPitchPanel uploadPitchPanel;
    private AnalyzePanel analyzePanel;
    private PitchController pitchController;
    private MainController mainController;

    // Constructor for initializing the ViewController with all necessary components.
    public ViewController(PitchPerfectFrame frame,
                          LoginController loginController, DashboardPanel dashboardPanel, UploadPitchPanel uploadPitchPanel,
                          AnalyzePanel analyzePanel, PitchController pitchController, MainController mainController) {
        this.pitchPerfectFrame = frame;
        this.loginController = loginController;
        this.dashboardPanel = dashboardPanel;
        this.uploadPitchPanel = uploadPitchPanel;
        this.analyzePanel = analyzePanel;
        this.pitchController = pitchController;
        this.mainController = mainController;
    }

    // Switches the view to the Home panel.
    public void switchToHomePanel() {
        pitchPerfectFrame.updateView(pitchPerfectFrame.homePanel, pitchPerfectFrame.homePanel.getMenuBar());
    }

    // Switches the view to the Login panel.
    public void switchToLoginPanel() {
        pitchPerfectFrame.updateView(pitchPerfectFrame.loginPanel, pitchPerfectFrame.homePanel.getMenuBar());
    }

    // Switches the view to the Signup panel.
    public void switchToSignupPanel() {
        pitchPerfectFrame.updateView(pitchPerfectFrame.signUpPanel, pitchPerfectFrame.homePanel.getMenuBar());
    }

    //Switches the view to the Dashboard panel.
    public void switchToDashboardPanel() {
    	// Updates the welcome message and email display using the current logged-in user's info.
        User user = loginController.getCurrentUser();
        if (user != null) {
            dashboardPanel.setWelcomeMessage(user.getName());
            dashboardPanel.setUserEmail(user.getEmail());
        }
        pitchPerfectFrame.updateView(dashboardPanel, pitchPerfectFrame.getMainMenuBar());
        dashboardPanel.refreshPitchTable();
    }
    
    // Switches the view to the Upload Pitch panel.
    public void switchToUploadPitchPanel() {
        pitchPerfectFrame.updateView(uploadPitchPanel, pitchPerfectFrame.getMainMenuBar());
    }
    
    // Switches the view to the Analyze panel.
    public void switchToAnalyzePanel() {
        pitchPerfectFrame.updateView(analyzePanel, pitchPerfectFrame.getMainMenuBar());
    }
    
    // Switches the view to the Compare Pitches panel.
    public void switchToComparePitchesPanel() {
    	// Initializes it with the current user's email to load their specific pitch history.
        User user = loginController.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            ComparePitchesPanel comparePitchesPanel = new ComparePitchesPanel(mainController, pitchController, email);
            pitchPerfectFrame.updateView(comparePitchesPanel, pitchPerfectFrame.getMainMenuBar());
        }
    }

    // Switches the view to the Edit Profile panel.
    public void switchToEditProfilePanel() {
        pitchPerfectFrame.updateView(pitchPerfectFrame.editProfilePanel, pitchPerfectFrame.getMainMenuBar());
    }
    
    // Switches the view to the Security panel.
    public void switchToSecurityPanel() {
        pitchPerfectFrame.updateView(pitchPerfectFrame.securityPanel, pitchPerfectFrame.getMainMenuBar());
    }
    
    // Switches the view to the Help panel.
    public void switchToHelpPanel() {
        pitchPerfectFrame.updateView(pitchPerfectFrame.helpPanel, pitchPerfectFrame.getMainMenuBar());
    }
}