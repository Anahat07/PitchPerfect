package controller;

// Imports
import model.*;
import java.io.IOException;
import java.util.List;

// The PitchController class serves as a bridge between the user interface and the data model for managing pitch-related operations.
public class PitchController {
	
	// File path to the CSV storing all pitch data
    private String csvPath;

    // Constructor that initializes the controller with the path to the pitch CSV file.
    public PitchController(String csvPath) {
        this.csvPath = csvPath;
    }

    // Loads all pitches associated with a specific user (based on email).
    public List<Pitch> getPitchesForUser(String email) {
        return Pitch.loadPitchesForUser(csvPath, email);
    }
    
    // Loads all pitches from the CSV file, regardless of the user.
    public List<Pitch> getAllPitches() {
        return Pitch.loadAllPitches(csvPath);
    }
    
    // Saves the provided list of Pitch objects to the CSV file, overwriting the existing content.
    public void saveAllPitches(List<Pitch> pitches) throws IOException {
        Pitch.saveAllPitches(csvPath, pitches);
    }
}