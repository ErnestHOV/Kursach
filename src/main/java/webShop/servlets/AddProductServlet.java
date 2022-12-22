package webShop.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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

@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String pathCSS1 = "\"static/style.css\"";
        String pathCSS2 = "\"static/menu.css\"";

        pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<title>Edit product</title>");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS1 + " type=\"text/css\">");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS2 + " type=\"text/css\">");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h1 class = 'heading'>Add product</h1>");
        pw.println(formToAddProduct());
        pw.println("</body> </html>");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String pathCSS1 = "\"static/style.css\"";
        String pathCSS2 = "\"static/menu.css\"";

        pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<title>Edit product</title>");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS1 + " type=\"text/css\">");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS2 + " type=\"text/css\">");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h1 class = 'heading'>Edit product</h1>");
        pw.println("<h2 class = 'littleHeading'>Result product</h1>");

        ArrayList<Product> products = uploadListFromBase();

        try {
            String name = request.getParameter("name");
            String costS = request.getParameter("cost");
            String date = request.getParameter("date");
            String numberS = request.getParameter("number");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            String image = request.getParameter("image");

            Integer cost = Integer.parseInt(costS);
            Integer number = Integer.parseInt(numberS);
            if (cost < 0 || number < 0) {
                throw new WebShopException("cost or number can't be negative");
            }

            Product product = new Product(name, cost, date, number, description, category);
            products.add(product);

            System.out.println("to download: " + image);
            BufferedImage buffImage = ImageIO.read(new File("C:/Users/aser/Desktop/Курсовая ТП 3 курс/Курсовик/webShop/src/main/webapp/image/" + image));

            ServletContext context = getServletContext();
            String newImagePath = "image/";
            newImagePath = context.getRealPath(newImagePath);
            newImagePath += name + ".jpg";
            System.out.println("to save: " + newImagePath);
            System.out.println(newImagePath);
            File newImage = new File(newImagePath);
            if (newImage.createNewFile()) {
                System.out.println();
            }

            ImageIO.write(buffImage, "jpg", newImage);
            pw.println(showProduct(product));
            saveToBase(products);
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2 class = 'littleHeading'>Sorry, you can't add this product to card: " + e.toString() + "</h1>");
        }

        pw.println("<a href= 'kuruBoards' class = 'button' >main<br>page</a>");
        pw.println("</body> </html>");
    }

    private void saveToBase(ArrayList<Product> products) {
        String basePath = "base/base.txt";
        ServletContext context = getServletContext();
        basePath = context.getRealPath(basePath);
        try {
            WriteProducts wp = new WriteProducts(basePath);
            wp.writeProducts(products);
            wp.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String showProduct(Product p) {
        StringBuilder html = new StringBuilder();
        html.append("<div id ='sorting' style='display:none;'>cost</div>");
        html.append("<div class = 'productBox'>");
        html.append("<table width='100%' cellspacing='0' cellpadding='0'>");
        html.append("<tr>");
        html.append("<td class='leftcol'><img src='image/" + p.getName() + ".jpg'");
        html.append("width='250' height='178' alt=''></td>");
        html.append("<td valign='top'>");
        html.append("<strong class = 'fontForText' align='left'>").append(p.getName()).append(" </strong><br>");
        html.append("<strong class = 'fontForText' align='left'>COST:").append(p.getCost()).append(" </strong><br>");
        html.append("<div class = 'fontForText' >AVAILABLE:").append(p.getNumberOfProduct()).append(" </div><br>");
        html.append("<i class = 'fontForText' align='left'>").append(p.getDateAdded()).append("</i>");
        html.append("<br>////////////////////////////////////////////////</br>");
        html.append("<br><div class = 'productDescription'>").append(p.getDescription()).append("</div>");
        html.append("</td>");
        html.append("</tr>");
        html.append("</table>");
        html.append("</div>");
        return html.toString();
    }

    private String formToAddProduct() {
        StringBuilder html = new StringBuilder();
        html.append("<div class='productBox'>");
        html.append("<form action='addProduct' method='post'>");

        html.append("<table class = 'txt'>");

        html.append("<tr><td>Name: </td>");
        html.append("<td><input type='text' name='name' class='textBox'/></td></tr>");

        html.append("<tr><td>Cost $:</td>");
        html.append("<td><input type='text' name='cost' class='textBox'/></td></tr>");

        html.append("<tr><td>Date (dd/mm/yyyy):</td>");
        html.append("<td><input type='text' name='date' class='textBox'/></td></tr>");

        html.append("<tr><td>Number of products:</td>");
        html.append("<td><input type='text' name='number' class='textBox'/> </td></tr>");

        html.append("<tr><td>Description:</td>");
        html.append("<td><input type='text' name='description' class = 'textBox'/> </td></tr>");

        html.append("<tr><td>Category:</td>");
        html.append("<td><select name = 'category'>");
        html.append("<option>keyboards</option>");

        html.append("</select></td></tr>");

        html.append("<tr><td>Image ( .jpg):</td>");
        html.append("<td><input type = 'file' name = 'image'/> </td></tr>");

        html.append("<tr><td colspan='2'><input type='submit' value='Enter' class = 'button'/></tr></td>");
        html.append(" </table> </form>");
        html.append("<div>");
        return html.toString();
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

}