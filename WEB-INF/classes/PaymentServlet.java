import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cardNumber = request.getParameter("card-number");
        String expirationMonth = request.getParameter("expiration-month");
        String expirationYear = request.getParameter("expiration-year");
        String cvc = request.getParameter("cvc");
        String nameOnCard = request.getParameter("name-on-card");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_project", "root", "");

            String query = "INSERT INTO payment_details (card_number, expiration_month, expiration_year, cvc, name_on_card) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, expirationMonth);
            pstmt.setString(3, expirationYear);
            pstmt.setString(4, cvc);
            pstmt.setString(5, nameOnCard);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            response.sendRedirect("payment_success.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
