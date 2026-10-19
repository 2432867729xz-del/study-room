<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>自习室预约系统 - 登录</title>
    <style>
        body { font-family: "Microsoft YaHei"; background: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-box { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 350px; }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; color: #666; }
        input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .btn { width: 100%; padding: 12px; background: #1890ff; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        .btn:hover { background: #40a9ff; }
        .error { color: #ff4d4f; text-align: center; margin-bottom: 15px; }
        .msg { color: #52c41a; text-align: center; margin-bottom: 15px; }
        .link { text-align: center; margin-top: 15px; }
        .link a { color: #1890ff; text-decoration: none; }
    </style>
</head>
<body>
<div class="login-box">
    <h2>自习室座位预约系统</h2>
    <% if (request.getParameter("msg") != null) { %>
    <div class="msg"><%= request.getParameter("msg") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form action="login" method="post">
        <div class="form-group">
            <label>账号</label>
            <input type="text" name="username" required placeholder="请输入账号">
        </div>
        <div class="form-group">
            <label>密码</label>
            <input type="password" name="password" required placeholder="请输入密码">
        </div>
        <button type="submit" class="btn">登 录</button>
    </form>
    <div class="link">还没有账号？<a href="register.jsp">立即注册</a></div>
</div>
</body>
</html>
