package servlets.ajax_servlets;

import com.google.gson.Gson;
import json.People;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/jsAjax")
public class AjaxServletTest01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        People people = new People("张三",45);
        Gson gson = new Gson();
        String s = gson.toJson(people);
        resp.getWriter().write(s);
    }
}
