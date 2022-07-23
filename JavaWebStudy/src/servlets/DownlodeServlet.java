package servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
@WebServlet("/down")
public class DownlodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取文件的下载路径
        String realPath = "D:\\图片\\路飞.png";
        //获取文件名,该文件名时客户端下载文件看见的名字
        String fileName = realPath.substring(realPath.lastIndexOf("\\")+1);
        //用下面方式当文件名为中文的时候会出现乱码的情况
        //resp.setHeader("Content-Disposition","attachment;filename="+ fileName);
        resp.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        //获取输入流
        FileInputStream fis = new FileInputStream(realPath);
        //缓冲区
        int len = 0;
        byte[] buffer = new byte[1024];
        //获取响应的输出流
        ServletOutputStream output = resp.getOutputStream();
        while((len = fis.read(buffer)) != -1){
            output.write(buffer,0,len);
        }
    }

}
