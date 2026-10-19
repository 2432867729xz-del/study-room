<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>自习室预约系统 - 我的预约</title>
    <style>
        body { font-family: "Microsoft YaHei"; background: #f0f2f5; margin: 0; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; background: white; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); padding: 30px; }
        h2 { color: #333; margin-top: 0; }
        .msg { color: #52c41a; margin-bottom: 15px; }
        .error { color: #ff4d4f; margin-bottom: 15px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #e8e8e8; padding: 10px; text-align: center; }
        th { background: #fafafa; color: #666; }
        .btn { display: inline-block; padding: 6px 16px; border-radius: 4px; text-decoration: none; font-size: 14px; margin: 0 4px; }
        .btn-signin { background: #1890ff; color: white; }
        .btn-signin:hover { background: #40a9ff; }
        .btn-cancel { background: #fff; color: #ff4d4f; border: 1px solid #ff4d4f; }
        .btn-cancel:hover { background: #fff1f0; }
        .status-0 { color: #1890ff; }
        .status-1 { color: #52c41a; }
        .status-2 { color: #999; }
        .empty { color: #999; text-align: center; padding: 30px 0; }
        .nav { margin-bottom: 15px; }
        .nav a { color: #1890ff; text-decoration: none; margin-right: 15px; }
    </style>
</head>
<body>
<div class="container">
    <div class="nav">
        <a href="${pageContext.request.contextPath}/student/index.jsp">首页</a>
        <a href="${pageContext.request.contextPath}/student/seat_list.jsp">座位预约</a>
    </div>
    <h2>我的预约</h2>
    <% if (request.getParameter("msg") != null) { %>
    <div class="msg"><%= request.getParameter("msg") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <c:if test="${empty reservations}">
        <div class="empty">暂无预约记录，去<a href="${pageContext.request.contextPath}/student/seat_list.jsp">座位列表</a>预约吧</div>
    </c:if>
    <c:if test="${not empty reservations}">
        <table>
            <tr>
                <th>预约日期</th>
                <th>座位</th>
                <th>时段</th>
                <th>状态</th>
                <th>预约时间</th>
                <th>操作</th>
            </tr>
            <c:forEach var="r" items="${reservations}">
                <tr>
                    <td>${r.reserveDate}</td>
                    <td>${r.room} ${r.seatNo}</td>
                    <td>${r.timeSlot}</td>
                    <td>
                        <c:choose>
                            <c:when test="${r.status == 0}"><span class="status-0">已预约</span></c:when>
                            <c:when test="${r.status == 1}"><span class="status-1">已签到</span></c:when>
                            <c:otherwise><span class="status-2">已取消</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${r.createTime}</td>
                    <td>
                        <c:if test="${r.status == 0}">
                            <a class="btn btn-signin"
                               href="${pageContext.request.contextPath}/reservation/signin?id=${r.id}"
                               onclick="return confirm('确认签到？')">签到</a>
                            <a class="btn btn-cancel"
                               href="${pageContext.request.contextPath}/reservation/cancel?id=${r.id}"
                               onclick="return confirm('确认取消该预约？')">取消</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
