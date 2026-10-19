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
    <a href="${pageContext.request.contextPath}/admin/dashboard">返回首页</a> |
    <a href="${pageContext.request.contextPath}/logout">退出登录</a></p>

<%-- 操作结果提示：新增/删除后显示成功或失败 --%>
<c:if test="${not empty msg}">
    <p style="color:${msg.contains('成功') ? 'green' : 'red'}; font-weight: bold;">${msg}</p>
</c:if>

<%-- 空座位列表提示 --%>
<c:if test="${empty seatList}">
    <p style="color:#888;">暂无座位，请使用上方表单添加。</p>
</c:if>

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
                <%-- 删除必须用POST表单（修改数据库操作），GET链接不会触发doPost --%>
                <form action="${pageContext.request.contextPath}/seat/delete" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${seat.id}">
                    <button type="submit" class="btn-del" onclick="return confirm('确定删除座位 ${seat.seatNo} 吗？')">删除</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
