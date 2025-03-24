import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ArrayList<MobilePhone> mobilePhones = new ArrayList<>();

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_project", "root", "");

            String query = "SELECT * FROM products";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MobilePhone phone = new MobilePhone(
                    rs.getString("productName"),
                    rs.getString("color"),
                    rs.getInt("quantity"),
                    rs.getInt("warranty"),
                    rs.getDouble("price"),
                    rs.getInt("storage")
                );
                mobilePhones.add(phone);
            }

            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }

        // Bootstrap Header
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Mobile Phones</title>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
        out.println("</head>");
        out.println("<body class='bg-light'>");

        // Page Title
        out.println("<div class='container my-5'>");
        out.println("<h2 class='text-center mb-4'>Mobile Phones</h2>");

        // Table
        out.println("<div class='table-responsive'>");
        out.println("<table class='table table-bordered table-striped table-hover shadow-sm'>");
        out.println("<thead class='thead-dark'>");
        out.println("<tr>");
        out.println("<th>Product Name</th>");
        out.println("<th>Color</th>");
        out.println("<th>Quantity</th>");
        out.println("<th>Warranty</th>");
        out.println("<th>Price</th>");
        out.println("<th>Storage</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        for (MobilePhone phone : mobilePhones) {
            out.println("<tr>");
            out.println("<td>" + phone.getProductName() + "</td>");
            out.println("<td>" + phone.getColor() + "</td>");
            out.println("<td>" + phone.getQuantity() + "</td>");
            out.println("<td>" + phone.getWarranty() + " years</td>");
            out.println("<td>$" + phone.getPrice() + "</td>");
            out.println("<td>" + phone.getStorage() + " GB</td>");
            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");  // Close Table Responsive Div
        out.println("</div>");  // Close Container

        // Bootstrap JS
        out.println("<script src='https://code.jquery.com/jquery-3.5.1.slim.min.js'></script>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js'></script>");
        out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js'></script>");

        out.println("</body>");
        out.println("</html>");
    }
}
