import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServletss extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/oop_project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL query to find the user by email and password
            String query = "SELECT id, name, email, password FROM users WHERE email = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // If the credentials match, create a session and send the user ID back to the client
                int userId = rs.getInt("id");

                // Store the user ID in the session (optional, if needed for server-side use)
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);

                // Send the user ID back to the client as a JSON response
                response.setContentType("application/json");
                response.getWriter().print("{\"userId\": " + userId + "}");
            } else {
                // If credentials don't match, show an error message
                response.getWriter().println("Invalid email or password");
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error");
        }
    }
}