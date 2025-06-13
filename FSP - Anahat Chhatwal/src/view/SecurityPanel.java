package view;

// Imports
import javax.swing.*;
import controller.*;
import java.awt.*;

// SecurityPanel is a custom JPanel in the PitchPerfect app that allows users to update their password.
public class SecurityPanel extends JPanel {
	
	// Instance
    MainController mainController;
	
	// GUI Components
    private JPasswordField currentPasswordField, newPasswordField;
    private JPanel rightSidebar;
    private JButton toggleButton;
    private boolean isCollapsed = false;

    // Constructor
    public SecurityPanel(MainController mainController) {
    	this.mainController = mainController;
        
    	// Layout
    	setLayout(null);
        setPreferredSize(new Dimension(1920, 1080));
        setBackground(new Color(230, 242, 207));

        // Sidebar for navigation
        SidebarPanel sidebar = new SidebarPanel();
        sidebar.addSidebarListeners(mainController);
        add(sidebar);

        // 2nd sidebar for edit profile only navigation
        rightSidebar = new JPanel();
        rightSidebar.setBackground(Color.decode("#64823c"));
        rightSidebar.setLayout(null);
        rightSidebar.setBounds(100, 0, 200, 1080);
        add(rightSidebar);
        String[] sidebarOptions = {"Edit Profile", "Security", "Help"};
        String[] rightCommands = {"Right Edit Profile", "Right Security", "Right Help"};
        int y = 100;
        for (int i = 0; i < sidebarOptions.length; i++) {
            JButton btn = new RoundedButton(sidebarOptions[i]);
            btn.setFont(new Font("SansSerif", Font.ITALIC, 16));
            btn.setBorder(BorderFactory.createLineBorder(Color.decode("#e6f2cf"), 2));
            btn.setBounds(20, y, 160, 40);
            btn.setActionCommand(rightCommands[i]);
            btn.addActionListener(mainController);
            rightSidebar.add(btn);
            y += 60;
        }

        // Toggle button (collapses 2nd sidebar)
        toggleButton = new JButton("<<");
        toggleButton.setContentAreaFilled(false);
        toggleButton.setOpaque(true);   
        toggleButton.setBackground(Color.decode("#64823c"));
        toggleButton.setForeground(Color.decode("#e6f2cf"));
        toggleButton.setBorder(BorderFactory.createLineBorder(Color.decode("#64823c"), 2));
        toggleButton.setBounds(300, 20, 30, 30);
        toggleButton.addActionListener(e -> toggleSidebar());
        add(toggleButton);

        // Main content
        int leftOffset = 500;

        // Title label
        JLabel titleLabel = new JLabel("Security");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.decode("#3a5600"));
        titleLabel.setBounds(leftOffset, 100, 300, 50);
        add(titleLabel);

        // Current password input
        createLabel("Current Password", leftOffset, 180);
        currentPasswordField = createPasswordField(leftOffset, 220);

        // New password input
        createLabel("New Password", leftOffset, 300);
        newPasswordField = createPasswordField(leftOffset, 340);

        // Cancel button
        JButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setBounds(leftOffset, 470, 120, 50);
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.decode("#64823c"), 2));
        cancelButton.setActionCommand("Security Cancel");
        cancelButton.addActionListener(mainController);
        add(cancelButton);

        // Save button
        JButton saveButton = new RoundedButton("Save");
        saveButton.setBounds(leftOffset + 150, 470, 120, 50);
        saveButton.setBorder(BorderFactory.createLineBorder(Color.decode("#64823c"), 2));
        saveButton.setActionCommand("Security Save");
        saveButton.addActionListener(mainController);
        add(saveButton);
    }

    // Toggles the visibility of the 2nd sidebar and shifts the content layout accordingly.
    private void toggleSidebar() {
        isCollapsed = !isCollapsed;
        rightSidebar.setVisible(!isCollapsed);
        toggleButton.setText(isCollapsed ? ">>" : "<<");
        toggleButton.setBounds(isCollapsed ? 150 : 350, 20, 30, 30);
        int shift = isCollapsed ? -200 : 200;
        for (Component comp : getComponents()) {
            if (comp != rightSidebar && comp != toggleButton && !(comp instanceof SidebarPanel)) {
                comp.setBounds(comp.getX() + shift, comp.getY(), comp.getWidth(), comp.getHeight());
            }
        }
        revalidate();
        repaint();
    }

    // Utility method to create and add a label to the panel.
    private void createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(Color.decode("#3a5600"));
        label.setBounds(x, y, 300, 30);
        add(label);
    }

    // Helper method to create a styled password field.
    private JPasswordField createPasswordField(int x, int y) {
        JPasswordField field = new JPasswordField();
        field.setBounds(x, y, 400, 50);
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createLineBorder(new Color(45, 75, 15), 2));
        return (JPasswordField) add(field);
    }
    
    // Clears both password input fields.
    public void clearPasswordFields() {
        currentPasswordField.setText("");
        newPasswordField.setText("");
    }
    
    // Getters & setters
    public String getCurrentPassword() {
        return new String(currentPasswordField.getPassword());
    }
    public String getNewPassword() {
        return new String(newPasswordField.getPassword());
    }  
}