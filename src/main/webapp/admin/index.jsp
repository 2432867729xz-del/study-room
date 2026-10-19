<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.bean.User" %>
<%
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>管理员后台 - 自习室预约系统</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: "Microsoft YaHei"; background: #f0f2f5; }
        .header { background: #fff; padding: 0 30px; height: 60px; display: flex; align-items: center; justify-content: space-between; box-shadow: 0 1px 4px rgba(0,21,41,.08); }
        .header h1 { font-size: 18px; color: #333; }
        .header .user-info { font-size: 14px; color: #666; }
        .header .user-info a { color: #ff4d4f; text-decoration: none; margin-left: 15px; }
        .container { max-width: 1000px; margin: 30px auto; padding: 0 20px; }
        .stats { display: flex; gap: 20px; margin-bottom: 30px; flex-wrap: wrap; }
        .stat-card { flex: 1; min-width: 200px; background: #fff; border-radius: 8px; padding: 25px; box-shadow: 0 2px 8px rgba(0,0,0,.06); }
        .stat-card .num { font-size: 36px; font-weight: bold; color: #1890ff; }
        .stat-card .label { font-size: 14px; color: #999; margin-top: 8px; }
        .stat-card.green .num { color: #52c41a; }
        .stat-card.orange .num { color: #fa8c16; }
        .stat-card.purple .num { color: #722ed1; }
        .nav-cards { display: flex; gap: 20px; flex-wrap: wrap; }
        .nav-card { flex: 1; min-width: 220px; background: #fff; border-radius: 8px; padding: 30px; text-align: center; box-shadow: 0 2px 8px rgba(0,0,0,.06); text-decoration: none; color: #333; transition: transform .2s; }
        .nav-card:hover { transform: translateY(-3px); box-shadow: 0 4px 12px rgba(0,0,0,.1); }
        .nav-card .icon { font-size: 40px; margin-bottom: 15px; }
        .nav-card .title { font-size: 16px; font-weight: bold; }
        .nav-card .desc { font-size: 13px; color: #999; margin-top: 8px; }
    </style>
</head>
<body>
<div class="header">
    <h1>自习室座位预约系统 - 管理员后台</h1>
    <div class="user-info">
        欢迎，<%= loginUser.getName() %>（管理员）
        <a href="<%= request.getContextPath() %>/logout">退出登录</a>
    </div>
</div>
<div class="container">
    <div class="stats">
        <div class="stat-card blue">
            <div class="num"><%= request.getAttribute("totalSeats") != null ? request.getAttribute("totalSeats") : 0 %></div>
            <div class="label">总座位数</div>
        </div>
        <div class="stat-card orange">
            <div class="num"><%= request.getAttribute("usedSeats") != null ? request.getAttribute("usedSeats") : 0 %></div>
            <div class="label">已预约座位</div>
        </div>
        <div class="stat-card green">
            <div class="num"><%= request.getAttribute("todayReservations") != null ? request.getAttribute("todayReservations") : 0 %></div>
            <div class="label">今日预约数</div>
        </div>
        <div class="stat-card purple">
            <div class="num"><%= request.getAttribute("totalUsers") != null ? request.getAttribute("totalUsers") : 0 %></div>
            <div class="label">注册用户数</div>
        </div>
    </div>

    <div class="nav-cards">
        <a class="nav-card" href="<%= request.getContextPath() %>/seat/admin">
            <div class="icon">&#128188;</div>
            <div class="title">座位管理</div>
            <div class="desc">查看、新增、删除自习室座位</div>
        </a>
        <a class="nav-card" href="<%= request.getContextPath() %>/reservation/admin">
            <div class="icon">&#128203;</div>
            <div class="title">预约记录</div>
            <div class="desc">查看所有用户的预约和签到记录</div>
        </a>
    </div>
</div>
</body>
</html>
