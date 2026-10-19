<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>座位列表 - 学生端</title>
    <style>
        body { font-family: "Microsoft YaHei", sans-serif; margin: 40px; }
        table { border-collapse: collapse; width: 600px; }
        th, td { border: 1px solid #ccc; padding: 8px 12px; text-align: center; }
        th { background-color: #f0f0f0; }
        .free { color: green; font-weight: bold; }
        .booked { color: red; font-weight: bold; }
        a { color: #007bff; text-decoration: none; }
    </style>
</head>
<body>
<h2>自习室座位列表</h2>
<p>当前用户：${sessionScope.loginUser.name}（${sessionScope.loginUser.username}）
    <a href="${pageContext.request.contextPath}/logout">退出登录</a></p>

<%-- 操作结果提示 --%>
<c:if test="${not empty msg}">
    <p style="color:${msg.contains('成功') ? 'green' : 'red'}; font-weight: bold;">${msg}</p>
</c:if>

<%-- 空座位列表提示 --%>
<c:if test="${empty seatList}">
    <p style="color:#888;">暂无座位，请联系管理员添加。</p>
</c:if>

<table>
    <tr>
        <th>座位编号</th>
        <th>区域</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${seatList}" var="seat">
        <tr>
            <td>${seat.seatNo}</td>
            <td>${seat.room}</td>
            <td class="${seat.status == 0 ? 'free' : 'booked'}">
                ${seat.status == 0 ? '空闲' : '已预约'}
            </td>
            <td>
                <c:if test="${seat.status == 0}">
                    <%-- 预约接口由同学C的 ReservationServlet 提供，参数传座位id --%>
                    <a href="${pageContext.request.contextPath}/reservation/reserve?seatId=${seat.id}">预约</a>
                </c:if>
                <c:if test="${seat.status != 0}">—</c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
