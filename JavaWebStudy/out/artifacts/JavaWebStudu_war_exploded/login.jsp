<%--
  Created by IntelliJ IDEA.
  User: 123
  Date: 2022/7/20
  Time: 0:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="login" method="post">
    邮箱：<input type="email" name="email" value="${cookie.email.value}"/><br/>
    密码：<input type="password" name="password" value="${cookie.password.value}"><br>
    <input type="submit" value="登录">
</form>
</body>
</html>
