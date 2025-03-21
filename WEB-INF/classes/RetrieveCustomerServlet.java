import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RetrieveCustomerServlet")
public class RetrieveCustomerServlet extends HttpServlet {
         private static final long serialVersionUID = 1L;

         protected void doPost(HttpServletRequest request, HttpServletResponse response)
                           throws ServletException, IOException {
                  response.setContentType("text/html");
                  PrintWriter out = response.getWriter();

                  // Retrieve input values
                  String idStr = request.getParameter("id");
                  String email = request.getParameter("email");

                  if (idStr == null || email == null || idStr.isEmpty() || email.isEmpty()) {
                           out.println("<h3 style='color:red;'>Invalid Input</h3>");
                           return;
                  }

                  int id = Integer.parseInt(idStr);

                  // Database connection details
                  String url = "jdbc:mysql://localhost:3306/oop_project";
                  String user = "root";
                  String password = "";

                  try {
                           // Load JDBC driver
                           Class.forName("com.mysql.cj.jdbc.Driver");
                           Connection conn = DriverManager.getConnection(url, user, password);

                           // SQL query
                           String sql = "SELECT * FROM users WHERE id = ? AND email = ?";
                           PreparedStatement stmt = conn.prepareStatement(sql);
                           stmt.setInt(1, id);
                           stmt.setString(2, email);

                           ResultSet rs = stmt.executeQuery();

                           if (rs.next()) {
                                    out.println("<h2>Customer Details:</h2>");
                                    out.println("<p><strong>ID:</strong> " + rs.getInt("id") + "</p>");
                                    out.println("<p><strong>Name:</strong> " + rs.getString("name") + "</p>");
                                    out.println("<p><strong>Email:</strong> " + rs.getString("email") + "</p>");
                                    out.println("<p><strong>Role:</strong> " + rs.getString("role") + "</p>");
                                    out.println("<img src='" + rs.getString("profile_pic")
                                                      + "' alt='Profile Picture' width='100'>");
                           } else {
                                    out.println("<h3 style='color:red;'>No customer found with given ID and email.</h3>");
                           }

                           conn.close();
                  } catch (Exception e) {
                           e.printStackTrace();
                           out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
                  }
         }
}
