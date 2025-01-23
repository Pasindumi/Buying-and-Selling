package com.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
         @Override
         protected void doPost(HttpServletRequest request, HttpServletResponse response)
                           throws ServletException, IOException {
                  HttpSession session = request.getSession();

                  // Retrieve the current cart from the session
                  List<String> cart = (List<String>) session.getAttribute("cart");
                  if (cart == null) {
                           cart = new ArrayList<>();
                  }

                  // Add the selected product to the cart
                  String product = request.getParameter("product");
                  if (product != null && !product.isEmpty()) {
                           cart.add(product);
                  }

                  // Save the updated cart back to the session
                  session.setAttribute("cart", cart);

                  // Generate the response
                  response.setContentType("text/html");
                  PrintWriter out = response.getWriter();

                  out.println("<html><head><title>Your Cart</title></head><body>");
                  out.println("<h1>Products in Your Cart</h1>");
                  out.println("<ul>");
                  for (String item : cart) {
                           out.println("<li>" + item + "</li>");
                  }
                  out.println("</ul>");
                  out.println("<a href='cart.html'>Back to Products</a>");
                  out.println("</body></html>");
         }
}
