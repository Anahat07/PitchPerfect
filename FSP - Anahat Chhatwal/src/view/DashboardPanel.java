package view;

// Imports
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import com.opencsv.exceptions.CsvValidationException;
import controller.*;
import model.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

// DashboardPanel is the main user dashboard screen for the PitchPerfect application - it displays all user information.
public class DashboardPanel extends JPanel {
	
	// Instances
	MainController mainController;
	LoginController loginController;

	// GUI Components
	private JLabel welcomeLabel;
	private String currentUserEmail;
	private JTable pitchTable;
	private JPanel mainPanel;
	private JLabel avgScore;
	private JLabel totalPitches;
	private JPanel suggestionsPanel;

	// Constructor
    public DashboardPanel(MainController mainController, LoginController loginController) {
    	this.mainController = mainController;
    	this.loginController = loginController;

    	// Layout
        setLayout(null);
        setSize(1920, 1080);

        // Sidebar for navigation
        SidebarPanel sidebar = new SidebarPanel();
        sidebar.addSidebarListeners(mainController);
        add(sidebar);

        // Main content panel
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode("#e6f2cf"));
        mainPanel.setLayout(null);
        mainPanel.setBounds(100, 0, 1720, 1080);

        // Welcome label - customized with user name
        welcomeLabel = new JLabel("Welcome to your PitchPerfect Dashboard!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.decode("#4d5d32"));
        welcomeLabel.setBounds(50, 20, 1000, 40);
        mainPanel.add(welcomeLabel);

        // New Pitch button
        RoundedButton newPitchButton = new RoundedButton("+ New Pitch");
        newPitchButton.setBounds(50, 100, 300, 200);
        newPitchButton.setFont(new Font("Arial", Font.BOLD, 30));
        newPitchButton.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
        newPitchButton.addActionListener(mainController);
        newPitchButton.setActionCommand("New Pitch");
        mainPanel.add(newPitchButton);

        // Panel showing total pitches and last pitch score
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBounds(400, 100, 300, 200);
        summaryPanel.setBackground(Color.decode("#3a5600"));
        summaryPanel.setLayout(new GridLayout(3, 1));
        JLabel summaryTitle = new JLabel("Summary Report", SwingConstants.CENTER);
        summaryTitle.setForeground(Color.decode("#e6f2cf"));
        summaryTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        avgScore = new JLabel("<html><center>Average Score<br>—</center></html>", SwingConstants.CENTER);
        avgScore.setFont(new Font("Arial", Font.PLAIN, 16));
        avgScore.setForeground(Color.decode("#e6f2cf"));
        totalPitches = new JLabel("<html><center>Total Pitches<br>—</center></html>", SwingConstants.CENTER);
        totalPitches.setFont(new Font("Arial", Font.PLAIN, 16));
        totalPitches.setForeground(Color.decode("#e6f2cf"));
        summaryPanel.add(summaryTitle);
        summaryPanel.add(avgScore);
        summaryPanel.add(totalPitches);
        mainPanel.add(summaryPanel);
        
        // Suggestions panel
        suggestionsPanel = new JPanel();
        suggestionsPanel.setLayout(new BoxLayout(suggestionsPanel, BoxLayout.Y_AXIS));
        suggestionsPanel.setBackground(Color.decode("#3a5600"));
        suggestionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Scroll pane to wrap the suggestions for vertical scrolling
        JScrollPane suggestionsScrollPane = new JScrollPane(suggestionsPanel);
        suggestionsScrollPane.setBounds(750, 100, 400, 200);
        suggestionsScrollPane.setBorder(null);
        suggestionsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        suggestionsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        suggestionsScrollPane.getViewport().setBackground(Color.decode("#3a5600"));
        suggestionsScrollPane.setBackground(Color.decode("#3a5600"));
        mainPanel.add(suggestionsScrollPane);

        // Label for table section
        JLabel myPitchesLabel = new JLabel("My Pitches:");
        myPitchesLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        myPitchesLabel.setBounds(50, 330, 300, 30);
        myPitchesLabel.setForeground(Color.decode("#3a5600"));
        mainPanel.add(myPitchesLabel);

        // Table column headers and initial empty data
        String[] columnNames = {"Title", "Date", "Score", "Options"};
        Object[][] emptyData = {};
        // Pitch table displaying past pitches
        pitchTable = new JTable(new DashboardTableModel(emptyData, columnNames));
        pitchTable.setRowHeight(40);
        pitchTable.setBackground(Color.decode("#3a5600")); 
        pitchTable.setForeground(Color.decode("#e6f2cf")); 
        pitchTable.setFont(new Font("SansSerif", Font.PLAIN, 16)); 
        pitchTable.setGridColor(Color.decode("#a5c48f"));
        pitchTable.getTableHeader().setBackground(Color.decode("#4d5d32")); 
        pitchTable.getTableHeader().setForeground(Color.decode("#e6f2cf"));
        pitchTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        pitchTable.setShowHorizontalLines(false);
        pitchTable.setShowVerticalLines(false);
        pitchTable.setIntercellSpacing(new Dimension(0, 0)); 
        // Scroll pane to allow pitch table scrolling
        JScrollPane scrollPane = new JScrollPane(pitchTable);
        scrollPane.setBounds(50, 370, 1200, 300);
        scrollPane.getViewport().setBackground(Color.decode("#3a5600"));
        scrollPane.setBackground(Color.decode("#3a5600"));
        mainPanel.add(scrollPane);
        
        mainPanel.setBounds(100, 0, 1720, 1080);
        add(mainPanel);
        setVisible(true);
    }

