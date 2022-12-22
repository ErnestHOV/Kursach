package webShop.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;


import webShop.model.*;
import webShop.io.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        String pathCSS1 = "\"static/style.css\"";
        String pathCSS2 = "\"static/menu.css\"";

        pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<title>Cart</title>");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS1 + " type=\"text/css\">");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS2 + " type=\"text/css\">");
        pw.println("</head>");

        pw.println("<body>");
        pw.println("<h1 class = 'heading'>Kuru Boards</h1>");
        pw.println(showAllProducts(cart));
        pw.println("<a href= 'kuruBoards' class = 'button' >main<br>page</a>");
        pw.println("</body>");
        pw.println("</html>");
    }

    private void saveToBase(ArrayList<Product> products) {
        String basePath = "base/base.txt";
        ServletContext context = getServletContext();
        basePath = context.getRealPath(basePath);
        try {
            WriteProducts wp = new WriteProducts(basePath);
            wp.writeProducts(products);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<Product> uploadListFromBase() {
        ArrayList<Product> products = null;
        String basePath = "base/base.txt";
        ServletContext context = getServletContext();
        basePath = context.getRealPath(basePath);

        try {

            ReadProducts readProducts = new ReadProducts(basePath);
            products = readProducts.readFile();

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }


        return products;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        int id = Integer.parseInt(request.getParameter("id"));
        cart.removeFromCart(id);

        ArrayList<Product> products = uploadListFromBase();
        Product product = products.get(id);
        product.setNumberOfProduct(product.getNumberOfProduct() + 1);
        saveToBase(products);


        String pathCSS1 = "\"static/style.css\"";
        String pathCSS2 = "\"static/menu.css\"";

        pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<title>Cart</title>");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS1 + " type=\"text/css\">");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS2 + " type=\"text/css\">");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h2 class = 'littleHeading'>You are deleted product.</h1>");
        pw.println("<a href= 'cart' class = 'button' >back to cart</a>");
        pw.println("</body>");
        pw.println("</html>");
    }

    private String showAllProducts(ShoppingCart cart) {
        if (cart == null) return "";
        StringBuilder html = new StringBuilder();
        html.append("<div id='allproducts'>");
        for (int i = 0; i < cart.getCart().size(); i++) {
            Product p = cart.getCart().get(i);
            html.append("<div class = 'productBox' id='prod" + i + "'>");
            html.append("<table width='100%' cellspacing='0' cellpadding='0'>");
            html.append("<tr>");
            html.append("<td class='leftcol'><img src='image/" + p.getName() + ".jpg'");
            html.append("width='250' height='178' alt=''></td>");
            html.append("<td valign='top'>");
            html.append("<strong class = 'fontForText' align='left'>").append(p.getName()).append(" </strong><br>");
            html.append("<strong class = 'fontForText' align='left'>COST:").append(p.getCost()).append(" </strong><br>");
            html.append("<i class = 'fontForText' align='left'>").append(p.getDateAdded()).append("</i>");
            html.append("<br>////////////////////////////////////////////////</br>");
            html.append("<br><div class = 'productDescription'>").append(p.getDescription()).append("</div>");
            html.append("</td>");
            html.append("</tr>");
            html.append("<tr>");
            html.append("<form action='cart' method='post'>");
            html.append("<input type='hidden' name='id' value='" + i + "'>");
            html.append("</td></tr><tr><td>");
            html.append("<input type='image' src='image/delete.png' style='width:40px;height:40;' " + " alt='Delete product from cart' class = 'littleButton' /><td>");
            html.append("</form>");
            html.append("</tr>");
            html.append("</table>");
            html.append("</div>");
        }
        html.append("</div>");
        return html.toString();
    }

}