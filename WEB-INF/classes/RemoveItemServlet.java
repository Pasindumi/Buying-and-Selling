import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RemoveItemServlet")
public class RemoveItemServlet extends HttpServlet {

         private static final String DB_URL = "jdbc:mysql://localhost:3306/shopping_cart";
         private static final String DB_USER = "root";
         private static final String DB_PASSWORD = "";

         @Override
         protected void doGet(HttpServletRequest request, HttpServletResponse response)
                           throws ServletException, IOException {
                  int id = Integer.parseInt(request.getParameter("id"));

                  try {
                           Class.forName("com.mysql.cj.jdbc.Driver");
                           Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                           String query = "DELETE FROM cart WHERE id = ?";
                           PreparedStatement stmt = conn.prepareStatement(query);
                           stmt.setInt(1, id);

                           stmt.executeUpdate();
                           conn.close();

                           response.sendRedirect("ViewCartServlet");
                  } catch (Exception e) {
                           response.getWriter().println("<p>Error: " + e.getMessage() + "</p>");
                  }
         }
}
