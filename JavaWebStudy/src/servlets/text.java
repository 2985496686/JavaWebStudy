package servlets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class text {
    public static void main(String[] args) {
        //获取文件的下载路径
        String realPath = "D:\\图片\\lufei.png";
        //获取文件名
        String fileName = realPath.substring(realPath.lastIndexOf("/") + 1);
        System.out.println(fileName);
        System.out.println("\\".length());
        try {
            FileInputStream fis = new FileInputStream("realPath");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
