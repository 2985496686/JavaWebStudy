package servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TestServlet01 extends HttpServlet {
    public TestServlet01(){
        System.out.println("正在实例化......");
    }
    @Override
    public void init() throws ServletException {
        System.out.println("正在初始化......");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("正在服务......");
        /*
        HttpSession session = req.getSession();
        System.out.println("session id = " +session .getId());
        System.out.println("isNew = " + session.isNew());
        System.out.println("time = " + session.getMaxInactiveInterval());
        */
    }

    @Override
    public void destroy() {
        System.out.println("正在销毁......");
    }
}
