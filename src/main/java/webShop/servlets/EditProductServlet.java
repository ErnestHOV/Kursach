package webShop.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

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

@WebServlet("/editProduct")
public class EditProductServlet extends HttpServlet {
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
        pw.println("<h1 class = 'heading'>Edit product</h1>");

        String sort, idS;
        sort = request.getParameter("sort");
        idS = request.getParameter("id");

        Integer id = Integer.parseInt(idS);
        ArrayList<Product> products = uploadListFromBase();
        switch(sort) {
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
        Product product = products.get(id);

        pw.println("<h2 class = 'littleHeading'>Product</h1>");
        pw.println(showProduct(product, id));

        pw.println("<h2 class = 'littleHeading'>Edit</h1>");
        pw.println(formToEditProduct(product, id, sort));


        pw.println("</body> </html>");
    }

    private String formToEditProduct(Product p, int id, String sort) {
        StringBuilder html = new StringBuilder();
        html.append("<div class='productBox'>");
        html.append("<form action='editProduct' method='post'>");
        html.append("<input type='hidden' name='id' value='" + id + "'/>");
        html.append("<input type='hidden' name='sort' value='" + sort + "'/>");

        html.append("<table class = 'txt'>");

        html.append("<tr><td>Product's name: </td>");
        html.append("<td><input type='text' name='name' class='textBox' value='" + p.getName() + "'/></td></tr>");
        html.append("<td><input type='hidden' name='lastname' value='" + p.getName() + "'/></td></tr>");

        html.append("<tr><td>Cost (only number):</td>");
        html.append("<td><input type='text' name='cost' class='textBox' value='" + p.getCost() + "'/></td></tr>");
        html.append("<td><input type='hidden' name='lastcost' value='" + p.getCost() + "'/></td></tr>");

        html.append("<tr><td>Date added (dd/mm/yyyy):</td>");
        html.append("<td><input type='text' name='date' class='textBox' value='" + p.getDateAdded() + "'/></td></tr>");
        html.append("<td><input type='hidden' name='lastdate' value='" + p.getDateAdded() + "'/></td></tr>");

        html.append("<tr><td>Number of products:</td>");
        html.append("<td><input type='text' name='number' class='textBox' value='" + p.getNumberOfProduct() + "'/> </td></tr>");
        html.append("<td><input type='hidden' name='lastnumber' value='" + p.getNumberOfProduct() + "'/></td></tr>");

        html.append("<tr><td>Description:</td>");
        html.append("<td><input type='text' name='description' class = 'textBox' value='" + p.getDescription() + "'/> </td></tr>");
        html.append("<td><input type='hidden' name='lastdescription' value='" + p.getDescription() + "'/></td></tr>");

        html.append("<tr><td>Category:</td>");
        html.append("<td><select name = 'category'>");
        html.append("<option>ALL</option>");
        html.append("<option>keyboards</option>");
        html.append("</select></td></tr>");

        html.append("<tr><td>Image (only .jpg):</td>");
        html.append("<td><input type = 'file' name = 'image'/> </td></tr>");

        html.append("<tr><td colspan='2'><input type='submit' value='SAVE' class = 'button'/></tr></td>");
        html.append(" </table> </form>");
        html.append("<div>");
        return html.toString();
    }

    private String showProduct(Product p, int id) {
        StringBuilder html = new StringBuilder();
        html.append("<div id ='sorting' style='display:none;'>cost</div>");
        html.append("<div class = 'productBox' id='prod" + id + "'>");
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
        pw.println("<h2 class = 'littleGeading'>Result product</h1>");

        String  idS;
        idS = request.getParameter("id");

        Integer id = Integer.parseInt(idS);
        ArrayList<Product> products = uploadListFromBase();

        Product product = products.get(id);

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

            if(name != null || name != "") {
                product.setName(name);
            }
            if(cost < 0 || number < 0) {
                throw new WebShopException("cost or number can't be negative");
            }
            product.setCost(cost);
            if(date != null || date != "") {
                product.setDateAdded(date);
            }
            product.setCategories(category);
            product.setNumberOfProduct(number);
            if(description != null || description != "") {
                product.setDescription(description);
            }
            if (!image.isEmpty()){
                System.out.println("to download: " + image);
                pw.println("<h2 class = 'littleHeading'> error 1 " + image + "</h1>");
                BufferedImage buffImage = ImageIO.read(new File("image/" + image));
                pw.println("<h2 class = 'littleHeading'> error 2 </h1>");
                ServletContext context = getServletContext();
                String newImagePath = "image/";
                newImagePath = context.getRealPath(newImagePath);
                newImagePath += name + ".jpg";
                System.out.println("to save: " + newImagePath);
                System.out.println(newImagePath);
                File newImage =  new File(newImagePath);
                if(newImage.createNewFile()) {
                    System.out.println();
                }
                ImageIO.write(buffImage,"jpg", newImage);
                pw.println("<h2 class = 'littleHeading'> error 3 </h1>");
            }
            pw.println(showProduct(product, id));
            saveToBase(products);
        }catch(Exception e) {
            pw.println("<h2 class = 'littleHeading'> error" + e.toString() + "</h1>");
            e.printStackTrace();

        }

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
        }catch(WebShopException wse) {
            wse.printStackTrace();
            return null;
        }catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            return null;
        }catch(Exception ex) {
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
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}