    // Sets a personalized greeting using the user's name.
    public void setWelcomeMessage(String name) {
        welcomeLabel.setText("Welcome to your PitchPerfect Dashboard, " + name + "!");
    }

    // Sets the current user's email and refreshes their data.
    public void setUserEmail(String email) {
        this.currentUserEmail = email;
        refreshPitchTable();
        refreshSummaryAndSuggestions();
    }

    // Loads and displays all pitch data for the current user in the table.
    public void refreshPitchTable() {
        if (currentUserEmail == null) return;
        String[] columnNames = {"Title", "Date", "Score", "Options"};
        List<Pitch> userPitches = Pitch.loadPitchesForUser("pitches.csv", currentUserEmail);
        Object[][] data = new Object[userPitches.size()][4];
        for (int i = 0; i < userPitches.size(); i++) {
            Pitch p = userPitches.get(i);
            data[i][0] = p.getTitle();
            data[i][1] = LocalDate.now().toString();
            double scoreValue = p.getAverageScore();
            data[i][2] = scoreValue == -1 ? "—" : String.format("%.1f", scoreValue);
            data[i][3] = createButtonPanel();
        }
        DashboardTableModel model = new DashboardTableModel(data, columnNames);
        pitchTable.setModel(model);
        pitchTable.getColumn("Options").setCellRenderer(new PanelCellRenderer());
        pitchTable.getColumn("Options").setCellEditor(new PanelCellEditor());
    }

