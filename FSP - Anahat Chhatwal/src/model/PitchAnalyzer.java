package model;

// Imports
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// The PitchAnalyzer class is responsible for analyzing a startup pitch text by extracting key components (problem, solution, market, revenue),
// rating their quality, and generating an overall score and SWOT analysis.
public class PitchAnalyzer {

	// Keyword arrays for identifying sections of the pitch
    private static final String[] PROBLEM_KEYWORDS = {"problem", "issue", "challenge", "pain point", "gap", "difficulty"};
    private static final String[] SOLUTION_KEYWORDS = {"solution", "approach", "resolve", "address", "method", "strategy"};
    private static final String[] MARKET_KEYWORDS = {"market", "audience", "customer", "segment", "user base", "target"};
    private static final String[] REVENUE_KEYWORDS = {"revenue", "pricing", "business model", "monetize", "income", "profit"};

    // Analyzes a pitch's text and returns a PitchScore object containing component details,
    // ratings, clarity/feasibility/market fit stars, and a SWOT analysis.
    public static PitchScore analyze(String pitchText) {
        PitchScore score = new PitchScore();
        Map<String, String> sections = extractAllSections(pitchText);
        String problem = sections.get("problem");
        String solution = sections.get("solution");
        String market = sections.get("market");
        String revenue = sections.get("revenue");
        score.setProblem(problem);
        score.setSolution(solution);
        score.setMarket(market);
        score.setRevenue(revenue);
        score.setProblemRating(rateComponent(problem, PROBLEM_KEYWORDS));
        score.setSolutionRating(rateComponent(solution, SOLUTION_KEYWORDS));
        score.setMarketRating(rateComponent(market, MARKET_KEYWORDS));
        score.setRevenueRating(rateComponent(revenue, REVENUE_KEYWORDS));
        score.setClarityStars(generateClarityStars(pitchText));
        score.setFeasibilityStars(generateFeasibilityStars(solution, revenue));
        score.setMarketFitStars(generateMarketFitStars(market));
        score.setSwotAnalysis(generateSWOT(problem, solution, market, revenue));
        // Convert visual ratings to numeric scores
        double clarityScore = starsToDouble(score.getClarityStars());
        double feasibilityScore = starsToDouble(score.getFeasibilityStars());
        double marketFitScore = starsToDouble(score.getMarketFitStars());
        double avgScore = Math.round(((clarityScore + feasibilityScore + marketFitScore) / 3.0) * 100.0) / 100.0;
        score.setClarityScore(clarityScore);
        score.setFeasibilityScore(feasibilityScore);
        score.setMarketFitScore(marketFitScore);
        score.setAverageScore(avgScore);
        return score;      
    }

