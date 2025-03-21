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

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
         private static final long serialVersionUID = 1L;

         // Database connection details
         private static final String URL = "jdbc:mysql://localhost:3306/oop_project";
         private static final String USER = "root";
         private static final String PASSWORD = "";

         protected void doGet(HttpServletRequest request, HttpServletResponse response)
                           throws ServletException, IOException {

                  response.setContentType("text/html; charset=UTF-8");
                  response.setCharacterEncoding("UTF-8");

                  PrintWriter out = response.getWriter();

                  // Start HTML with Bootstrap and custom CSS for chat-like design
                  out.println("<html><head><title>Cart Report</title>");
                  out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
                  out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css'>"); // Font
                                                                                                                                           // Awesome
                                                                                                                                           // for
                                                                                                                                           // icons
                  out.println("<style>");
                  out.println("body { background-color: #f1f3f5; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }");
                  out.println(".container { background: #ffffff; padding: 20px; border-radius: 12px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); max-width: 900px; margin-top: 30px; }");
                  out.println(".header { text-align: center; font-size: 24px; font-weight: 600; color: rgb(46, 194, 174); }");
                  out.println(".table { font-size: 16px; border-collapse: separate; border-spacing: 0 15px; width: 100%; }");
                  out.println(".table th { background-color:rgb(41, 102, 94); color: white; padding: 10px 15px; }");
                  out.println(".table td { background-color: #ffffff; padding: 10px 15px; border: 1px solid #ddd; }");
                  out.println(".table .fw-bold { color:rgb(41, 102, 94); }");
                  out.println(".total-box { background-color:rgb(174, 220, 230); color: black; padding: 15px; border-radius: 12px; font-size: 1.2rem; margin-top: 20px; text-align: center; }");
                  out.println(".btn-back { background-color:rgb(41, 102, 94); color: white; padding: 10px 20px; font-size: 16px; border-radius: 8px; text-decoration: none; display: inline-block; margin-top: 20px; }");
                  out.println(".btn-back:hover { background-color: rgb(41, 102, 94)); }");
                  out.println("</style>");
                  out.println("</head><body>");

                  out.println("<div class='container'>");
                  out.println("<div class='header'><i class='fas fa-shopping-cart'></i> Cart Item Details</div>");
                  out.println("<table class='table'>");
                  out.println("<thead><tr><th>Product ID</th><th>Product Name</th><th>Price (Rs.)</th><th>Quantity</th><th>Total Price (Rs.)</th></tr></thead>");
                  out.println("<tbody>");

                  double grandTotal = 0.0; // Variable to store total price of all items

                  try {
                           // Establish database connection
                           Class.forName("com.mysql.cj.jdbc.Driver");
                           Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                           String query = "SELECT * FROM cartitem";
                           PreparedStatement ps = con.prepareStatement(query);
                           ResultSet rs = ps.executeQuery();

                           while (rs.next()) {
                                    int productId = rs.getInt("productid");
                                    String productName = rs.getString("product_name");
                                    double price = rs.getDouble("product_price");
                                    int quantity = rs.getInt("quantity");
                                    double totalPrice = price * quantity; // Calculate total price for each item

                                    grandTotal += totalPrice; // Add to grand total

                                    out.println("<tr><td>" + productId + "</td>"
                                                      + "<td>" + productName + "</td>"
                                                      + "<td>Rs. " + price + "</td>"
                                                      + "<td>" + quantity + "</td>"
                                                      + "<td class='fw-bold'>Rs. " + totalPrice + "</td></tr>");
                           }
                           out.println("</tbody></table>"); // Close table

                           // Display Grand Total
                           out.println("<div class='total-box'>");
                           out.println("<h4><i class='fas fa-coins'></i> Grand Total: Rs. " + grandTotal + "</h4>");
                           out.println("</div>");

                           out.println("<a href='index.html' class='btn-back'><i class='fas fa-arrow-left'></i> Go Back</a>");
                           out.println("</div></body></html>"); // Close container and HTML

                           con.close();
                  } catch (Exception e) {
                           out.println("<p class='text-danger text-center'>Error: " + e.getMessage() + "</p>");
                  }
         }
}
