import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addToCart")
public class AddToCartServletses extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials (Change these based on your setup)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/oop_project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String color = request.getParameter("color");
        String storage = request.getParameter("storage");
        String warranty = request.getParameter("warranty");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // 1️⃣ Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2️⃣ Connect to Database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 3️⃣ Prepare SQL Query to Insert Data
            String sql = "INSERT INTO cart (product_name, color, storage, warranty, quantity, price) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, productName);
            stmt.setString(2, color);
            stmt.setString(3, storage);
            stmt.setString(4, warranty);
            stmt.setInt(5, quantity);
            stmt.setDouble(6, price);

            // 4️⃣ Execute Query
            int rowsInserted = stmt.executeUpdate();

            // 5️⃣ Send Response
            if (rowsInserted > 0) {
                response.getWriter().write("✅ Item added to cart successfully!");
            } else {
                response.getWriter().write("❌ Failed to add item.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("❌ Error: " + e.getMessage());
        } finally {
            // 6️⃣ Close Resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
