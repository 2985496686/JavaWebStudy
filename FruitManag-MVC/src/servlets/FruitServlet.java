package servlets;

import fruit.Fruit;
import fruit.FruitDAO;
import fruit.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String operation = req.getParameter("operation");
        if(operation==null || operation.equals("")){
            operation = "index";
        }
        /*
        switch (operation){
            case "index":
                index(req, resp);
                break;
            case "add":
                add(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            case"edit":
                edit(req, resp);
                break;
            case "update":
                update(req, resp);
                break;
            default:
                throw new RuntimeException("Illegal Operation");
        }*/
        //使用反射机制来优化掉switch-case
        Method[] methods = this.getClass().getDeclaredMethods();
        for(Method method:methods){
            String methodName  = method.getName();
            if(operation.equals(methodName)){
                try {
                    method.setAccessible(true);
                    String returnStr = (String)method.invoke(this,req);
                    if(returnStr.startsWith("redirect:")){
                        returnStr = returnStr.substring("redirect:".length());
                        resp.sendRedirect(returnStr);
                    }else if(!returnStr.equals("error")){
                        super.processTemplate(returnStr,req,resp);
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        throw  new RuntimeException("Illegal Operation");
    }
    private String index(HttpServletRequest req) throws  IOException {
        req.setCharacterEncoding("UTF-8");
        FruitDAO fruitDAO = new FruitDAO();
        HttpSession session = req.getSession();
        Connection conn = null;
        int pageNo = 1;
        String pageNoStr = req.getParameter("pageNo");
        try {
            conn = JDBCUtils.getConnection();
            if(pageNoStr!=null && !"".equals(pageNoStr)){
                pageNo = Integer.parseInt(pageNoStr);
            }

            String keyword = (String)session.getAttribute("keyword");
            String oper = req.getParameter("oper");
            //说明这里点击的是查询按钮跳转过来的
            if(oper!=null && oper.equals("search")){
                pageNo = 1;
                keyword = req.getParameter("keyword");
                if(keyword==null){
                    keyword = "";
                }
                session.setAttribute("keyword",keyword);
            }else {
                if(keyword==null){
                    keyword = "";
                }
            }
            List<Fruit> list = fruitDAO.getAll(conn,pageNo,keyword);
            session.setAttribute("fruitList",list);
            session.setAttribute("pageNo",pageNo);
            long pageCount = fruitDAO.getPageCount(conn,keyword);
            session.setAttribute("pageCount",pageCount);
            /*
            此处的视图名称是：index
            thymeleaf会将逻辑视图名称对应到物理视图名称上去
            逻辑视图名称：index
            物理视图名称：view-prefix(/) + 逻辑视图名称(index) + view-suffer(.html)
             */
            //super.processTemplate("/index",req,resp);
            return "/index";
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "error";
    }

    private String add(HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String fpriceStr = req.getParameter("fprice");
        String fcountStr = req.getParameter("fcount");
        String fname = req.getParameter("fname");
        String fremark = req.getParameter("fremark");
        Connection conn = null;
        FruitDAO fruitDAO = new FruitDAO();
        if(fpriceStr!=null && !"".equals(fpriceStr) && fcountStr!=null && !"".equals(fcountStr)){
            int fprice = Integer.parseInt(fpriceStr);
            int fcount = Integer.parseInt(fcountStr);
            try {
                conn = JDBCUtils.getConnection();
                fruitDAO.insert(conn,new Fruit(fname,fprice,fcount,fremark));
                //resp.sendRedirect("/fruit.do");
                return "redirect:/fruit.do";
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "error";
    }



    private String delete(HttpServletRequest req) throws IOException {
        String fidStr = req.getParameter("fid");
        Connection conn = null;
        FruitDAO fruitDAO = new FruitDAO();
        if(fidStr!=null && !"".equals(fidStr)){
            try {
                int fid = Integer.parseInt(fidStr);
                conn = JDBCUtils.getConnection();
                fruitDAO.delFruitById(conn,fid);
                //resp.sendRedirect("/fruit.do");
                return "redirect:/fruit.do";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "error";
    }


    private String edit(HttpServletRequest req) throws IOException {
        String fidStr = req.getParameter("fid");
        Connection conn = null;
        FruitDAO fruitDAO = new FruitDAO();
        if(fidStr != null && !fidStr.equals("")){
            int fid = Integer.parseInt(fidStr);
            try {
                conn = JDBCUtils.getConnection();
                Fruit fruit = fruitDAO.getFruitById(conn, fid);
                req.setAttribute("fruit",fruit);
                //super.processTemplate("edit",req,resp);
                return "edit";
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "error";
    }

    private String update(HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String fidStr = req.getParameter("fid");
        String fpriceStr = req.getParameter("fprice");
        String fcountStr = req.getParameter("fcount");
        String fname = req.getParameter("fname");
        String fremark = req.getParameter("fremark");
        FruitDAO fruitDAO = new FruitDAO();
        Connection conn = null;
        if(fidStr!=null && !"".equals(fidStr) && fpriceStr!=null && !"".equals(fpriceStr) && fcountStr!=null && !"".equals(fcountStr)){
            int fid = Integer.parseInt(fidStr);
            int fprice = Integer.parseInt(fpriceStr);
            int fcount = Integer.parseInt(fcountStr);
            try {
                conn = JDBCUtils.getConnection();
                fruitDAO.update(conn,new Fruit(fid,fname,fprice,fcount,fremark));
                //这里是客户端重定向,因为这里虽然数据库修改了，但是会话的保存作用域没有被修改
                /*
                未解决疑问:这里使用服务器内部转发会报"405"错误()
                req.getRequestDispatcher("/index").forward(req,resp);
                 */
                //resp.sendRedirect("/fruit.do");
                return "redirect:/fruit.do";
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "error";
    }
}
