import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.http.HttpServletResponse;

public class CartServletsses extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Database connection
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Set up connection
        String dbURL = "jdbc:mysql://localhost:3306/oop_project?useSSL=false&serverTimezone=UTC";
        String dbUser = "root";  // Replace with your MySQL username
        String dbPass = "";      // Replace with your MySQL password

          double totalAmount = 0;

        try {
            // Explicitly load the MySQL JDBC driver (for legacy support)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            // SQL query to fetch cart items
            String sql = "SELECT * FROM cart";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // HTML structure to display cart items in a table
            while (rs.next()) {
                String productName = rs.getString("product_name");
                String color = rs.getString("color");
                String storage = rs.getString("storage");
                String warranty = rs.getString("warranty");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                String imagePath = rs.getString("imagePath");

                // Calculate total price for the item (price * quantity)
                double totalPrice = price * quantity;

                 totalAmount += totalPrice;

                out.println("<div class='product'>");
                out.println("<img src='" + imagePath + "' alt='" + productName + "' class='product-image'>");
                out.println("<div class='product-details'>");
                out.println("<h3>" + productName + "</h3>");
                out.println("<div class='product-info'><span>" + color + "</span></div>");
                out.println("<div class='product-info'><span>" + storage + "</span></div>");
                out.println("<div class='product-info'><span>" + warranty + "</span></div>");
                out.println("<div class='product-info'><span>Quantity: " + quantity + "</span></div>");
                out.println("<div class='product-price'>Rs " + price + "</div>");
                out.println("<div class='product-total'>Total: Rs " + totalPrice + "</div>");
                out.println("</div>");
                out.println("</div>");
            }
            out.println("<div class='final-total'>");
            out.println("<h3>Total: Rs " + totalAmount + "</h3>");
            out.println("</div>");
            // Close the database connections
            rs.close();
            ps.close();
            conn.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<h3>Error connecting to database: " + e.getMessage() + "</h3>");
        } finally {
            out.close();
        }
    }
}
