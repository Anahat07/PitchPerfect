package controller;

// Imports
import java.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.Loader;

// PdfController is responsible for loading and reading the content of text-based (.pdf or .txt) files used in the PitchPerfect application. 
public class PdfController {

	// Loads and reads the content of a provided file. Supports .pdf (using PDFBox) and .txt formats.
    public String loadFileContent(File file) throws IOException {
    	// Handles PDF files
        if (file.getName().toLowerCase().endsWith(".pdf")) {
            try (PDDocument document = Loader.loadPDF(file)) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        // Handles TXT files
        } else if (file.getName().toLowerCase().endsWith(".txt")) {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            return content.toString();
        } else {
            throw new IOException("Unsupported file format.");
        }
    }
}