package cn.itcast.cookie;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
    在服务器中判断是否有lastime的cookie,
    1.有，表示不是第一次访问
        1.响应数据：欢迎回来，你上次登录的时间是：2020年10月29日09:26:12
        2.更新cookie数据，lastime为2020年10月29日09:26:57
    2.没有，表示第一次访问
        1.相应数据：你好，欢迎你首次访问
        2.设置cookie，lastime=2020年10月29日09:28:07

 */
@WebServlet("/cookieTest")
public class CookieTest extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置数据体格式以及编码
        resp.setContentType("text/html;charset=utf-8");
        //1.获取cookie
        Cookie[] cookies = req.getCookies();
        Boolean flag = false;
        if(cookies != null && cookies.length>0){
            for (Cookie cookie : cookies) {
                //2.有cookie，判断是否为lastime
                String name = cookie.getName();
                if("lastime".equals(name)){
                    //3.响应数据,更新lastime数据
                    flag=true;//监测到lastime
                    Date data = new Date();
                    //设置时间格式
                    //问题出现500状态码浏览器不解析空格
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年mm月dd日 HH时mm分ss秒");
                    String format = simpleDateFormat.format(data);
                    //使用URL编码，保证不出错，接收的时在解码
                    String encode = URLEncoder.encode(format,"utf-8");
                    cookie.setValue(encode);
                    //设置cookie存活时间为一个月
                    cookie.setMaxAge(10);
                    //传递cookie
                    resp.addCookie(cookie);
                    //响应数据：欢迎你再次访问
                    String value = cookie.getValue();
                    String decode = URLDecoder.decode(value, "utf-8");
                    resp.getWriter().write("欢迎回来，你上次登录的时间是："+decode);
                    System.out.println("2222222222222222222");
                    break;
                }

            }
        }


        if(cookies == null|| cookies.length==0 || flag==false){
            //3.响应数据,更新lastime数据
            Date data = new Date();
            //设置时间格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年mm月dd日HH时mm分ss秒");
            String format = simpleDateFormat.format(data);
            String encode = URLEncoder.encode(format,"utf-8");

            //首次访问
            Cookie cookie = new Cookie("lastime",encode);
            //设置cookie存活时间为一个月
            cookie.setMaxAge(10);
            //传递cookie
            resp.addCookie(cookie);

            //响应数据
            resp.getWriter().write("你好，欢迎你首次访问!");
            System.out.println("111111111");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
