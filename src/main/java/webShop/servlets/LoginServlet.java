package webShop.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String pathCSS1 = "\"static/style.css\"";
        String pathCSS2 = "\"static/menu.css\"";

        pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<title>Login</title>");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS1 + " type=\"text/css\">");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS2 + " type=\"text/css\">");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h1 class = 'heading'>HI</h1>");
        pw.println(createBody());
        pw.println("</body> </html>");
    }

    private String createBody() {
        StringBuilder html = new StringBuilder();
        html.append("<form action='login' method='post'>");
        html.append("</td></tr><tr> <td>Password:</td> <td>");
        html.append("<input type='text' name='password' class = 'textBox'/>");
        html.append("</td> </tr> <tr><td colspan='2'><input type='submit' value='Enter' class = 'button'/></td>");
        html.append("</tr> </table> </form>");
        return html.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        String pathCSS1 = "\"static/style.css\"";
        String pathCSS2 = "\"static/menu.css\"";

        pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS1 + " type=\"text/css\">");
        pw.println("<link rel=\"stylesheet\" href=" + pathCSS2 + " type=\"text/css\">");
        pw.println("</head>");

        pw.println("<body>");
        String password = request.getParameter("password");
        boolean checkInBase = password.equals("0000");
        if (checkInBase) {
            pw.println("<p class = 'txt'>You are in SyStEm!</p>");
            HttpSession session = request.getSession();
            session.setAttribute("password", password);
        } else {
            pw.println("<p class = 'txt'>Try Again.</p>");
        }
        pw.println("<a href= 'kuruBoards' class = 'button' >main<br>page</a>");
        pw.println("</body> </html>");
    }

}