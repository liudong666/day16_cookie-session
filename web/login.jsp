<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <script>
        //帮定点击图片就更换
        window.onload = function () {
            //获取change对象
            // 绑定点击事件
            document.getElementById("change").onclick = function () {
                //添加时间戳
                this.src = "day16/checkCodeServlet?time="+new Date().getTime();
            }

        }
    </script>
    <style>
        div{
            color: red;
        }
    </style>
</head>
<body>
<form action="/day16/loginServlet" method="">
    <table>
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username" placeholder="请输入用户名"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="password" name="password" placeholder="请输入密码"></td>
        </tr>
        <tr>
            <td>验证码</td>
            <td><input type="text" name="checkCode"></td>
        </tr>
        <tr>
            <td id="change" colspan="2"><img src="day16/checkCodeServlet"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="登录"></td>
        </tr>
    </table>

    <div><%=request.getAttribute("error") == null ? "" : request.getAttribute("error") %></div>
    <div><%=request.getAttribute("login_error") == null ? "" : request.getAttribute("login_error")%></div>



</form>
</body>
</html>
