import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
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

public class shippingservlet extends HttpServlet {

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/oop_project";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String country = request.getParameter("country");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String address = request.getParameter("address");
        String apartment = request.getParameter("apartment");
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postal-code");
        String phone = request.getParameter("phone");

        // Database connection and insertion logic
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Step 1: Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Step 2: Prepare SQL query to insert data
            String sql = "INSERT INTO shipping_details (country, first_name, last_name, address, apartment, city, postal_code, phone) "
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            // Step 3: Set values in the prepared statement
            stmt.setString(1, country);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, address);
            stmt.setString(5, apartment);
            stmt.setString(6, city);
            stmt.setString(7, postalCode);
            stmt.setString(8, phone);

            // Step 4: Execute the insert operation
            stmt.executeUpdate();

            // Redirect or forward to a success page
            response.sendRedirect("aftershipping.html");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
