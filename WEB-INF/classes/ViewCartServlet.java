import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewCartServlet")
public class ViewCartServlet extends HttpServlet {

         private static final String DB_URL = "jdbc:mysql://localhost:3306/oop_project";
         private static final String DB_USER = "root";
         private static final String DB_PASSWORD = "";

         @Override
         protected void doGet(HttpServletRequest request, HttpServletResponse response)
                           throws ServletException, IOException {
                  response.setContentType("text/html");
                  PrintWriter out = response.getWriter();

                  try {
                           Class.forName("com.mysql.cj.jdbc.Driver");
                           Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                           String query = "SELECT * FROM cart";
                           PreparedStatement stmt = conn.prepareStatement(query);
                           ResultSet rs = stmt.executeQuery();

                           out.println("<html><body>");
                           out.println("<h1>Shopping Cart</h1>");
                           out.println("<table border='1'>");
                           out.println("<tr><th>Product ID</th><th>Product Name</th><th>Quantity</th><th>Price</th><th>Image</th><th>Actions</th></tr>");

                           while (rs.next()) {
                                    int id = rs.getInt("id");
                                    int productId = rs.getInt("product_id");
                                    String productName = rs.getString("product_name");
                                    int quantity = rs.getInt("quantity");
                                    double price = rs.getDouble("price");
                                    String imageUrl = rs.getString("image_url");

                                    out.println("<tr>");
                                    out.println("<td>" + productId + "</td>");
                                    out.println("<td>" + productName + "</td>");
                                    out.println("<td>" + quantity + "</td>");
                                    out.println("<td>" + price + "</td>");
                                    out.println("<td><img src='" + imageUrl
                                                      + "' alt='Product Image' width='100'></td>");
                                    out.println("<td><a href='RemoveItemServlet?id=" + id + "'>Remove</a></td>");
                                    out.println("</tr>");
                           }
                           out.println("</table>");
                           out.println("<a href='add_item.html'>Add More Items</a>");
                           out.println("</body></html>");

                           conn.close();
                  } catch (Exception e) {
                           out.println("<p>Error: " + e.getMessage() + "</p>");
                  }
         }
}
