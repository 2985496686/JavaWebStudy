package servlets;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/refresh")
public class RefreshCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //让浏览器三秒刷新一次
        resp.setHeader("refresh","3");
        //在内存中创建一个图片
        BufferedImage image = new BufferedImage(80,20,BufferedImage.TYPE_INT_RGB);
        //得到用来操作图片的对象
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        //设置图片的背景颜色
        graphics.setColor(Color.CYAN);
        graphics.fillRect(0,0,80,20);
        //给图片写数据
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(null,Font.BOLD,20));
        graphics.drawString(makeRandom(),0,20);
        //告诉浏览器打开的方式
        resp.setContentType("image/jpeg");

        //网站存在缓存，不让浏览器缓存
        resp.setDateHeader("expires",-1);
        resp.setHeader("Cache-Control","no-cache");
        resp.setHeader("Pragma","no-cache");
        //把图片写给浏览器
        ImageIO.write(image,"jpeg",resp.getOutputStream());
    }

    private String makeRandom(){
        Random random = new Random();
        int num = random.nextInt(99999) ;
        StringBuffer numStr = new StringBuffer(num+"");
        for(int i = 0; i <5 - numStr.length();i++){
            numStr.append(i);
        }
        return numStr.toString();
    }
}
