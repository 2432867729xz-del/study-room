<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>预约记录 - 管理员</title>
    <style>
        body { font-family: "Microsoft YaHei", sans-serif; margin: 40px; background: #f0f2f5; }
        h2 { color: #333; }
        .top-bar { margin-bottom: 20px; }
        .top-bar a { color: #1890ff; text-decoration: none; margin-right: 15px; }
        table { border-collapse: collapse; width: 900px; background: #fff; }
        th, td { border: 1px solid #e8e8e8; padding: 10px 12px; text-align: center; }
        th { background: #fafafa; }
        .st0 { color: #fa8c16; font-weight: bold; }
        .st1 { color: #52c41a; font-weight: bold; }
        .st2 { color: #999; }
    </style>
</head>
<body>
<div class="top-bar">
    <a href="${pageContext.request.contextPath}/admin/dashboard">&larr; 返回后台首页</a>
    <a href="${pageContext.request.contextPath}/logout">退出登录</a>
</div>
<h2>全部预约记录</h2>
<table>
    <tr>
        <th>ID</th>
        <th>用户</th>
        <th>姓名</th>
        <th>区域</th>
        <th>座位号</th>
        <th>预约日期</th>
        <th>时段</th>
        <th>状态</th>
    </tr>
    <c:forEach items="${reservations}" var="r">
    <tr>
        <td>${r.id}</td>
        <td>${r.username}</td>
        <td>${r.name}</td>
        <td>${r.room}</td>
        <td>${r.seatNo}</td>
        <td>${r.reserveDate}</td>
        <td>${r.timeSlot}</td>
        <td>
            <c:choose>
                <c:when test="${r.status == 0}"><span class="st0">已预约</span></c:when>
                <c:when test="${r.status == 1}"><span class="st1">已签到</span></c:when>
                <c:otherwise><span class="st2">已取消</span></c:otherwise>
            </c:choose>
        </td>
    </tr>
    </c:forEach>
    <c:if test="${empty reservations}">
    <tr><td colspan="9" style="color:#999;padding:30px;">暂无预约记录</td></tr>
    </c:if>
</table>
</body>
</html>
