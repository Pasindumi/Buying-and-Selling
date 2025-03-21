import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class additm extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/oop_project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {
        var2.setContentType("text/html");
    
        try {
            String var4 = var1.getParameter("itemName");
            String var5 = var1.getParameter("price");
            String var6 = var1.getParameter("mainCategory");
            String var7 = var1.getParameter("subCategory");
            String var8 = var1.getParameter("sellerName");
            String var9 = var1.getParameter("address");
            String var10 = var1.getParameter("contactNumber");
            String var11 = var1.getParameter("email");
    
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection var12 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    
            String var13 = "INSERT INTO items (item_name, price, main_category, sub_category, seller_name, address, contact_number, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement var14 = var12.prepareStatement(var13);
            var14.setString(1, var4);
            var14.setString(2, var5);
            var14.setString(3, var6);
            var14.setString(4, var7);
            var14.setString(5, var8);
            var14.setString(6, var9);
            var14.setString(7, var10);
            var14.setString(8, var11);
    
            int var15 = var14.executeUpdate();
            var12.close();
    
            if (var15 > 0) {
                // Redirect to the image upload page (replace "uploadImages.jsp" with your page)
                var2.sendRedirect("imageUploadPage.html");
            } else {
                var2.sendRedirect("errorPage.html"); // Redirect to an error page if needed
            }
        } catch (Exception var16) {
            var16.printStackTrace();
            var2.sendRedirect("errorPage.jsp"); // Redirect to an error page if there's an exception
        }
    }
    
}
