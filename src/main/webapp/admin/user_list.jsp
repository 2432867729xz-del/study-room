<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理 - 管理员</title>
    <style>
        body { font-family: "Microsoft YaHei", sans-serif; margin: 40px; background: #f0f2f5; }
        h2 { color: #333; }
        .top-bar { margin-bottom: 20px; }
        .top-bar a { color: #1890ff; text-decoration: none; margin-right: 15px; }
        table { border-collapse: collapse; width: 700px; background: #fff; }
        th, td { border: 1px solid #e8e8e8; padding: 10px 12px; text-align: center; }
        th { background: #fafafa; }
        .role-admin { color: #722ed1; font-weight: bold; }
        .role-user { color: #666; }
    </style>
</head>
<body>
<div class="top-bar">
    <a href="${pageContext.request.contextPath}/admin/dashboard">&larr; 返回后台首页</a>
    <a href="${pageContext.request.contextPath}/logout">退出登录</a>
</div>
<h2>注册用户列表</h2>
<table>
    <tr>
        <th>ID</th>
        <th>账号</th>
        <th>姓名</th>
        <th>角色</th>
        <th>预约次数</th>
    </tr>
    <c:forEach items="${users}" var="u">
    <tr>
        <td>${u.id}</td>
        <td>${u.username}</td>
        <td>${u.name}</td>
        <td>
            <c:choose>
                <c:when test="${u.role == 'admin'}"><span class="role-admin">管理员</span></c:when>
                <c:otherwise><span class="role-user">普通用户</span></c:otherwise>
            </c:choose>
        </td>
        <td>${u.reserveCount}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
