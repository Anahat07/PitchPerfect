package controller;

// Imports
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.*;
import view.*;

// MainController handles all application-wide actions and user interactions within the PitchPerfect app, including button actions.
public class MainController implements ActionListener {
    
	// Core controllers
    private PitchPerfectFrame pitchPerfectFrame;
    private ViewController viewController;
    private LoginController loginController;
    private PdfController pdfController;
    private PitchController pitchController;

    // Panels used throughout the app
    private DashboardPanel dashboardPanel;
    private UploadPitchPanel uploadPitchPanel;
    private AnalyzePanel analyzePanel;

    // For tracking the most recently analyzed pitch and score
    public Pitch lastAnalyzedPitch;
    private PitchScore lastPitchScore;
    
    // Constructor initializes controllers, panels, and sets the initial view
    public MainController() {
        loginController = new LoginController();
        pdfController = new PdfController();
        pitchController = new PitchController("pitches.csv");

        HomePanel homePanel = new HomePanel(this);
        dashboardPanel = new DashboardPanel(this, loginController);
        uploadPitchPanel = new UploadPitchPanel(this, pdfController);
        analyzePanel = new AnalyzePanel(this);

        pitchPerfectFrame = new PitchPerfectFrame(this, homePanel, homePanel.getMenuBar(), loginController, pdfController, pitchController);

        viewController = new ViewController(pitchPerfectFrame, loginController, dashboardPanel, uploadPitchPanel, analyzePanel, pitchController, this);
        viewController.switchToHomePanel();
    }
    
