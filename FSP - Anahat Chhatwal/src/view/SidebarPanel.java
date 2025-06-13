package view;

// Imports
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// SidebarPanel is a vertically-stacked sidebar used in the PitchPerfect app.
public class SidebarPanel extends JPanel {
	
	// List to store all sidebar buttons for later reference or event binding
    private List<JButton> sidebarButtons = new ArrayList<>();

    // Constructor
    public SidebarPanel() {
        
    	// Layout
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.decode("#3a5600"));
        setBounds(0, 0, 100, 1080);

        // Paths to sidebar button icons
        String[] iconPaths = {
            "images/homeIcon.png",
            "images/uploadIcon.png",
            "images/analyzeIcon.png",
            "images/compareIcon.png",
            "images/helpIcon.png",
            "images/settingsIcon.png"
        };
        String[] tooltips = {"Home", "Upload", "Analyze", "Compare", "Help", "Settings"};
        String[] commands = {"Sidebar Home", "Sidebar Upload", "Sidebar Analyze", "Sidebar Compare", "Help", "Sidebar Settings"};
        // Loop through each icon/tool/command combo to build buttons
        for (int i = 0; i < iconPaths.length; i++) {
            ImageIcon icon = new ImageIcon(iconPaths[i]);
            JButton button = new JButton(icon);
            button.setToolTipText(tooltips[i]);
            button.setActionCommand(commands[i]);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(70, 70));
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            add(Box.createRigidArea(new Dimension(0, 50)));
            add(button);
            sidebarButtons.add(button);
        }
    }

    // Returns the list of sidebar buttons.
    public List<JButton> getSidebarButtons() {
        return sidebarButtons;
    }

    // Registers a single ActionListener to all sidebar buttons.
    public void addSidebarListeners(java.awt.event.ActionListener listener) {
        for (JButton button : sidebarButtons) {
            button.addActionListener(listener);
        }
    }
}