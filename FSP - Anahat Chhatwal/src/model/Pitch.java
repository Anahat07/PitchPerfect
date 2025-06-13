package model;

// Imports
import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

// The Pitch class represents a startup pitch submitted by a user. It stores pitch content, analysis scores, and user metadata.
public class Pitch {
	
	// Pitch content fields
    private String title;
    private String problem;
    private String solution;
    private String market;
    private String businessModel;
    private String competitiveAdvantage;
    private String rawText;

    // Analysis score fields
    private double clarityScore;
    private double marketFitScore;
    private double feasibilityScore;
    private double averageScore;
    private String clarityStars;
    private String feasibilityStars;
    private String marketFitStars;
    private String swotAnalysis;
    private String problemRating;
    private String solutionRating;
    private String marketRating;
    private String revenueRating;
    private String userEmail;

    // Full constructor with all the details.
    public Pitch(String title, String problem, String solution, String market,
                 String businessModel, String competitiveAdvantage, String rawText,
                 double clarityScore, double marketFitScore, double feasibilityScore,
                 double averageScore) {
        this.title = title;
        this.problem = problem;
        this.solution = solution;
        this.market = market;
        this.businessModel = businessModel;
        this.competitiveAdvantage = competitiveAdvantage;
        this.rawText = rawText;
        this.clarityScore = clarityScore;
        this.marketFitScore = marketFitScore;
        this.feasibilityScore = feasibilityScore;
        this.averageScore = averageScore;
    }

    // Minimal constructor used when loading pitches from CSV (where some fields may be missing).
    public Pitch(String title, String rawText, double clarityScore, double feasibilityScore, double marketFitScore, double averageScore, String userEmail) {
        this.title = title;
        this.rawText = rawText;
        this.clarityScore = clarityScore;
        this.feasibilityScore = feasibilityScore;
        this.marketFitScore = marketFitScore;
        this.averageScore = averageScore;
        this.userEmail = userEmail;
    }

