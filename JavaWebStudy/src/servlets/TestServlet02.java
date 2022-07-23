package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TestServlet02 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        HttpSession session = req.getSession();
        session.setAttribute("123","张三");
        */
        req.setCharacterEncoding("UTF-8");//这行代码要放在所有获取参数动作之前
        resp.setCharacterEncoding("UTF-8");//response回写数据时使用UTF-8
        resp.setHeader("Content-Type","text/html;charset=utf-8");
        String name = req.getParameter("fname");
        Integer price = Integer.parseInt(req.getParameter("fprice"));
        Integer count = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("fremark");
        System.out.println(name);
        System.out.println(price);
        System.out.println(count);
        System.out.println(remark);
    }
}
