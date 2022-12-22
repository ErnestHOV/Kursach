package webShop.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
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
        pw.println("<h1 class = 'heading'>Logout page</h1>");
        HttpSession session = request.getSession();
        session.removeAttribute("password");
        pw.println("<p class = 'txt'>You are logOUT</p>");
        pw.println("<a href= 'kuruBoards' class = 'button' >main<br>page</a>");
        pw.println("</body> </html>");
    }

}