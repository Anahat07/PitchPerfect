package model;

// The PitchScore class represents the results of analyzing a startup pitch. It stores extracted components from the pitch (problem, solution, market, revenue),
// ratings for each component, star-based evaluations (clarity, feasibility, market fit), numerical scores, an average score, and a SWOT analysis.
public class PitchScore {

	// Extracted textual components of the pitch
	private String problem, solution, market, revenue;
	// Rating strings for each pitch component (e.g., "8/10")
	private String problemRating, solutionRating, marketRating, revenueRating;
	// SWOT analysis statements for the pitch
	private String[] swotAnalysis;
	// Star-based visual ratings for clarity, feasibility, and market fit (e.g.,
	// "★★★★☆")
	private String clarityStars, feasibilityStars, marketFitStars;
	// Numeric scores associated with each of the star ratings
	private double clarityScore;
	private double feasibilityScore;
	private double marketFitScore;
	private double averageScore;

	// Score Getters & Setters
	public void setClarityScore(double score) {
		this.clarityScore = score;
	}
	public void setFeasibilityScore(double score) {
		this.feasibilityScore = score;
	}
	public void setMarketFitScore(double score) {
		this.marketFitScore = score;
	}
	public void setAverageScore(double score) {
		this.averageScore = score;
	}
	public double getClarityScore() {
		return clarityScore;
	}
	public double getFeasibilityScore() {
		return feasibilityScore;
	}
	public double getMarketFitScore() {
		return marketFitScore;
	}
	public double getAverageScore() {
		return averageScore;
	}

	// Pitch Component Getters & Setters
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getRevenue() {
		return revenue;
	}
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	// Rating Getters & Setters
	public String getProblemRating() {
		return problemRating;
	}
	public void setProblemRating(String rating) {
		this.problemRating = rating;
	}
	public String getSolutionRating() {
		return solutionRating;
	}
	public void setSolutionRating(String rating) {
		this.solutionRating = rating;
	}
	public String getMarketRating() {
		return marketRating;
	}
	public void setMarketRating(String rating) {
		this.marketRating = rating;
	}
	public String getRevenueRating() {
		return revenueRating;
	}
	public void setRevenueRating(String rating) {
		this.revenueRating = rating;
	}

	// SWOT Analysis Getters & Setters
	public String[] getSwotAnalysis() {
		return swotAnalysis;
	}
	public void setSwotAnalysis(String[] swotAnalysis) {
		this.swotAnalysis = swotAnalysis;
	}

	// Star Rating Getters & Setters
	public String getClarityStars() {
		return clarityStars;
	}
	public void setClarityStars(String stars) {
		this.clarityStars = stars;
	}
	public String getFeasibilityStars() {
		return feasibilityStars;
	}
	public void setFeasibilityStars(String stars) {
		this.feasibilityStars = stars;
	}
	public String getMarketFitStars() {
		return marketFitStars;
	}
	public void setMarketFitStars(String stars) {
		this.marketFitStars = stars;
	}

	// toString Method - returns a formatted string summary of the PitchScore object.
	@Override
	public String toString() {
		return "PitchScore {" + "problem='" + problem + '\'' + ", solution='" + solution + '\'' + ", market='" + market
				+ '\'' + ", revenue='" + revenue + '\'' + ", problemRating='" + problemRating + '\''
				+ ", solutionRating='" + solutionRating + '\'' + ", marketRating='" + marketRating + '\''
				+ ", revenueRating='" + revenueRating + '\'' + ", clarityStars='" + clarityStars + '\''
				+ ", feasibilityStars='" + feasibilityStars + '\'' + ", marketFitStars='" + marketFitStars + '\''
				+ ", swotAnalysis=" + (swotAnalysis != null ? String.join(", ", swotAnalysis) : "null")
				+ ", averageScore=" + getAverageScore() + '}';
	}
}