    // Updates the summary labels and suggestions panel with data from the latest analyzed pitch.
    public void refreshSummaryAndSuggestions() {
        if (currentUserEmail == null) return;
        List<Pitch> userPitches = Pitch.loadPitchesForUser("pitches.csv", currentUserEmail);
        totalPitches.setText("<html><center>Total Pitches<br>" + userPitches.size() + "</center></html>");
        Pitch lastPitch = Pitch.getLastPitchForUser("pitches.csv", currentUserEmail);
        if (lastPitch != null) {
            double score = lastPitch.getAverageScore();
            avgScore.setText("<html><center>Last Pitch Score<br>" + String.format("%.1f", score) + "/10</center></html>");
            PitchScore pitchScore = PitchAnalyzer.analyze(lastPitch.getRawText());
            suggestionsPanel.removeAll();
            JLabel suggestionsTitle = new JLabel("Suggestions");
            suggestionsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            suggestionsTitle.setForeground(Color.decode("#e6f2cf"));
            suggestionsTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
            suggestionsPanel.add(suggestionsTitle);
            if (pitchScore.getSwotAnalysis() != null) {
            	for (String suggestion : pitchScore.getSwotAnalysis()) {
            	    JCheckBox cb = new JCheckBox("<html>" + suggestion + "</html>");
            	    cb.setOpaque(false);
            	    cb.setForeground(Color.decode("#e6f2cf"));
            	    cb.setFont(new Font("SansSerif", Font.PLAIN, 14));
            	    cb.setAlignmentX(Component.LEFT_ALIGNMENT);
            	    cb.setMaximumSize(new Dimension(380, Integer.MAX_VALUE));
            	    suggestionsPanel.add(Box.createVerticalStrut(5));
            	    suggestionsPanel.add(cb);
            	}
            } else {
                JLabel noSuggestions = new JLabel("No suggestions available");
                noSuggestions.setForeground(Color.decode("#e6f2cf"));
                noSuggestions.setAlignmentX(Component.CENTER_ALIGNMENT);
                suggestionsPanel.add(noSuggestions);
            }
            suggestionsPanel.revalidate();
            suggestionsPanel.repaint();
        } else {
            avgScore.setText("<html><center>Last Pitch Score<br>—</center></html>");
            suggestionsPanel.removeAll();
            JLabel suggestionsTitle = new JLabel("Suggestions", SwingConstants.CENTER);
            suggestionsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            suggestionsTitle.setForeground(Color.decode("#e6f2cf"));
            suggestionsTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
            suggestionsPanel.add(suggestionsTitle);
            JLabel noSuggestions = new JLabel("No suggestions available");
            noSuggestions.setForeground(Color.decode("#e6f2cf"));
            noSuggestions.setAlignmentX(Component.CENTER_ALIGNMENT);
            suggestionsPanel.add(noSuggestions);
            suggestionsPanel.revalidate();
            suggestionsPanel.repaint();
        }
    }