    //Opens a file chooser to allow user to select a .txt or .pdf pitch file and loads its content into the UploadPitchPanel.
    private void browseAndLoadFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF and TXT files", "pdf", "txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String content = pdfController.loadFileContent(selectedFile);
                // Update text area and status label in UploadPitchPanel
                uploadPitchPanel.getPitchTextArea().setText(content);
                uploadPitchPanel.getStatusLabel().setText("Loaded: " + selectedFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to load file: " + e.getMessage());
                uploadPitchPanel.getStatusLabel().setText("Failed to load file.");
            }
        } else {
            uploadPitchPanel.getStatusLabel().setText("File selection cancelled.");
        }
    }
    
    // Extracts the pitch title by taking the first line of the text.
    private String extractTitleFromPitch(String pitchText) {
        if (pitchText == null || pitchText.trim().isEmpty()) {
            return "Untitled Pitch";
        }
        String[] lines = pitchText.split("\\r?\\n");
        if (lines.length > 0) {
            String firstLine = lines[0].trim();
            return firstLine.substring(0, Math.min(firstLine.length(), 50));
        }
        return "Untitled Pitch";
    }

    // Handles button and menu actions throughout the application - ActionPerformed.
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		// Menu bar actions
		if (command.equalsIgnoreCase("Quit")) {
			System.exit(0);
		} else if(command.equalsIgnoreCase("Home")) {
			viewController.switchToHomePanel();
		} else if (command.equalsIgnoreCase("Help") || (command.equalsIgnoreCase("Right Help"))) {
	    	viewController.switchToHelpPanel();
		} else if (command.equalsIgnoreCase("view my pitches") || (command.equalsIgnoreCase("Dashboard"))) {
	    	viewController.switchToDashboardPanel();
	    } else if (command.equalsIgnoreCase("Run Analysis")) {
	    	viewController.switchToAnalyzePanel();
	    } else if (command.equalsIgnoreCase("logout")) {
	    	loginController.logout();
	    	pitchPerfectFrame.loginPanel.getEmail().setText("");
	        pitchPerfectFrame.loginPanel.getPassword().setText("");
	    	JOptionPane.showMessageDialog(null, "You have been logged out.");
	    	viewController.switchToHomePanel();
	    }
		// Sidebar actions
	    else if (command.equalsIgnoreCase("Sidebar Home")) {
	    	viewController.switchToDashboardPanel();
	    } else if (command.equalsIgnoreCase("Sidebar Upload")) {
	    	viewController.switchToUploadPitchPanel();
	    } else if (command.equalsIgnoreCase("Sidebar Analyze")) {
	    	viewController.switchToAnalyzePanel();
	    } else if (command.equalsIgnoreCase("Sidebar Compare")) {
	    	viewController.switchToComparePitchesPanel();
	    } else if (command.equalsIgnoreCase("Sidebar Settings") || command.equalsIgnoreCase("Edit profile")) {
	    	viewController.switchToEditProfilePanel();
	    } else if (command.equalsIgnoreCase("Right Edit profile")) {
	    	viewController.switchToEditProfilePanel();
	    } else if (command.equalsIgnoreCase("Right Security")) {
	    	viewController.switchToSecurityPanel();
	    }
		// Login & signup actions
		else if (command.equalsIgnoreCase("Login")) {
	        viewController.switchToLoginPanel();
	    } else if (command.equalsIgnoreCase("SignUp")) {
	        viewController.switchToSignupPanel();
	    } else if (command.equalsIgnoreCase("Login Change")) {
	    	viewController.switchToLoginPanel();
	    } else if (command.equalsIgnoreCase("SignUp Final")) {
	    	SignUpPanel signUpPanel = pitchPerfectFrame.getSignUpPanel();
	        String name = signUpPanel.getNameText();
	        String email = signUpPanel.getEmailText();
	        String password = signUpPanel.getPasswordText();
	        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Error: Please fill out ALL fields.");
	            return;
	        }
	        User newUser = new User(name, email, password);
	        loginController.saveUserToCSV(newUser);
	        JOptionPane.showMessageDialog(null, "Account created successfully! Please login to continue.");
	        viewController.switchToLoginPanel();
	    } else if (command.equalsIgnoreCase("Signup Change")) {
	    	viewController.switchToSignupPanel();
	    } else if (command.equalsIgnoreCase("Login Final")) {
	    	String email = pitchPerfectFrame.loginPanel.getEmailText().trim();
	    	String password = pitchPerfectFrame.loginPanel.getPasswordText().trim();
	        if (email.isEmpty() || password.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Please enter both email and password.");
	            return;
	        }
	        boolean isValid = loginController.validateLogin(email, password);
	        if (isValid) {
	            JOptionPane.showMessageDialog(null, "Login successful! Redirecting to Dashboard...");
	            viewController.switchToDashboardPanel();
	        } else {
	            JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.");
	        }
	    } 
	    // Dashboard actions
	    else if (command.equalsIgnoreCase("New Pitch")) {
	    	viewController.switchToUploadPitchPanel();
	    } 
	    // Analyze actions
	    else if (command.equalsIgnoreCase("Browse")) {
	    	browseAndLoadFile();
	    } else if (command.equalsIgnoreCase("Analyze")) {
	        User currentUser = loginController.getCurrentUser();
	        if (currentUser == null) {
	            JOptionPane.showMessageDialog(null, "You must be logged in to analyze a pitch.");
	            return;
	        }
	        String fullPitchText = uploadPitchPanel.getPitchTextArea().getText().trim();
	        if (fullPitchText.isEmpty() || fullPitchText.equalsIgnoreCase("Enter pitch content here...") 
	        		|| fullPitchText.equalsIgnoreCase("File cleared - enter new pitch content here...")) {
	            JOptionPane.showMessageDialog(null, "Please upload a pitch file or enter your pitch in the text box before analyzing.");
	            return;
	        }
	        String pitchTitle = extractTitleFromPitch(fullPitchText);
	        Pitch pitch = new Pitch(
	            pitchTitle,
	            "", "", "", "", "", fullPitchText,
	            0.0, 0.0, 0.0,
	            0.0
	        );
	        PitchScore score = PitchAnalyzer.analyze(pitch.getRawText());
		     pitch.setClarityStars(score.getClarityStars());
		     pitch.setFeasibilityStars(score.getFeasibilityStars());
		     pitch.setMarketFitStars(score.getMarketFitStars());
		     pitch.setClarityScore(score.getClarityScore());
		     pitch.setFeasibilityScore(score.getFeasibilityScore());
		     pitch.setMarketFitScore(score.getMarketFitScore());
		     pitch.setAverageScore(score.getAverageScore());
		     pitch.setSwotAnalysis(String.join("\n", score.getSwotAnalysis()));
		     pitch.setProblemRating(score.getProblemRating());
		     pitch.setSolutionRating(score.getSolutionRating());
		     pitch.setMarketRating(score.getMarketRating());
		     pitch.setRevenueRating(score.getRevenueRating());
		     pitch.saveToCSV("pitches.csv", currentUser.getEmail());
		     setLastAnalyzedPitch(pitch);
		     setLastPitchScore(score);
		     analyzePanel.updatePitchSections(score); 
		     dashboardPanel.refreshPitchTable();
		     dashboardPanel.refreshSummaryAndSuggestions();
		     JOptionPane.showMessageDialog(null, "Pitch saved and analyzed successfully!");
		     viewController.switchToAnalyzePanel();
	    } else if (command.equalsIgnoreCase("Clear")) {
	    	uploadPitchPanel.getPitchTextArea().setText("File cleared - enter new pitch content here...");
	        uploadPitchPanel.getStatusLabel().setText("File cleared - upload new file here.");
	    } else if (command.equalsIgnoreCase("Pitch Format Instructions")) {
	        String instructions = 
	                "Please format your pitch as follows:\n\n" +
	                "Name: [CONTENT HERE]\n" +
	                "Problem: [CONTENT HERE]\n" +
	                "Solution: [CONTENT HERE]\n" +
	                "Market: [CONTENT HERE]\n" +
	                "Business Model: [CONTENT HERE]";
	            JOptionPane.showMessageDialog(null, instructions, 
	                "Pitch Formatting Instructions", JOptionPane.INFORMATION_MESSAGE);
	    }
	    // Pitch analysis actions
	    else if (command.equalsIgnoreCase("Compare Pitch")) {
	    	viewController.switchToComparePitchesPanel();
	    } else if (command.equalsIgnoreCase("Compare Back") || command.equalsIgnoreCase("Upload Pitch"))  {
	    	viewController.switchToUploadPitchPanel();
	    } else if (command.equalsIgnoreCase("Export Analysis")) {
	    	exportAnalysisToFile();
	    } 
	    // Edit profile & security actions
	    else if (command.equalsIgnoreCase("Edit Save")) {
	        EditProfilePanel editPanel = pitchPerfectFrame.getEditProfilePanel();
	        String newName = editPanel.getNameField().getText().trim();
	        String newEmail = editPanel.getEmailField().getText().trim();
	        User currentUser = loginController.getCurrentUser();
	        if (currentUser == null) {
	            JOptionPane.showMessageDialog(null, "You must be logged in to edit your profile.");
	            return;
	        }
	        if (newName.isEmpty() || newEmail.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Name and Email cannot be empty.");
	            return;
	        }
	        String oldEmail = currentUser.getEmail();
	        boolean success = loginController.updateUserInfo(oldEmail, newName, newEmail);
	        if (success) {
	            if (!oldEmail.equalsIgnoreCase(newEmail)) {
	                updatePitchesEmail(oldEmail, newEmail);
	            }
	            JOptionPane.showMessageDialog(null, "Profile updated successfully!");
	            dashboardPanel.refreshPitchTable();
	        } else {
	            JOptionPane.showMessageDialog(null, "Failed to update profile.");
	        }
	    } else if (command.equalsIgnoreCase("Edit Cancel")) {
	        EditProfilePanel editProfilePanel = pitchPerfectFrame.getEditProfilePanel();
	        if (editProfilePanel != null) {
	            editProfilePanel.getNameField().setText("");
	            editProfilePanel.getEmailField().setText("");
	        }
	    } else if (command.equalsIgnoreCase("Security Save")) {
	        User currentUser = loginController.getCurrentUser();
	        if (currentUser == null) {
	            JOptionPane.showMessageDialog(null, "You must be logged in to change your password.");
	            return;
	        }
	        SecurityPanel securityPanel = pitchPerfectFrame.getSecurityPanel();
	        String currentPasswordInput = securityPanel.getCurrentPassword();
	        String newPasswordInput = securityPanel.getNewPassword();
	        if (currentPasswordInput.isEmpty() || newPasswordInput.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Please fill all password fields.");
	            return;
	        }
	        if (!currentPasswordInput.equals(currentUser.getPassword())) {
	            JOptionPane.showMessageDialog(null, "Current password is incorrect.");
	            return;
	        }
	        currentUser.setPassword(newPasswordInput);
	        boolean updateSuccess = LoginController.updatePassword(currentUser.getEmail(), newPasswordInput);
	        if (updateSuccess) {
	            JOptionPane.showMessageDialog(null, "Password updated successfully.");
	            securityPanel.clearPasswordFields();
	        } else {
	            JOptionPane.showMessageDialog(null, "Failed to update password. Please try again.");
	        }
	    } else if (command.equalsIgnoreCase("Security Cancel")) {
	        SecurityPanel securityPanel = pitchPerfectFrame.getSecurityPanel();
	        if (securityPanel != null) {
	        	securityPanel.clearPasswordFields();
	        }
	    } 
	}
	
	// Exports the last analyzed pitch and score as a report text file, allowing user to export it to their device.
	private void exportAnalysisToFile() {
	    PitchScore score = getLastPitchScore(); 
	    if (score == null) {
	        JOptionPane.showMessageDialog(null, "No pitch analyzed yet to export.");
	        return;
	    }
	    StringBuilder sb = new StringBuilder();
	    sb.append(lastAnalyzedPitch.getTitle()).append("\n\n");
	    sb.append("Pitch Analysis Report\n");
	    sb.append("======================\n\n");
	    sb.append("Problem: ").append(score.getProblem()).append("\n");
	    sb.append("→ Rating: ").append(score.getProblemRating()).append("\n\n");
	    sb.append("Solution: ").append(score.getSolution()).append("\n");
	    sb.append("→ Rating: ").append(score.getSolutionRating()).append("\n\n");
	    sb.append("Market: ").append(score.getMarket()).append("\n");
	    sb.append("→ Rating: ").append(score.getMarketRating()).append("\n\n");
	    sb.append("Revenue Model: ").append(score.getRevenue()).append("\n");
	    sb.append("→ Rating: ").append(score.getRevenueRating()).append("\n\n");
	    sb.append("General Ratings:\n");
	    sb.append("→ Clarity: ").append(score.getClarityStars()).append("\n");
	    sb.append("→ Feasibility: ").append(score.getFeasibilityStars()).append("\n");
	    sb.append("→ Market Fit: ").append(score.getMarketFitStars()).append("\n\n");
	    sb.append("Suggestions:\n");
	    for (String suggestion : score.getSwotAnalysis()) {
	        sb.append("→ ").append(suggestion).append("\n");
	    }
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setSelectedFile(new java.io.File("PitchAnalysis.txt"));
	    int result = fileChooser.showSaveDialog(null);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        try {
	            java.io.FileWriter writer = new java.io.FileWriter(fileChooser.getSelectedFile());
	            writer.write(sb.toString());
	            writer.close();
	            JOptionPane.showMessageDialog(null, "Analysis exported successfully!");
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Error writing file: " + e.getMessage());
	        }
	    }
	}
	
	// Switches to the Compare Pitches panel and passes the user's email.
    public void switchToComparePitchesPanel() {
        User user = loginController.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            ComparePitchesPanel comparePitchesPanel = new ComparePitchesPanel(this, pitchController, email);
            pitchPerfectFrame.updateView(comparePitchesPanel, pitchPerfectFrame.getMainMenuBar());
        }
    }
    
    // Updates all saved pitch records for a user to reflect a changed email.
    private void updatePitchesEmail(String oldEmail, String newEmail) {
        List<Pitch> allPitches = pitchController.getAllPitches();
        boolean updated = false;
        for (Pitch pitch : allPitches) {
            if (pitch.getUserEmail().equalsIgnoreCase(oldEmail)) {
                pitch.setUserEmail(newEmail);
                updated = true;
            }
        }
        if (updated) {
            try {
                Pitch.saveAllPitches("pitches.csv", allPitches);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error updating pitches with new email: " + e.getMessage());
            }
        }
    }
    
    // Getters and setters for pitch analysis tracking
    public PitchScore getLastPitchScore() {
        return lastPitchScore;
    }
    public Pitch getLastAnalyzedPitch() {
        return lastAnalyzedPitch;
    }
    public void setLastAnalyzedPitch(Pitch pitch) {
        this.lastAnalyzedPitch = pitch;
    }
    public void setLastPitchScore(PitchScore score) {
        this.lastPitchScore = score;
    }
}