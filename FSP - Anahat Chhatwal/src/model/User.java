package model;

// Represents a user in the PitchPerfect application. Each user has a name, email, and password, and can be serialized to or from a CSV format.
public class User {
	
	// Fields
    private String name;
    private String email;
    private String password;

    // Constructor
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    // Converts the user information into a single CSV-formatted line.
    public String toCSV() {
        return name + "," + email + "," + password;
    }

    // Parses a User object from a line of CSV.
    public static User fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 3) {
            return new User(parts[0].trim(), parts[1].trim(), parts[2].trim());
        }
        return null;
    }
}