import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/uploadImage")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10, // 10 MB
    maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class ImageUploadServletes extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/oop_project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemId = request.getParameter("itemId"); // Get the item ID from the form
        System.out.println("Item ID: " + itemId); // Debugging
    
        Collection<Part> fileParts = request.getParts(); // Get all parts from the request
        System.out.println("Number of file parts: " + fileParts.size()); // Debugging
    
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Database connection established."); // Debugging
    
            for (Part filePart : fileParts) {
                // Check if the part is an image file (name="images")
                if (filePart.getName().equals("images") && filePart.getSize() > 0) {
                    String fileName = filePart.getSubmittedFileName();
                    System.out.println("Uploading file: " + fileName); // Debugging
    
                    InputStream fileContent = filePart.getInputStream();
    
                    // Save the file to a directory
                    File uploads = new File("C:\\Buying-and-Selling\\Images");
                    if (!uploads.exists()) {
                        uploads.mkdirs(); // Create the directory if it doesn't exist
                    }
                    File file = new File(uploads, fileName);
                    Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    
                    // Insert the file path and item ID into the database
                    String sql = "INSERT INTO image_upload (id, image_path) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, Integer.parseInt(itemId)); // Set the item ID
                        stmt.setString(2, file.getAbsolutePath()); // Set the image path
                        stmt.executeUpdate();
                        System.out.println("File uploaded and database updated: " + fileName); // Debugging
                    }
                }
            }
            response.sendRedirect("next.html");
        } catch (Exception e) {
            e.printStackTrace();
            // Log the error for debugging
            System.err.println("Error during image upload: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error.html"); // Use dynamic path
        }
    }
}