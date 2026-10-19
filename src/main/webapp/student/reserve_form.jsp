<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>自习室预约系统 - 选择预约日期时段</title>
    <style>
        body { font-family: "Microsoft YaHei"; background: #f0f2f5; margin: 0; padding: 20px; }
        .container { max-width: 480px; margin: 0 auto; background: white; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); padding: 30px; margin-top: 60px; }
        h2 { color: #333; margin-top: 0; }
        .seat-info { background: #fafafa; border: 1px solid #e8e8e8; border-radius: 4px; padding: 12px; margin-bottom: 20px; font-size: 16px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; color: #666; }
        input[type="date"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .slot-group label { display: inline-block; margin-right: 20px; color: #333; }
        .btn { width: 100%; padding: 12px; background: #1890ff; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        .btn:hover { background: #40a9ff; }
        .error { color: #ff4d4f; margin-bottom: 15px; }
        .back { margin-top: 15px; text-align: center; }
        .back a { color: #1890ff; text-decoration: none; }
    </style>
</head>
<body>
<div class="container">
    <h2>选择预约日期与时段</h2>
    <c:if test="${empty seat}">
        <div class="error">未找到该座位，请返回重试</div>
        <div class="back"><a href="${pageContext.request.contextPath}/student/seat_list.jsp">返回座位列表</a></div>
    </c:if>
    <c:if test="${not empty seat}">
        <div class="seat-info">座位：<strong>${seat.room} ${seat.seatNo}</strong></div>
        <form action="${pageContext.request.contextPath}/reservation/reserve" method="post">
            <input type="hidden" name="seatId" value="${seat.id}">
            <div class="form-group">
                <label>预约日期</label>
                <input type="date" name="date" required min="<%= java.time.LocalDate.now() %>">
            </div>
            <div class="form-group slot-group">
                <label>时段</label><br>
                <label><input type="radio" name="timeSlot" value="上午" checked> 上午</label>
                <label><input type="radio" name="timeSlot" value="下午"> 下午</label>
                <label><input type="radio" name="timeSlot" value="晚上"> 晚上</label>
            </div>
            <button type="submit" class="btn">确认预约</button>
        </form>
        <div class="back"><a href="${pageContext.request.contextPath}/student/seat_list.jsp">返回座位列表</a></div>
    </c:if>
</div>
</body>
</html>
