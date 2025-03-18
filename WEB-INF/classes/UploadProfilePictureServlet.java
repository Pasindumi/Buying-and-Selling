import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UploadProfilePictureServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)

public class UploadProfilePictureServlet extends HttpServlet {
         private static final String UPLOAD_DIRECTORY = "profile_pics";
         private static final String DB_URL = "jdbc:mysql://localhost:3306/oop_project";
         private static final String DB_USER = "root";
         private static final String DB_PASS = "";

         protected void doPost(HttpServletRequest request, HttpServletResponse response)
                           throws ServletException, IOException {
                  String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                  File uploadDir = new File(uploadPath);
                  if (!uploadDir.exists())
                           uploadDir.mkdir();

                  try {
                           Part filePart = request.getPart("profilePic");
                           String fileName = "user_1.jpg";
                           String filePath = uploadPath + File.separator + fileName;
                           filePart.write(filePath);

                           Class.forName("com.mysql.cj.jdbc.Driver");
                           Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

                           String sql = "UPDATE users SET profile_pic = ? WHERE id = 1";
                           PreparedStatement stmt = conn.prepareStatement(sql);
                           stmt.setString(1, fileName);
                           stmt.executeUpdate();
                           stmt.close();
                           conn.close();

                           response.sendRedirect("index.html");
                  } catch (Exception e) {
                           e.printStackTrace();
                  }
         }
}
