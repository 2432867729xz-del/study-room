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
    <title>学生首页 - 自习室预约系统</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: "Microsoft YaHei"; background: #f0f2f5; }
        .header { background: #fff; padding: 0 30px; height: 60px; display: flex; align-items: center; justify-content: space-between; box-shadow: 0 1px 4px rgba(0,21,41,.08); }
        .header h1 { font-size: 18px; color: #333; }
        .header .user-info { font-size: 14px; color: #666; }
        .header .user-info a { color: #ff4d4f; text-decoration: none; margin-left: 15px; }
        .container { max-width: 800px; margin: 50px auto; padding: 0 20px; }
        .welcome { text-align: center; margin-bottom: 40px; }
        .welcome h2 { color: #333; font-size: 24px; }
        .welcome p { color: #999; margin-top: 10px; }
        .nav-cards { display: flex; gap: 25px; flex-wrap: wrap; justify-content: center; }
        .nav-card { flex: 1; min-width: 250px; max-width: 350px; background: #fff; border-radius: 8px; padding: 40px 30px; text-align: center; box-shadow: 0 2px 8px rgba(0,0,0,.06); text-decoration: none; color: #333; transition: transform .2s; }
        .nav-card:hover { transform: translateY(-3px); box-shadow: 0 4px 12px rgba(0,0,0,.1); }
        .nav-card .icon { font-size: 48px; margin-bottom: 20px; }
        .nav-card .title { font-size: 18px; font-weight: bold; }
        .nav-card .desc { font-size: 14px; color: #999; margin-top: 10px; line-height: 1.6; }
    </style>
</head>
<body>
<div class="header">
    <h1>自习室座位预约系统</h1>
    <div class="user-info">
        欢迎，<%= loginUser.getName() %>
        <a href="<%= request.getContextPath() %>/logout">退出登录</a>
    </div>
</div>
<div class="container">
    <div class="welcome">
        <h2>你好，<%= loginUser.getName() %></h2>
        <p>请选择你要进行的操作</p>
    </div>
    <div class="nav-cards">
        <a class="nav-card" href="<%= request.getContextPath() %>/student/seat_list.jsp">
            <div class="icon">&#128188;</div>
            <div class="title">浏览座位</div>
            <div class="desc">查看自习室空位情况，选择座位进行预约</div>
        </a>
        <a class="nav-card" href="<%= request.getContextPath() %>/student/my_reservation.jsp">
            <div class="icon">&#128203;</div>
            <div class="title">我的预约</div>
            <div class="desc">查看我的预约记录，进行签到或取消操作</div>
        </a>
    </div>
</div>
</body>
</html>
