package view;

// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.*;
import model.*;

// AnalyzePanel is the panel that displays the breakdown and analysis results of a startup pitch.
public class AnalyzePanel extends JPanel {
    
	// Instances
	MainController mainController;

    // Labels to display descriptions, ratings, and suggestions for pitch components.
    private JLabel problemDescLabel;
    private JLabel problemRatingLabel;
    private JLabel solutionDescLabel;
    private JLabel solutionRatingLabel;
    private JLabel marketDescLabel;
    private JLabel marketRatingLabel;
    private JLabel revenueDescLabel;
    private JLabel revenueRatingLabel;
    private JLabel clarityRatingLabel;
    private JLabel feasibilityRatingLabel;
    private JLabel marketFitRatingLabel;
    private JLabel suggestion1Label;
    private JLabel suggestion2Label;
    private JLabel suggestion3Label;
    private JLabel suggestion4Label;

    // Constructor
    public AnalyzePanel(MainController mainController) {
        this.mainController = mainController;

        // Layout
        setSize(1920, 1080);
        setLayout(null);
        setBackground(Color.decode("#e6f2cf"));

        // Sidebar for navigation
        SidebarPanel sidebar = new SidebarPanel();
        sidebar.addSidebarListeners(mainController);
        add(sidebar);

        // Scrollable content panel	
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 0, 1700, 1080);
        scrollPane.setBackground(Color.decode("#e6f2cf"));
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.decode("#e6f2cf"));
        contentPanel.setPreferredSize(new Dimension(1680, 1400));

        // Title label
        JLabel titleLabel = new JLabel("Your Pitch; Unpacked");
        titleLabel.setBounds(50, 50, 600, 60);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.decode("#4a5a2e"));
        contentPanel.add(titleLabel);

        // Each pitch component section (problem, solution, etc.)
        problemDescLabel = new JLabel();
        problemRatingLabel = new JLabel();
        JPanel problemPanel = createSectionPanel("Problem", problemDescLabel, problemRatingLabel);
        problemPanel.setBounds(50, 150, 1250, 100);
        contentPanel.add(problemPanel);
        solutionDescLabel = new JLabel();
        solutionRatingLabel = new JLabel();
        JPanel solutionPanel = createSectionPanel("Solution", solutionDescLabel, solutionRatingLabel);
        solutionPanel.setBounds(50, 280, 1250, 100);
        contentPanel.add(solutionPanel);
        marketDescLabel = new JLabel();
        marketRatingLabel = new JLabel();
        JPanel marketPanel = createSectionPanel("Market", marketDescLabel, marketRatingLabel);
        marketPanel.setBounds(50, 410, 1250, 100);
        contentPanel.add(marketPanel);
        revenueDescLabel = new JLabel();
        revenueRatingLabel = new JLabel();
        JPanel revenuePanel = createSectionPanel("Revenue Model", revenueDescLabel, revenueRatingLabel);
        revenuePanel.setBounds(50, 540, 1250, 100);
        contentPanel.add(revenuePanel);

        // Score ratings panel (clarity, feasibility, market fit)
        JPanel ratingsPanel = createRatingsPanel();
        ratingsPanel.setBounds(50, 670, 600, 250);
        contentPanel.add(ratingsPanel);

        // Suggestions panel from SWOT analysis
        JPanel suggestionsPanel = createSuggestionsPanel();
        suggestionsPanel.setBounds(700, 670, 600, 250);
        contentPanel.add(suggestionsPanel);

        // Navigation buttons panel
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(100, 950, 1580, 80);
        contentPanel.add(buttonPanel);

        // Finalize scroll pane
        scrollPane.setViewportView(contentPanel);
        add(scrollPane);

        setVisible(true);
    }

    // Creates a coloured panel showing a pitch section with its description and rating.
    private JPanel createSectionPanel(String title, JLabel descLabel, JLabel ratingLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#4a5a2e"));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(30, 15, 300, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.decode("#e6f2cf"));
        panel.add(titleLabel);
        descLabel.setBounds(30, 45, 1400, 25);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        descLabel.setForeground(Color.decode("#c8d6a8"));
        panel.add(descLabel);
        JLabel ratingTitleLabel = new JLabel("Rating");
        ratingTitleLabel.setBounds(1450, 15, 100, 30);
        ratingTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        ratingTitleLabel.setForeground(Color.decode("#e6f2cf"));
        panel.add(ratingTitleLabel);
        ratingLabel.setBounds(1450, 45, 80, 25);
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        ratingLabel.setForeground(Color.decode("#c8d6a8"));
        panel.add(ratingLabel);
        return panel;
    }

    // Creates the panel displaying clarity, feasibility, and market fit scores.
    private JPanel createRatingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#4a5a2e"));
        JLabel titleLabel = new JLabel("Rating Scores");
        titleLabel.setBounds(30, 20, 200, 35);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.decode("#e6f2cf"));
        panel.add(titleLabel);
        clarityRatingLabel = new JLabel("Clarity:");
        clarityRatingLabel.setBounds(30, 70, 300, 30);
        clarityRatingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        clarityRatingLabel.setForeground(Color.decode("#c8d6a8"));
        panel.add(clarityRatingLabel);
        feasibilityRatingLabel = new JLabel("Feasibility:");
        feasibilityRatingLabel.setBounds(30, 110, 300, 30);
        feasibilityRatingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        feasibilityRatingLabel.setForeground(Color.decode("#c8d6a8"));
        panel.add(feasibilityRatingLabel);
        marketFitRatingLabel = new JLabel("Market Fit:");
        marketFitRatingLabel.setBounds(30, 150, 300, 30);
        marketFitRatingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        marketFitRatingLabel.setForeground(Color.decode("#c8d6a8"));
        panel.add(marketFitRatingLabel);
        return panel;
    }

    // Creates the suggestions panel populated with SWOT insights.
    private JPanel createSuggestionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#4a5a2e"));
        JLabel titleLabel = new JLabel("Suggestions");
        titleLabel.setBounds(30, 20, 200, 35);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.decode("#e6f2cf"));
        panel.add(titleLabel);
        suggestion1Label = new JLabel("");
        suggestion1Label.setBounds(30, 70, 700, 30);
        suggestion1Label.setFont(new Font("Arial", Font.PLAIN, 20));
        suggestion1Label.setForeground(Color.decode("#c8d6a8"));
        panel.add(suggestion1Label);
        suggestion2Label = new JLabel("");
        suggestion2Label.setBounds(30, 110, 700, 30);
        suggestion2Label.setFont(new Font("Arial", Font.PLAIN, 20));
        suggestion2Label.setForeground(Color.decode("#c8d6a8"));
        panel.add(suggestion2Label);
        suggestion3Label = new JLabel("");
        suggestion3Label.setBounds(30, 150, 700, 30);
        suggestion3Label.setFont(new Font("Arial", Font.PLAIN, 20));
        suggestion3Label.setForeground(Color.decode("#c8d6a8"));
        panel.add(suggestion3Label);
        suggestion4Label = new JLabel("");
        suggestion4Label.setBounds(30, 190, 700, 30);
        suggestion4Label.setFont(new Font("Arial", Font.PLAIN, 20));
        suggestion4Label.setForeground(Color.decode("#c8d6a8"));
        panel.add(suggestion4Label);
        return panel;
    }

    // Creates the bottom navigation panel with action buttons.
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#e6f2cf"));
        String[] buttonTexts = {"Compare Pitch", "Export Analysis", "Help", "Back"};
        String[] actionCommands = {"Compare Pitch", "Export Analysis", "Help", "Compare Back"};
        int buttonWidth = 200;
        int buttonHeight = 60;
        int buttonSpacing = 50;
        int startX = 100;
        for (int i = 0; i < buttonTexts.length; i++) {
            RoundedButton button = new RoundedButton(buttonTexts[i]);
            button.setFont(new Font("Arial", Font.ITALIC, 20));
            button.setBackground(Color.decode("#3a5600"));
            button.setForeground(Color.decode("#e6f2cf"));
            button.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
            button.setActionCommand(actionCommands[i]);
            button.setBounds(startX + (i * (buttonWidth + buttonSpacing)), 20, buttonWidth, buttonHeight);
            button.addActionListener(mainController);
            panel.add(button);
        }
        return panel;
    }

    // Updates the UI components with data from the latest pitch analysis.
    public void updatePitchSections(PitchScore score) {
        problemDescLabel.setText("<html>" + score.getProblem() + "</html>");
        problemDescLabel.setToolTipText(score.getProblem());
        solutionDescLabel.setText("<html>" + score.getSolution() + "</html>");
        solutionDescLabel.setToolTipText(score.getSolution());
        marketDescLabel.setText("<html>" + score.getMarket() + "</html>");
        marketDescLabel.setToolTipText(score.getMarket());
        revenueDescLabel.setText("<html>" + score.getRevenue() + "</html>");
        revenueDescLabel.setToolTipText(score.getRevenue());
        problemRatingLabel.setText(score.getProblemRating());
        solutionRatingLabel.setText(score.getSolutionRating());
        marketRatingLabel.setText(score.getMarketRating());
        revenueRatingLabel.setText(score.getRevenueRating());
        clarityRatingLabel.setText("Clarity: " + score.getClarityStars());
        feasibilityRatingLabel.setText("Feasibility: " + score.getFeasibilityStars());
        marketFitRatingLabel.setText("Market Fit: " + score.getMarketFitStars());
        String[] suggestions = score.getSwotAnalysis();
        if (suggestions.length > 0) suggestion1Label.setText(suggestions[0]);
        if (suggestions.length > 1) suggestion2Label.setText(suggestions[1]);
        if (suggestions.length > 2) suggestion3Label.setText(suggestions[2]);
        if (suggestions.length > 3) suggestion4Label.setText(suggestions[3]);
    }
}