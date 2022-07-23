package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet04 extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("test04执行");
        /*
        //服务器端的内部转发
        req.getRequestDispatcher("test05").forward(req,resp);
        */
        //客户端重定向
        resp.sendRedirect("test05");
    }
}
