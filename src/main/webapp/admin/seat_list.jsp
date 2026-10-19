<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>座位管理 - 管理员端</title>
    <style>
        body { font-family: "Microsoft YaHei", sans-serif; margin: 40px; }
        table { border-collapse: collapse; width: 650px; }
        th, td { border: 1px solid #ccc; padding: 8px 12px; text-align: center; }
        th { background-color: #f0f0f0; }
        .free { color: green; font-weight: bold; }
        .booked { color: red; font-weight: bold; }
        .add-form { margin-bottom: 20px; padding: 15px; background: #fafafa; border: 1px solid #eee; width: 400px; }
        .btn-del { color: red; text-decoration: none; }
    </style>
</head>
<body>
<h2>座位管理（管理员）</h2>
<p>当前用户：${sessionScope.loginUser.name}
    <a href="${pageContext.request.contextPath}/login.jsp">退出登录</a></p>

<%-- 加座位表单：POST 到 /seat/add --%>
<div class="add-form">
    <h3>新增座位</h3>
    <form action="${pageContext.request.contextPath}/seat/add" method="post">
        区域：<input type="text" name="room" placeholder="如 A区" required>
        座位号：<input type="text" name="seatNo" placeholder="如 A011" required>
        <button type="submit">添加</button>
    </form>
</div>

<table>
    <tr>
        <th>ID</th>
        <th>座位编号</th>
        <th>区域</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${seatList}" var="seat">
        <tr>
            <td>${seat.id}</td>
            <td>${seat.seatNo}</td>
            <td>${seat.room}</td>
            <td class="${seat.status == 0 ? 'free' : 'booked'}">
                ${seat.status == 0 ? '空闲' : '已预约'}
            </td>
            <td>
                <a class="btn-del" href="${pageContext.request.contextPath}/seat/delete?id=${seat.id}"
                   onclick="return confirm('确定删除座位 ${seat.seatNo} 吗？')">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
