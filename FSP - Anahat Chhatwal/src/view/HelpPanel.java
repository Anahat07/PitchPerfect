package view;

// Imports
import javax.swing.*;
import controller.*;
import java.awt.*;

// HelpPanel is a JPanel that displays user-friendly help information for the application.
public class HelpPanel extends JPanel {
	
	// Instance
	MainController mainController;

    // Constructor
    public HelpPanel(MainController mainController) {
    	this.mainController = mainController;
        
    	// Layout
        setLayout(new BorderLayout());
        setBackground(Color.decode("#e6f2cf"));
        
        // Sidebar for navigation
        SidebarPanel sidebar = new SidebarPanel();
        sidebar.addSidebarListeners(mainController);
        sidebar.setPreferredSize(new Dimension(100, 1080)); // Fix sidebar width & height
        add(sidebar, BorderLayout.WEST); 

        // FAQs section
        JPanel definitionsPanel = createSection("FAQs", 
            "What does PitchPerfect do?\nPitchPerfect analyzes startup pitch decks or texts and provides feedback on clarity, market fit, and feasibility.\n\n"
          + "What kind of files can I upload?\nYou can upload plain text or PDF pitch files.\n\n"
          + "What is SWOT analysis?\nSWOT stands for Strengths, Weaknesses, Opportunities, and Threats. It is used to assess key areas of a business.\n\n"
          + "What is the purpose of star ratings?\nPitchPerfect gives scores out of 5 stars in three areas; clarity, feasibility, and market fit, to help identify how strong the pitch is.\n\n"
          + "Where is my pitch history stored?\nYour previous pitch analyses are saved and viewable on your dashboard.\n\n"
          + "Can I export results?\nYes. You can export pitch summaries and scores as a PDF using the 'Export' option in the Dashboard.");

        // Glossary section
        JPanel glossaryPanel = createSection("Glossary", 
            "Dashboard --> Displays the most recent pitch analysis and overall stats like total pitches and average score.\n\n"
          + "Upload Pitch --> Allows you to upload a pitch file (PDF or text) for analysis.\n\n"
          + "Run Analysis --> Analyzes the uploaded pitch and extracts components such as Problem, Solution, Market, and Revenue Model.\n\n"
          + "View Suggestions --> Shows AI-generated improvement tips as a checklist based on your last pitch.\n\n"
          + "Compare Pitches --> Lets you compare scores of multiple saved pitches to see which performs best.\n\n"
          + "Edit Profile --> Update your name or password.\n\n"
          + "Security --> Allows you to change your password by entering your old and new password.\n\n"
          + "Help --> Opens this help screen with guidance on using PitchPerfect.\n\n"
          + "Quit --> Closes the application safely.");

        // SplitPane to show FAQs and Glossary side-by-side
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, definitionsPanel, glossaryPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(5);
        add(splitPane, BorderLayout.CENTER);
    }

    // Helper method to create a bordered section panel with a title and description
    private JPanel createSection(String title, String descriptionText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.decode("#3a5600"), 2));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.decode("#3a5600"));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextArea description = new JTextArea(descriptionText);
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setEditable(false);
        description.setBackground(panel.getBackground());
        description.setFont(new Font("SansSerif", Font.PLAIN, 14));
        description.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        description.setForeground(Color.decode("#3a5600"));
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(description), BorderLayout.CENTER);
        return panel;
    }
}