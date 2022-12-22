package webShop.servlets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webShop.exception.WebShopException;
import webShop.io.ReadProducts;
import webShop.io.WriteProducts;
import webShop.model.Product;

@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String pathCSS1 = "\"static/style.css\"";
        String pathCSS2 = "\"static/menu.css\"";

        pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<title>Logout</title>");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS1 + " type=\"text/css\">");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS2 + " type=\"text/css\">");
        pw.println("</head>");

        pw.println("<body>");
        pw.println("<h1 class = 'heading'>Delete product</h1>");

        String sort, idS;
        sort = request.getParameter("sort");
        idS = request.getParameter("id");
        int id = Integer.parseInt(idS);
        ArrayList<Product> products = uploadListFromBase();
        switch (sort) {
            case "alph":
                Collections.sort(products, Product.alphComparator);
                break;
            case "cost":
                Collections.sort(products, Product.costComparator);
                break;
            case "date":
                Collections.sort(products, Product.dateComparator);
                break;
        }
        products.remove(id);
        saveToBase(products);

        pw.println("<p class = 'txt'>You are deleted product(</p>");
        pw.println("<a href= 'kuruBoards' class = 'button' >main<br>page</a>");
        pw.println("</body> </html>");
    }

    private ArrayList<Product> uploadListFromBase() {
        ArrayList<Product> products = null;
        String basePath = "base/base.txt";
        ServletContext context = getServletContext();
        basePath = context.getRealPath(basePath);
        try {
            ReadProducts readProducts = new ReadProducts(basePath);
            products = readProducts.readFile();
        } catch (WebShopException wse) {
            wse.printStackTrace();
            return null;
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return products;
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

}