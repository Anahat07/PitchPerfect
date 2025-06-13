/*
 * Name: Anahat Chhatwal
 * 
 * Date: Friday, June 13, 2025
 * 
 * Course Code: ICS4U1-03
 * Teacher: Mr. Fernandes
 * 
 * Title: PitchPerfect
 * 
 * Description:
 * PitchPerfect is a Java-based startup pitch analyzer designed to evaluate the quality and viability of 
 * entrepreneurial pitches using algorithmic and NLP-based techniques. The application allows users to 
 * create accounts, upload pitches (as text or files), run SWOT-based analysis, view pitch history, 
 * compare performance, and track personalized improvement suggestions. The goal is to assist aspiring 
 * entrepreneurs in enhancing the clarity, feasibility, and competitiveness of their business ideas.
 * 
 * Features:
 * - Home, Login, and Sign Up screens with input validation and error handling
 * - CSV-based user data storage and secure password reset system
 * - Dashboard displaying pitch stats, report summaries, suggestions, and table of all uploaded pitches
 * - Upload pitch manually or via file (PDF/TXT), with format instructions and validation
 * - Analyze screen parses pitch into components: Problem, Solution, Market, and Revenue Model
 * - Ratings generated based on SWOT analysis, clarity, feasibility, and market fit
 * - Suggestions presented as a checkbox-style to-do list for iterative improvements
 * - Export analyzed pitch as a .TXT file
 * - Compare up to 2 pitches using a bar chart and content display
 * - Sidebar and menu bar for seamless navigation across all screens
 * - Profile editing (name/email) and password updating with validation
 * - Avatar selection and preview (pending CSV saving implementation)
 * - Help screen accessible throughout the application
 * 
 * Major Skills:
 *  - Object-Oriented Programming (OOP) principles and design patterns
 * - Java Swing for GUI design and event handling
 * - File I/O operations and CSV parsing using OpenCSV
 * - GUI navigation using controllers and centralized event management
 * - Exception handling and input validation
 * - Simple Natural Language Processing techniques for pitch parsing
 * - Data visualization (bar charts for pitch comparison)
 * - Modular coding and MVC architecture implementation
 * 
 * Areas of Concerns:
 * - Improve user flow in the Help screen by adding "Back" buttons for all entry points
 * - Store avatar selections persistently in the User CSV file
 * - Add 2-Factor Authentication or other enhanced security features for password management
 * - Add transitions (sliding animations) between Login and Sign Up for better UX
 * - Clear login fields after failed login attempts for better security
 * - Improve communication on inactive button actions (e.g., already on Home screen)
 * - Add About section and more contextual help/instructions throughout the app
 * - Optimize compare screen UI for clarity when more than two pitches are selected
 * - Further refine pitch parsing and analysis algorithms for deeper insights
 * 
 * NOTE:
 * Sample startup pitches have been uploaded and included on Google Classroom 
 * for testing purposes. These can be used to try out the upload, analysis, and comparison 
 * features of the PitchPerfect application. Each pitch is formatted to demonstrate 
 * different strengths and weaknesses in clarity, feasibility, market fit, and competitiveness.
 * 
 */

package application;

// Imports
import controller.MainController;

//This class creates an instance of MainController, which starts the application
public class PitchPerfectApplication {
    public static void main(String[] args) {
    	new MainController();
    }
}