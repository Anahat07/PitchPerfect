package view;

// Imports
import javax.swing.*;
import controller.*;
import java.awt.*;
import java.io.File;

// EditProfilePanel displays the user interface for editing profile details.
public class EditProfilePanel extends JPanel {
	
	// Instance
    MainController mainController;
	
	// GUI Components
    private JTextField nameField, emailField;
    private JLabel avatarLabel;
    private JPanel rightSidebar;
    private JButton toggleButton;
    private boolean isCollapsed = false;

    // Constructor
    public EditProfilePanel(MainController mainController) {
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
        JLabel titleLabel = new JLabel("Edit Profile");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.decode("#3a5600"));
        titleLabel.setBounds(leftOffset, 150, 300, 50);
        add(titleLabel);

        // Name input
        createLabel("Name", leftOffset, 230);
        nameField = createTextField(leftOffset, 270);

        // Emial input
        createLabel("Email", leftOffset, 350);
        emailField = createTextField(leftOffset, 390);

        // Avatar preview label
        avatarLabel = new JLabel("Choose Your Avatar", SwingConstants.CENTER);
        avatarLabel.setBounds(1100, 150, 300, 300);
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(new Color(190, 220, 150));
        avatarLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        avatarLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(avatarLabel);

        // Avatar selection button
        JButton chooseAvatarButton = new JButton("Choose Your Avatar");
        chooseAvatarButton.setBounds(1150, 470, 200, 40);
        chooseAvatarButton.addActionListener(e -> chooseAvatar());
        add(chooseAvatarButton);

        // Cancel button
        JButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setBounds(leftOffset, 470, 120, 50);
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.decode("#64823c"), 2));
        cancelButton.setActionCommand("Edit Cancel");
        cancelButton.addActionListener(mainController);
        add(cancelButton);

        // Save button
        JButton saveButton = new RoundedButton("Save");
        saveButton.setBounds(leftOffset + 150, 470, 120, 50);
        saveButton.setBorder(BorderFactory.createLineBorder(Color.decode("#64823c"), 2));
        saveButton.setActionCommand("Edit Save");
        saveButton.addActionListener(mainController);
        add(saveButton);
    }

    // Getters & setters
    public JTextField getNameField() {
		return nameField;
	}
	public JTextField getEmailField() {
		return emailField;
	}

	// Toggles the visibility of the 2nd sidebar and shifts the content layout accordingly.
	private void toggleSidebar() {
        isCollapsed = !isCollapsed;
        rightSidebar.setVisible(!isCollapsed);
        toggleButton.setText(isCollapsed ? ">>" : "<<");
        toggleButton.setBounds(isCollapsed ? 100 : 300, 20, 30, 30); 
        int shift = isCollapsed ? -200 : 200;
        for (Component comp : getComponents()) {
            if (comp != rightSidebar && comp != toggleButton && !(comp instanceof SidebarPanel)) {
                comp.setBounds(comp.getX() + shift, comp.getY(), comp.getWidth(), comp.getHeight());
            }
        }
        revalidate();
        repaint();
    }

	// Opens a file chooser to allow the user to select an avatar image and displays the selected image in the avatarLabel.
    private void chooseAvatar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Avatar");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selected = fileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(new ImageIcon(selected.getAbsolutePath()).getImage()
                    .getScaledInstance(300, 300, Image.SCALE_SMOOTH));
            avatarLabel.setText("");
            avatarLabel.setIcon(icon);
        }
    }

    // Utility method to create and add a label to the panel.
    private void createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(Color.decode("#3a5600"));
        label.setBounds(x, y, 200, 30);
        add(label);
    }

    // Utility method to create and add a styled text field to the panel.
    private JTextField createTextField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 350, 40);
        add(field);
        return field;
    }
}