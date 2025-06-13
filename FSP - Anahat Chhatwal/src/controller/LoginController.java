package controller;

// Imports
import java.awt.*;
import java.io.*;
import javax.swing.*;
import model.*;
import view.*;

// LoginController manages user-related authentication and account operations such as login validation, user registration, password reset, user info updates, 
// and logout functionality.
public class LoginController {
    
    // Stores the user currently logged in
    private User currentUser;

    // Saves a new user's information to the CSV file
    public void saveUserToCSV(User user) {
        try (FileWriter csvWriter = new FileWriter("users.csv", true)) {
            csvWriter.append(user.toCSV()).append("\n"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Validates login credentials by checking them against the CSV file
    public boolean validateLogin(String email, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                User user = User.fromCSV(line);
                if (user != null && user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                    currentUser = user;
                    return true; 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Returns the currently logged-in user
    public User getCurrentUser() {
        return currentUser;
    }

    // Checks if an email already exists in the user database
    public static boolean emailExists(String email) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equalsIgnoreCase(email)) {
                    return true; 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; 
    }

    // Updates a user's password if the email exists
    public static boolean updatePassword(String email, String newPassword) {
        File inputFile = new File("users.csv");
        File tempFile = new File("users_temp.csv");
        boolean updated = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                User user = User.fromCSV(currentLine);
                if (user != null && user.getEmail().equalsIgnoreCase(email)) {
                    user.setPassword(newPassword); 
                    updated = true;
                }
                writer.write(user != null ? user.toCSV() : currentLine); 
                writer.newLine();
            }
            writer.flush();
            // Replace original file if update was successful
            if (updated) {
                inputFile.delete();
                tempFile.renameTo(inputFile);
            } else {
                tempFile.delete(); 
            }
            return updated;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Handles password reset process with validation
    public void handlePasswordReset(LoginPanel loginPanel) {
        // Ask user for their registered email
        String emailInput = JOptionPane.showInputDialog(loginPanel, "Enter your registered email:", "Password Reset",
                JOptionPane.PLAIN_MESSAGE);
        if (emailInput == null || emailInput.trim().isEmpty()) {
        	// User cancelled or entered empty
            return; 
        }
        // Check if email exists in the user database
        if (!emailExists(emailInput.trim())) {
            JOptionPane.showMessageDialog(loginPanel, "Email not found. Please check and try again.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // UI for entering new password and confirmation
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);
        // Process response
        int result = JOptionPane.showConfirmDialog(loginPanel, panel, "Enter new password",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String newPass = new String(newPasswordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());
            // Check password validity
            if (newPass.isEmpty()) {
                JOptionPane.showMessageDialog(loginPanel, "Password cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(loginPanel, "Passwords do not match.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Attempt password update
            boolean success = updatePassword(emailInput.trim(), newPass);
            if (success) {
                JOptionPane.showMessageDialog(loginPanel, "Password updated successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(loginPanel, "Error updating password. Please try again later.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Updates user's name and/or email address based on their current email
    public boolean updateUserInfo(String oldEmail, String newName, String newEmail) {
        File inputFile = new File("users.csv");
        File tempFile = new File("users_temp.csv");
        boolean updated = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                User user = User.fromCSV(currentLine);
                if (user != null && user.getEmail().equalsIgnoreCase(oldEmail)) {
                    user.setName(newName);     
                    user.setEmail(newEmail);  
                    updated = true;
                    if (currentUser != null && currentUser.getEmail().equalsIgnoreCase(oldEmail)) {
                        currentUser.setName(newName);
                        currentUser.setEmail(newEmail);
                    }
                }
                writer.write(user != null ? user.toCSV() : currentLine);
                writer.newLine();
            }
            writer.flush();
            if (updated) {
                inputFile.delete();
                tempFile.renameTo(inputFile); 
            } else {
                tempFile.delete();
            }
            return updated;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Logs out the current user
    public void logout() {
        this.currentUser = null;
    }
}