    // Saves the current pitch to the given CSV file path. Creates a header if the file does not exist yet.
    public void saveToCSV(String filePath, String userEmail) {
        File file = new File(filePath);
        boolean fileExists = file.exists();
        try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
            if (!fileExists) {
                writer.writeNext(new String[]{
                        "Email", "Title", "Problem", "Solution", "Market", "Business Model",
                        "Competitive Advantage", "Raw Text", "Clarity Score", "Market Fit Score",
                        "Feasibility Score", "Average Score"
                });
            }
            String[] line = new String[]{
                    userEmail,
                    title,
                    problem,
                    solution,
                    market,
                    businessModel,
                    competitiveAdvantage,
                    rawText,
                    String.format("%.2f", clarityScore),
                    String.format("%.2f", marketFitScore),
                    String.format("%.2f", feasibilityScore),
                    String.format("%.2f", averageScore)
            };
            writer.writeNext(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads all pitches for a specific user from a CSV file.
    public static List<Pitch> loadPitchesForUser(String filePath, String userEmail) {
        List<Pitch> pitches = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            csvReader.readNext(); 
            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine.length < 12) continue;
                String email = nextLine[0].trim();
                if (!email.equalsIgnoreCase(userEmail.trim())) continue;
                double clarity = parseDoubleSafe(nextLine[8]);
                double marketFit = parseDoubleSafe(nextLine[9]);
                double feasibility = parseDoubleSafe(nextLine[10]);
                double avgScore = parseDoubleSafe(nextLine[11]);
                String rawText = nextLine[7];
                String problem = extractField(rawText, "Problem:");
                String solution = extractField(rawText, "Solution:");
                pitches.add(new Pitch(
                        nextLine[1],
                        problem,
                        solution,
                        "",
                        "",
                        "",
                        rawText,
                        clarity,
                        marketFit,
                        feasibility,
                        avgScore
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pitches;
    }

    // Extracts a section of text from the pitch raw text using a label like "Problem:".
    private static String extractField(String text, String label) {
        String[] lines = text.split("\n");
        boolean found = false;
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            if (found) {
                if (line.trim().matches("^(Market|Business Model|Competitive Advantage|Problem|Solution):.*")) {
                    break;
                }
                result.append(line.trim()).append(" ");
            }
            if (line.trim().startsWith(label)) {
                found = true;
                result.append(line.trim().substring(label.length()).trim()).append(" ");
            }
        }
        return result.toString().trim();
    }

    // Parses a string to double safely, returning 0.0 if parsing fails.
    private static double parseDoubleSafe(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0.0;
        }
    }

    // Retrieves the last pitch submitted by the user from a CSV file.
    public static Pitch getLastPitchForUser(String csvFilePath, String userEmail) {
        List<Pitch> pitches = loadPitchesForUser(csvFilePath, userEmail);
        return pitches.isEmpty() ? null : pitches.get(pitches.size() - 1);
    }

    // Finds a specific pitch by its title for a given user.
    public static Pitch getPitchByTitle(String filePath, String title, String email) {
        List<Pitch> pitches = loadPitchesForUser(filePath, email);
        for (Pitch p : pitches) {
            if (p.getTitle().equals(title)) return p;
        }
        return null;
    }

    // Deletes a pitch based on title and user email from the CSV file.
    public static void deletePitch(String csvFilePath, String pitchTitleToDelete, String userEmail) throws IOException, CsvValidationException {
        List<PitchRecord> allPitches = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            csvReader.readNext(); // Skip header
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine.length < 9) continue;
                allPitches.add(new PitchRecord(nextLine[0], nextLine[1], nextLine));
            }
        }
        allPitches.removeIf(pr -> pr.email.equalsIgnoreCase(userEmail) && pr.title.equals(pitchTitleToDelete));
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            writer.writeNext(new String[]{
                    "Email", "Title", "Problem", "Solution", "Market", "Business Model",
                    "Competitive Advantage", "Raw Text", "Clarity Score", "Market Fit Score",
                    "Feasibility Score", "Average Score"
            });
            for (PitchRecord pr : allPitches) {
                writer.writeNext(pr.csvLine);
            }
        }
    }

    // Helper class to hold CSV line data when deleting a pitch.
    private static class PitchRecord {
        String email, title;
        String[] csvLine;
        PitchRecord(String email, String title, String[] csvLine) {
            this.email = email;
            this.title = title;
            this.csvLine = csvLine;
        }
    }
    
    // Loads all pitches (regardless of user) from the given CSV file.
    public static List<Pitch> loadAllPitches(String csvPath) {
        List<Pitch> pitches = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (tokens.length >= 8) {
                    String userEmail = tokens[0];
                    String title = tokens[1];
                    String rawText = tokens[3]; // tokens[2] is empty?
                    double clarity = Double.parseDouble(tokens[4]);
                    double feasibility = Double.parseDouble(tokens[5]);
                    double marketFit = Double.parseDouble(tokens[6]);
                    double avg = Double.parseDouble(tokens[7]);
                    Pitch pitch = new Pitch(title, rawText, clarity, feasibility, marketFit, avg, userEmail);
                    pitches.add(pitch);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pitches;
    }

    // Saves a list of pitches to a CSV file, overwriting existing content.
    public static void saveAllPitches(String csvPath, List<Pitch> pitches) throws IOException {
    	try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath))) {
    	    writer.writeNext(new String[]{
    	        "Email", "Title", "Problem", "Solution", "Market", "Business Model",
    	        "Competitive Advantage", "Raw Text", "Clarity Score", "Market Fit Score",
    	        "Feasibility Score", "Average Score"
    	    });
    	    for (Pitch p : pitches) {
    	        String[] line = new String[]{
    	            p.getUserEmail(),
    	            p.getTitle(),
    	            p.getProblem(),
    	            p.getSolution(),
    	            p.getMarket(),
    	            p.getBusinessModel(),
    	            p.getCompetitiveAdvantage(),
    	            p.getRawText(),
    	            String.format("%.2f", p.getClarityScore()),
    	            String.format("%.2f", p.getMarketFitScore()),
    	            String.format("%.2f", p.getFeasibilityScore()),
    	            String.format("%.2f", p.getAverageScore())
    	        };
    	        writer.writeNext(line);
    	    }
    	}
    }

    // Getters
    public String getTitle() {
        return title;
    }
    public String getProblem() {
        return problem;
    }
    public String getSolution() {
        return solution;
    }
    public String getMarket() {
        return market;
    }
    public String getBusinessModel() {
        return businessModel;
    }
    public String getCompetitiveAdvantage() {
        return competitiveAdvantage;
    }
    public String getRawText() {
        return rawText;
    }
    public double getClarityScore() {
        return clarityScore;
    }
    public double getMarketFitScore() {
        return marketFitScore;
    }
    public double getFeasibilityScore() {
        return feasibilityScore;
    }
    public double getAverageScore() {
        return averageScore;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getClarityStars() {
        return clarityStars;
    }
    public String getFeasibilityStars() {
        return feasibilityStars;
    }
    public String getMarketFitStars() {
        return marketFitStars;
    }

    // Setters
    public void setClarityStars(String stars) {
        this.clarityStars = stars;
    }
    public void setFeasibilityStars(String stars) {
        this.feasibilityStars = stars;
    }
    public void setMarketFitStars(String stars) {
        this.marketFitStars = stars;
    }
    public void setSwotAnalysis(String swot) {
        this.swotAnalysis = swot;
    }
    public void setProblemRating(String rating) {
        this.problemRating = rating;
    }
    public void setSolutionRating(String rating) {
        this.solutionRating = rating;
    }
    public void setMarketRating(String rating) {
        this.marketRating = rating;
    }
    public void setRevenueRating(String rating) {
        this.revenueRating = rating;
    }
    public void setClarityScore(double clarityScore) {
        this.clarityScore = clarityScore;
    }
    public void setFeasibilityScore(double feasibilityScore) {
        this.feasibilityScore = feasibilityScore;
    }
    public void setMarketFitScore(double marketFitScore) {
        this.marketFitScore = marketFitScore;
    }
    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setRawText(String rawText) {
        this.rawText = rawText;
    }
    public void setUserEmail(String email) {
        this.userEmail = email;
    }
}