    // Cleans raw section text by removing labels and formatting for consistency.
    private static String cleanAndFormatText(String rawText) {
        if (rawText == null || rawText.trim().isEmpty()) return "Not clearly defined.";
        rawText = rawText.replaceAll("(?i)^name:\\s*\\w+\\s*", "");
        rawText = rawText.replaceAll("(?i)^(problem|solution|market|revenue|business model|competitive advantage)\\s*:\\s*", "");
        rawText = rawText.trim();
        if (rawText.length() > 0) {
            rawText = Character.toUpperCase(rawText.charAt(0)) + rawText.substring(1);
        }
        Pattern pattern = Pattern.compile("([.!?]\\s+)([a-z])");
        Matcher matcher = pattern.matcher(rawText);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1) + Character.toUpperCase(matcher.group(2).charAt(0)));
        }
        matcher.appendTail(sb);
        rawText = sb.toString();
        rawText = rawText.replaceAll("\\.\\.\\.+$", ".");
        rawText = rawText.replaceAll("\\.\\.\\.", ".");
        return wrapText(rawText, 80);
    }

    // Wraps text to a specific max line length for neat formatting.
    private static String wrapText(String text, int maxLineLength) {
        StringBuilder result = new StringBuilder();
        int lineLength = 0;
        for (String word : text.split(" ")) {
            if (lineLength + word.length() > maxLineLength) {
                result.append("\n");
                lineLength = 0;
            }
            result.append(word).append(" ");
            lineLength += word.length() + 1;
        }
        return result.toString().trim();
    }

    // Rates the quality of a pitch section based on keyword presence and length.
    private static String rateComponent(String sectionText, String[] relevantKeywords) {
        if (sectionText.contains("Not clearly defined")) return "3/10";
        int score = 0;
        int length = sectionText.length();
        if (length > 300) score += 3;
        else if (length > 150) score += 2;
        else score += 1;
        int keywordHits = 0;
        for (String keyword : relevantKeywords) {
            if (sectionText.toLowerCase().contains(keyword)) keywordHits++;
        }
        score += Math.min(keywordHits, 3);
        if (sectionText.contains(",") || sectionText.contains(";")) score++;
        return Math.min(score + 3, 10) + "/10";
    }

    // Generates a star rating for clarity based on structure and length.
    private static String generateClarityStars(String text) {
        int score = 0;
        int wordCount = text.trim().split("\\s+").length;
        int sentenceCount = text.split("[.!?]").length;
        if (wordCount > 300) score += 2;
        else if (wordCount > 150) score += 1;
        if (sentenceCount >= 5) score += 1;
        if (text.toLowerCase().contains("problem") &&
            text.toLowerCase().contains("solution") &&
            text.toLowerCase().contains("market") &&
            text.toLowerCase().contains("revenue")) {
            score += 2;
        }
        return starsFromScore(score, 5);
    }

    // Generates a feasibility rating based on solution and revenue detail.
    private static String generateFeasibilityStars(String solution, String revenue) {
        int score = 0;
        if (solution.length() > 200) score += 2;
        else if (solution.length() > 100) score += 1;
        if (revenue.length() > 150) score += 2;
        else if (revenue.length() > 80) score += 1;
        if (revenue.toLowerCase().contains("subscription") ||
            revenue.toLowerCase().contains("ads") ||
            revenue.toLowerCase().contains("partnership") ||
            revenue.toLowerCase().contains("pricing") ||
            revenue.toLowerCase().contains("scalable")) {
            score += 1;
        }
        if (solution.contains("Not clearly defined") || revenue.contains("Not clearly defined")) score = Math.max(score - 2, 0);
        return starsFromScore(score, 5);
    }

    // Generates a market fit rating based on length and keyword density.
    private static String generateMarketFitStars(String market) {
        int score = 0;
        int keywordHits = 0;
        for (String keyword : MARKET_KEYWORDS) {
            if (market.toLowerCase().contains(keyword)) keywordHits++;
        }
        if (keywordHits >= 4) score += 2;
        else if (keywordHits >= 2) score += 1;
        if (market.length() > 200) score += 2;
        else if (market.length() > 100) score += 1;
        if (market.toLowerCase().contains("expansion") ||
            market.toLowerCase().contains("demographic") ||
            market.toLowerCase().contains("growth")) {
            score += 1;
        }
        return starsFromScore(score, 5);
    }
    
    // Converts a numeric score to star representation.
    private static String starsFromScore(int score, int maxStars) {
        if (score >= 5) return "★★★★★";
        if (score == 4) return "★★★★☆";
        if (score == 3) return "★★★☆☆";
        if (score == 2) return "★★☆☆☆";
        return "★☆☆☆☆";
    }

    // Generates a SWOT analysis based on section content.
    private static String[] generateSWOT(String problem, String solution, String market, String revenue) {
        List<String> swot = new ArrayList<>();
        if (!solution.contains("Not clearly defined") && solution.length() > 150) {
            swot.add("Strength: Well-explained and actionable solution.");
        } else {
            swot.add("Strength: Clear motivation and purpose.");
        }
        if (problem.contains("Not clearly defined") || problem.length() < 100) {
            swot.add("Weakness: Problem lacks clear articulation.");
        }
        if (!market.contains("Not clearly defined") && market.length() > 150) {
            swot.add("Opportunity: Strong market targeting potential.");
        } else {
            swot.add("Opportunity: Room to clarify audience segmentation.");
        }
        if (revenue.contains("Not clearly defined") || revenue.length() < 80) {
            swot.add("Threat: Unclear monetization model may limit scalability.");
        } else {
            swot.add("Threat: Risk from market saturation or competition.");
        }
        return swot.toArray(new String[0]);
    }

    // Extracts problem, solution, market, and revenue sections from pitch text.
    private static Map<String, String> extractAllSections(String pitchText) {
        Map<String, String> sectionMap = new HashMap<>();
        sectionMap.put("problem", "Not clearly defined.");
        sectionMap.put("solution", "Not clearly defined.");
        sectionMap.put("market", "Not clearly defined.");
        sectionMap.put("revenue", "Not clearly defined.");
        String[] sentences = pitchText.split("(?<=[.!?])\\s+");
        for (String sentence : sentences) {
            String clean = sentence.trim().toLowerCase();
            if (containsKeyword(clean, PROBLEM_KEYWORDS) && sectionMap.get("problem").equals("Not clearly defined.")) {
                sectionMap.put("problem", cleanAndFormatText(sentence));
            } else if (containsKeyword(clean, SOLUTION_KEYWORDS) && sectionMap.get("solution").equals("Not clearly defined.")) {
                sectionMap.put("solution", cleanAndFormatText(sentence));
            } else if (containsKeyword(clean, MARKET_KEYWORDS) && sectionMap.get("market").equals("Not clearly defined.")) {
                sectionMap.put("market", cleanAndFormatText(sentence));
            } else if (containsKeyword(clean, REVENUE_KEYWORDS) && sectionMap.get("revenue").equals("Not clearly defined.")) {
                sectionMap.put("revenue", cleanAndFormatText(sentence));
            }
        }
        return sectionMap;
    }
    
    // Converts star string (e.g., ★★★☆☆) to numeric double.
    private static double starsToDouble(String stars) {
        if (stars == null) return 0.0;
        if (stars.equals("★★★★★")) return 5.0;
        if (stars.equals("★★★★☆")) return 4.0;
        if (stars.equals("★★★☆☆")) return 3.0;
        if (stars.equals("★★☆☆☆")) return 2.0;
        if (stars.equals("★☆☆☆☆")) return 1.0;
        return 0.0;
    }

    // Utility method to check if text contains any of the specified keywords.
    private static boolean containsKeyword(String text, String[] keywords) {
        text = text.toLowerCase();
        for (String keyword : keywords) {
            if (text.contains(keyword)) return true;
        }
        return false;
    }
}