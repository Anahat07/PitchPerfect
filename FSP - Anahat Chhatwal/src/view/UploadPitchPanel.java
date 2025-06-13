package view;

// Imports
import javax.swing.*;
import java.awt.*;
import controller.*;

// UploadPitchPanel is the panel that allows users to submit their startup pitch either by typing/pasting text directly or uploading a .pdf/.txt file.
public class UploadPitchPanel extends JPanel {
	
	// Instances
    MainController mainController;
    PdfController pdfController;
	
	// GUI Components
    private JTextArea pitchTextArea;
    private JButton analyzeButton, helpButton, clearButton;
    private JButton browseButton;
    private JLabel statusLabel;

    // Constructor
    public UploadPitchPanel(MainController mainController, PdfController pdfController) {
    	this.mainController = mainController;
    	this.pdfController = pdfController;
    	
    	// Layout
        setSize(1920, 1080);
        setLayout(null);
        setBackground(Color.decode("#e6f2cf"));

        // Sidebar for navigation
        SidebarPanel sidebar = new SidebarPanel();
        sidebar.addSidebarListeners(mainController);
        add(sidebar);

        // Title
        JLabel title = new JLabel("Ready to Launch? Submit Your Startup Pitch Below.", SwingConstants.CENTER);
        title.setForeground(Color.decode("#3a5600"));
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setBounds(-90, 40, 1720, 40);
        add(title);

        // Text input label
        JLabel textLabel = new JLabel("Paste your pitch directly into the text box below.");
        textLabel.setForeground(Color.decode("#3a5600"));
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        textLabel.setBounds(130, 140, 600, 30);
        add(textLabel);
        pitchTextArea = new JTextArea("Enter pitch content here...");
        pitchTextArea.setLineWrap(true);
        pitchTextArea.setWrapStyleWord(true);
        pitchTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        pitchTextArea.setForeground(Color.decode("#3a5600"));
        JScrollPane scrollPane = new JScrollPane(pitchTextArea);
        scrollPane.setBounds(130, 180, 600, 400);
        add(scrollPane);

        // OR label
        JLabel orLabel = new JLabel("OR");
        orLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        orLabel.setForeground(Color.decode("#3a5600"));
        orLabel.setBounds(765, 370, 50, 30);
        add(orLabel);

        // Upload label
        JLabel uploadLabel = new JLabel("Upload a .pdf or .txt file of your pitch.");
        uploadLabel.setForeground(Color.decode("#3a5600"));
        uploadLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        uploadLabel.setBounds(840, 140, 600, 30);
        add(uploadLabel);
        JPanel uploadPanel = new JPanel();
        uploadPanel.setBorder(BorderFactory.createTitledBorder(
        	    BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2),
        	    "File Upload",                                               
        	    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
        	    javax.swing.border.TitledBorder.DEFAULT_POSITION,
        	    new Font("SansSerif", Font.BOLD, 18),                      
        	    Color.decode("#e6f2cf")                            
        	));
        uploadPanel.setBounds(840, 180, 600, 400);
        uploadPanel.setBackground(Color.decode("#3a5600"));
        uploadPanel.setLayout(null);
        
        // Browse button
        browseButton = new RoundedButton("Browse Files");
        browseButton.setFont(new Font("Arial", Font.ITALIC, 26));
        browseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
        browseButton.setBounds(200, 150, 200, 50);
        browseButton.addActionListener(mainController);
        browseButton.setActionCommand("Browse");
        uploadPanel.add(browseButton);

        // Status label
        statusLabel = new JLabel("No file uploaded.");
        statusLabel.setForeground(Color.decode("#e6f2cf"));
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        statusLabel.setBounds(220, 220, 400, 30);
        uploadPanel.add(statusLabel);
        add(uploadPanel);

        // Analyze button
        analyzeButton = new RoundedButton("Analyze Pitch");
        analyzeButton.setFont(new Font("Arial", Font.ITALIC, 22));
        analyzeButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
        analyzeButton.addActionListener(mainController);
        analyzeButton.setActionCommand("Analyze");
        analyzeButton.setBounds(490, 650, 180, 50);
        add(analyzeButton);

        // Help button
        helpButton = new RoundedButton("Pitch Format Instructions");
        helpButton.setFont(new Font("Arial", Font.ITALIC, 20));
        helpButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
        helpButton.addActionListener(mainController);
        helpButton.setActionCommand("Pitch Format Instructions");
        helpButton.setBounds(690, 650, 200, 50);
        add(helpButton);

        // Clear button
        clearButton = new RoundedButton("Clear");
        clearButton.setFont(new Font("Arial", Font.ITALIC, 22));
        clearButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
        clearButton.addActionListener(mainController);
        clearButton.setActionCommand("Clear");
        clearButton.setBounds(910, 650, 120, 50);
        add(clearButton);
        
        setVisible(true);
    }
    
    // Getters & setters
    public JButton getBrowseButton() {
        return browseButton;
    }
    public JTextArea getPitchTextArea() {
        return pitchTextArea;
    }
    public JLabel getStatusLabel() {
        return statusLabel;
    }
    public JButton getAnalyzeButton() {
        return analyzeButton;
    }
    public JButton getHelpButton() {
        return helpButton;
    }  
    public JButton getClearButton() {
        return clearButton;
    }
}