    // Creates a JPanel containing "View", "Export", and "Delete" buttons for each pitch row
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.decode("#3a5600"));
        JButton viewBtn = new JButton("View");
        JButton exportBtn = new JButton("Export");
        JButton deleteBtn = new JButton("Delete");
        panel.add(viewBtn);
        panel.add(exportBtn);
        panel.add(deleteBtn);
        return panel;
    }
    
    // Renders a JPanel in a table cell, used to display button panels inside table
    class PanelCellRenderer implements javax.swing.table.TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JPanel) {
                ((JPanel) value).setBackground(Color.decode("#3a5600"));
            }
            return (JPanel) value;
        }
    }
    
    // Editor for cells that contain buttons, enabling button interaction inside the table
    class PanelCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        public PanelCellEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(Color.decode("#3a5600"));
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            if (value instanceof JPanel) {
                JPanel originalPanel = (JPanel) value;
                panel.removeAll();
                for (Component c : originalPanel.getComponents()) {
                    if (c instanceof JButton) {
                        JButton originalButton = (JButton) c;
                        JButton clonedButton = new JButton(originalButton.getText());
                        clonedButton.addActionListener(e -> {
                            String action = clonedButton.getText();
                            String pitchTitle = (String) table.getValueAt(row, 0);
                            switch (action) {
                            case "View":
                            	// Load full pitch from CSV and display in scrollable text area
                                Pitch fullPitch = Pitch.getPitchByTitle("pitches.csv", pitchTitle, currentUserEmail);
                                if (fullPitch != null) {
                                    JTextArea textArea = new JTextArea(fullPitch.getRawText());
                                    textArea.setLineWrap(true);
                                    textArea.setWrapStyleWord(true);
                                    textArea.setEditable(false);
                                    textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
                                    JScrollPane scrollPane = new JScrollPane(textArea);
                                    scrollPane.setPreferredSize(new Dimension(600, 400));
                                    JOptionPane.showMessageDialog(DashboardPanel.this, scrollPane, 
                                        "Pitch: " + fullPitch.getTitle(), JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(DashboardPanel.this, 
                                        "Could not find the full pitch content.", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Export":
                            	// Load pitch from file and re-analyze
                                Pitch pitchToExport = Pitch.getPitchByTitle("pitches.csv", pitchTitle, currentUserEmail);
                                if (pitchToExport == null) {
                                    JOptionPane.showMessageDialog(null, "Pitch not found for export.");
                                    return;
                                }
                                PitchScore score = PitchAnalyzer.analyze(pitchToExport.getRawText());
                                if (score == null) {
                                    JOptionPane.showMessageDialog(null, "Pitch analysis failed or unavailable.");
                                    return;
                                }
                                // Construct export content string
                                StringBuilder sb = new StringBuilder();
                                sb.append(pitchToExport.getTitle()).append("\n\n");
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
                                if (score.getSwotAnalysis() != null) {
                                    for (String suggestion : score.getSwotAnalysis()) {
                                        sb.append("→ ").append(suggestion).append("\n");
                                    }
                                } else {
                                    sb.append("None\n");
                                }
                                // File chooser to export analysis as text
                                JFileChooser fileChooser = new JFileChooser();
                                fileChooser.setSelectedFile(new java.io.File("PitchAnalysis.txt"));
                                int result = fileChooser.showSaveDialog(null);
                                if (result == JFileChooser.APPROVE_OPTION) {
                                    try {
                                        java.io.FileWriter writer = new java.io.FileWriter(fileChooser.getSelectedFile());
                                        writer.write(sb.toString());
                                        writer.close();
                                        JOptionPane.showMessageDialog(null, "Analysis exported successfully!");
                                    } catch (IOException error) {
                                        JOptionPane.showMessageDialog(null, "Error writing file: " + error.getMessage());
                                    }
                                }
                                break;
                                case "Delete":
                                	// Prompt confirmation to delete the pitch
                                    int confirm = JOptionPane.showConfirmDialog(DashboardPanel.this,
                                            "Are you sure you want to delete pitch: " + pitchTitle + "?",
                                            "Confirm Delete", JOptionPane.YES_NO_OPTION);
                                    if (confirm == JOptionPane.YES_OPTION) {
                                        try {
											Pitch.deletePitch("pitches.csv", pitchTitle, currentUserEmail);
										} catch (IOException e1) {
											e1.printStackTrace();
										} catch (CsvValidationException e1) {
											e1.printStackTrace();
										}
                                        refreshPitchTable(); 
                                        refreshSummaryAndSuggestions();
                                    }
                                    break;
                            }
                            stopCellEditing(); 
                        });
                        panel.add(clonedButton);
                    }
                }
            }
            return panel;
        }
        @Override
        public Object getCellEditorValue() {
            return panel;
        }
    }
	
    // Ensures custom renderer is used once panel is added to GUI
	@Override
	public void addNotify() {
	    super.addNotify();
	    pitchTable.setDefaultRenderer(Object.class, new DashboardCellRenderer());
	}
	
	// Table cell renderer that matches dashboard colour theme
	class DashboardCellRenderer extends DefaultTableCellRenderer {
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        c.setBackground(Color.decode("#3a5600"));
	        c.setForeground(Color.decode("#e6f2cf"));
	        if (isSelected) {
	            c.setBackground(Color.decode("#5a7d3b")); 
	        }
	        return c;
	    }
	}

    // Custom TableModel for the dashboard table with editable button column
    class DashboardTableModel extends javax.swing.table.AbstractTableModel {
        private Object[][] data;
        private String[] columnNames;
        public DashboardTableModel(Object[][] data, String[] columnNames) {
            this.data = data;
            this.columnNames = columnNames;
        }
        public int getColumnCount() {
            return columnNames.length;
        }
        public int getRowCount() {
            return data.length;
        }
        public String getColumnName(int col) {
            return columnNames[col];
        }
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        public boolean isCellEditable(int row, int col) {
            return col == 3;
        }
        public Class<?> getColumnClass(int col) {
            if (data.length == 0) return Object.class;
            return getValueAt(0, col).getClass();
        }
    }
}