package servlets;

import fruit.Fruit;
import fruit.FruitDAO;
import fruit.JDBCUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class AddServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("开始服务...");
        doPost(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        Tomcat8之前需要进行如下转换，Tomcat8之后不需要进行转换
        String fname = req.getParameter("fname");
        byte[] bytes = fname.getBytes("ISO-8859-1");
        fname = new String(bytes,"UTF-8");
        */

        System.out.println("doPost执行！");
        req.setCharacterEncoding("UTF-8");//这行代码要放在所有获取参数动作之前
        resp.setCharacterEncoding("UTF-8");//response回写数据时使用UTF-8
        resp.setHeader("Content-Type","text/html;charset=utf-8");
        String name = req.getParameter("fname");
        Integer price = Integer.parseInt(req.getParameter("fprice"));
        Integer count = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("fremark");
        PrintWriter writer = resp.getWriter();
        writer.print("<html><head></head><body><p>" + name +"添加成功！！！"+"</p></body></html>");

        System.out.println(name);
        System.out.println(price);
        System.out.println(count);
        System.out.println(remark);

        req.getRequestDispatcher("/test02").forward(req,resp);
    }


}
