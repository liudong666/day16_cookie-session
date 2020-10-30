package cn.itcast.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置文件类型以及编码问题
        response.setContentType("text/jsp;charset=utf-8");
        //获取request参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String checkCode = request.getParameter("checkCode");

        //获取session
        HttpSession session = request.getSession();

        //获取服务器验证码
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        request.getSession().removeAttribute("checkCode_session");
        //2.比较验证码是否正确
        if(checkCode_session!=null && checkCode_session.equalsIgnoreCase(checkCode)){
            //验证码相等,返回验证码相等消息
            //request.setAttribute("corret","验证码正确");
            //并判断用户名与密码
            if("zhangsan".equals(username)&&"123".equals(password)){
                //全相同,跳转页面到successServlet
                //保存用户信息
                request.setAttribute("user",username);
                //重定向
                response.sendRedirect(request.getContextPath()+"/success.jsp");

            }else{
                //用户名或密码有误
                request.setAttribute("login_error","用户名或密码有误");
                //跳转到login.jsp页面
                request.getRequestDispatcher("/day16/login.jsp").forward(request,response);
            }


        }else{
            //验证码错误
            //将错误信息保存
            request.setAttribute("error","验证码有误");
            //跳转页面回login.jsp,请求转发
            request.getRequestDispatcher("/day/login.jsp").forward(request,response);

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
