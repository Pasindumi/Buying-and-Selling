import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class AddItemServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/oop_project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String itemName = request.getParameter("itemName");
            String price = request.getParameter("price");
            String mainCategory = request.getParameter("mainCategory");
            String subCategory = request.getParameter("subCategory");
            String sellerName = request.getParameter("sellerName");
            String address = request.getParameter("address");
            String contactNumber = request.getParameter("contactNumber");
            String email = request.getParameter("email");

            // Handle file upload
            Part filePart = request.getPart("images");
            if (filePart == null || filePart.getSubmittedFileName().isEmpty()) {
                out.println("<h2>No file uploaded. Please select a file and try again.</h2>");
                return;
            }
            String fileName = filePart.getSubmittedFileName();
            String uploadPath = "C:/uploaded_images/";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            filePart.write(uploadPath + fileName);

            // Save data to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO items (item_name, price, main_category, sub_category, seller_name, address, contact_number, email, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, itemName);
            stmt.setString(2, price);
            stmt.setString(3, mainCategory);
            stmt.setString(4, subCategory);
            stmt.setString(5, sellerName);
            stmt.setString(6, address);
            stmt.setString(7, contactNumber);
            stmt.setString(8, email);
            stmt.setString(9, uploadPath + fileName);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<h2>Item added successfully!</h2>");
            } else {
                out.println("<h2>Error adding item. Please try again.</h2>");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}
