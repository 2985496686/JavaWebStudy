package filters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/Login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type","text/html,UTF-8");
        HttpSession session = req.getSession();
        String password = req.getParameter("password");
        if(password.equals("123456"))
        session.setAttribute("email",req.getParameter("email"));
        resp.getWriter().write("登录成功！！！");
    }
}
