package servlets.cookie_servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/createCookie")
public class CreateCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        Cookie cookie1 = new Cookie("JSESSIONID","value1");
        cookie1.setMaxAge(0);
        //cookie1.setMaxAge(60*60);
        //Cookie cookie2 = new Cookie("cookie2","1133");
        //cookie2.setMaxAge(0);
        //Cookie cookie3 = new Cookie("cookie3","1144");
        resp.addCookie(cookie1);
        //resp.addCookie(cookie2);
        //resp.addCookie(cookie3);
        /*
        Cookie cookie = new Cookie("cookie1","1122");
        String path = req.getContextPath()+"/abc";
        System.out.println(path);
        cookie.setPath(path);
        resp.addCookie(cookie);
        resp.getWriter().write("Cookie创建成功！");
        */
    }
}
