package view;

// Imports
import controller.*;
import model.*;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// ComparePitchesPanel is the JPanel that allows users to compare multiple startup pitches.
public class ComparePitchesPanel extends JPanel {
	
	// Instances
    private PitchController pitchController;
    private MainController mainController;
    
    // Email of the current user (used to filter their pitches)
    private String userEmail;
    // List of all pitches uploaded by the current user
    private List<Pitch> userPitches;
    // List of dynamically generated checkboxes (one for each pitch)
    private List<JCheckBox> checkBoxes;
    // Panel to display selected pitch details
    private JPanel detailPanel;
    // Panel to display the comparison chart
    private JPanel chartContainer;

    // Constructor
    public ComparePitchesPanel(MainController mainController, PitchController pitchController, String userEmail) {
        this.pitchController = pitchController;
        this.mainController = mainController;
        this.userEmail = userEmail;
        this.userPitches = pitchController.getPitchesForUser(userEmail);

        // Layout
        setSize(1920, 1080);
        setLayout(null);
        setBackground(Color.decode("#e6f2cf"));
        
        // Sidebar for navigation
        SidebarPanel sidebar = new SidebarPanel();
        sidebar.addSidebarListeners(mainController);
        add(sidebar);

        // Scroll pane to hold the main content
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 0, 1700, 1080);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.decode("#e6f2cf"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel contentPanel = new JPanel(null);
        contentPanel.setPreferredSize(new Dimension(1680, 1450));
        contentPanel.setBackground(Color.decode("#e6f2cf"));

        // Top title
        JLabel titleLabel = new JLabel("Compare Your Pitches");
        titleLabel.setForeground(Color.decode("#3a5600"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setBounds(30, 30, 800, 60);
        contentPanel.add(titleLabel);

        // Selection checkboxes
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Choose pitches to compare:"));
        selectionPanel.setBackground(Color.decode("#f0f8e2"));
        selectionPanel.setBounds(30, 110, 200, 1020);
        checkBoxes = new ArrayList<>();
        for (Pitch p : userPitches) {
            JCheckBox cb = new JCheckBox(p.getTitle());
            cb.setBackground(Color.decode("#f0f8e2"));
            cb.setMargin(new Insets(0, 0, 0, 0));
            cb.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));
            cb.addItemListener(e -> updateComparisonView());
            checkBoxes.add(cb);
            selectionPanel.add(cb);
        }
        contentPanel.add(selectionPanel);

        // Details panel
        detailPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        detailPanel.setBackground(Color.decode("#e6f2cf"));
        detailPanel.setBounds(240, 110, 1100, 200);
        contentPanel.add(detailPanel);

        // Chart panel
        chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBounds(240, 330, 1100, 800);
        chartContainer.setBackground(Color.decode("#e6f2cf"));
        contentPanel.add(chartContainer);

        scrollPane.setViewportView(contentPanel);
        add(scrollPane);
    }

    // Updates the comparison view based on which checkboxes are selected.
    private void updateComparisonView() {
        detailPanel.removeAll();
        chartContainer.removeAll();
        List<Pitch> selected = new ArrayList<>();
        for (JCheckBox cb : checkBoxes) {
            if (cb.isSelected()) {
                Pitch p = getPitchByTitle(cb.getText());
                if (p != null) selected.add(p);
            }
        }
        for (Pitch pitch : selected) {
            String text = "<html><b>Title:</b> " + pitch.getTitle() + "<br>"
                    + "<b>Problem:</b> " + (pitch.getProblem() == null || pitch.getProblem().isEmpty() ? "No problem description" : pitch.getProblem()) + "<br>"
                    + "<b>Solution:</b> " + (pitch.getSolution() == null || pitch.getSolution().isEmpty() ? "No solution description" : pitch.getSolution()) + "</html>";
            JLabel descLabel = new JLabel(text);
            descLabel.setVerticalAlignment(SwingConstants.TOP);
            JScrollPane scrollPane = new JScrollPane(descLabel);
            detailPanel.add(scrollPane);
        }
        if (selected.size() == 2) {
            chartContainer.add(new ChartPanel(createChart(selected.get(0), selected.get(1))), BorderLayout.CENTER);
        }
        revalidate();
        repaint();
    }

    // Creates a bar chart comparing clarity, market fit, and feasibility scores of two pitches.
    private JFreeChart createChart(Pitch p1, Pitch p2) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(p1.getClarityScore(), p1.getTitle(), "Clarity");
        dataset.addValue(p2.getClarityScore(), p2.getTitle(), "Clarity");
        dataset.addValue(p1.getMarketFitScore(), p1.getTitle(), "Market Fit");
        dataset.addValue(p2.getMarketFitScore(), p2.getTitle(), "Market Fit");
        dataset.addValue(p1.getFeasibilityScore(), p1.getTitle(), "Feasibility");
        dataset.addValue(p2.getFeasibilityScore(), p2.getTitle(), "Feasibility");
        return ChartFactory.createBarChart(
                "Pitch Comparison",
                "Category",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    // Retrieves a Pitch object from the user's list based on its title.
    private Pitch getPitchByTitle(String title) {
        for (Pitch p : userPitches) {
            if (p.getTitle().equals(title)) return p;
        }
        return null;
    }
}