package cn.itcast.servlet;

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

@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置图片
        int width = 100;
        int height = 50;

        //1.创建对象，在内存中保存图片
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //2.美化图片
        //2.1填充背景
        Graphics g = image.getGraphics();
        g.setColor(Color.pink);
        g.fillRect(0,0,width,height);

        //2.2画边框
        g.setColor(Color.blue);
        g.drawRect(0,0,width-1,height-1);

        //2.3写验证码
        String str = "ABCDEFGHIJKLMNOPQRSTUVWSYZabcdefghijklmnopqrstuvwsyz0123456789";
        Random ran = new Random();

        //创建一个容器，用来存储ch
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 1;i<=4;i++){
            int index = ran.nextInt(str.length());
            char ch = str.charAt(index);
            //将ch添加到容器中
            stringBuilder.append(ch);
            g.drawString(ch+"",width/5*i,height/2);
        }
        //将ch转化成字符串
        String s = stringBuilder.toString();
        //用request保存字符串,传给验证码
        req.getSession().setAttribute("checkCode_session",s);

        //2.4画干扰线
        g.setColor(Color.green);
        for(int i=0;i<=10;i++){
            int x1 = ran.nextInt(width);
            int x2 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int y2 = ran.nextInt(height);
            g.drawLine(x1,y1,x2,y2);
        }

        /*g.drawString("A",20,25);
        g.drawString("b",40,25);
        g.drawString("d",60,25);
        g.drawString("W",80,25);*/

        //3.将图片输出到页面上
        ImageIO.write(image,"jpg",resp.getOutputStream());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
