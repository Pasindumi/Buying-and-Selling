import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
         private static final String DB_URL = "jdbc:mysql://localhost:3306/oop_project";
         private static final String DB_USER = "root";
         private static final String DB_PASS = "";

         protected void doGet(HttpServletRequest request, HttpServletResponse response)
                           throws ServletException, IOException {
                  response.setContentType("application/json");
                  response.setCharacterEncoding("UTF-8");

                  try {
                           Class.forName("com.mysql.cj.jdbc.Driver");
                           Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

                           String sql = "SELECT name, email, profile_pic FROM users WHERE id = 1"; // Fetch user data by
                                                                                                   // ID
                           PreparedStatement stmt = conn.prepareStatement(sql);
                           ResultSet rs = stmt.executeQuery();

                           JSONObject userData = new JSONObject();
                           if (rs.next()) {
                                    userData.put("name", rs.getString("name"));
                                    userData.put("email", rs.getString("email"));
                                    userData.put("profile_pic", rs.getString("profile_pic"));
                           }
                           response.getWriter().write(userData.toString());

                           rs.close();
                           stmt.close();
                           conn.close();
                  } catch (Exception e) {
                           e.printStackTrace();
                           response.getWriter().write("{}");
                  }
         }
}
