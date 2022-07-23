package servlets.cookie_servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        Cookie[] cookies = req.getCookies();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Cookie emailCookie = null;
        Cookie passwordCookie = null;
        if(cookies != null){
            for(Cookie cookie:cookies){
                if("email".equals(cookie.getName())){
                    emailCookie = cookie;
                }
                if("password".equals(cookie.getName())){
                    passwordCookie = cookie;
                }
            }
        }
        if(emailCookie == null){
            emailCookie = new Cookie("email",email);
            emailCookie.setMaxAge(60*60);
            resp.addCookie(emailCookie);
        }
        if(passwordCookie == null){
            passwordCookie = new Cookie("password",password);
            passwordCookie.setMaxAge(60*60);
            resp.addCookie(passwordCookie);
        }
        PrintWriter writer = resp.getWriter();
        writer.write("<html><body>");
        writer.write("<h1>登录成功！</h2>");
        writer.write("</html></body>");
    }
}
