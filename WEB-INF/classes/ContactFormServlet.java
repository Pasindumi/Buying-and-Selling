import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ContactFormServlet extends HttpServlet {

         protected void doPost(HttpServletRequest request, HttpServletResponse response)
                           throws ServletException, IOException {
                  // Set character encoding to handle non-ASCII characters
                  request.setCharacterEncoding("UTF-8");

                  // Get form data
                  String name = request.getParameter("name");
                  String email = request.getParameter("email");
                  String message = request.getParameter("message");

                  // Database connection variables
                  String dbURL = "jdbc:mysql://localhost:3306/oop_project"; // Modify this with your database
                                                                            // details
                  String dbUsername = "root"; // Replace with your MySQL username
                  String dbPassword = ""; // Replace with your MySQL password

                  Connection conn = null;
                  PreparedStatement stmt = null;

                  try {
                           // Load MySQL JDBC driver (for older versions)
                           Class.forName("com.mysql.cj.jdbc.Driver");

                           // Establish database connection
                           conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

                           // SQL insert query
                           String sql = "INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)";
                           stmt = conn.prepareStatement(sql);

                           // Set the parameters for the prepared statement
                           stmt.setString(1, name);
                           stmt.setString(2, email);
                           stmt.setString(3, message);

                           // Execute the query
                           int rowsAffected = stmt.executeUpdate();

                           if (rowsAffected > 0) {
                                    response.getWriter().write("Message sent successfully!");
                           } else {
                                    response.getWriter().write("Error in submitting message.");
                           }

                  } catch (Exception e) {
                           e.printStackTrace();
                           response.getWriter().write("Error: " + e.getMessage());
                  } finally {
                           try {
                                    if (stmt != null)
                                             stmt.close();
                                    if (conn != null)
                                             conn.close();
                           } catch (SQLException se) {
                                    se.printStackTrace();
                           }
                  }
         }
}
