package com.example.servlet;

import com.example.bean.Reservation;
import com.example.bean.Seat;
import com.example.bean.User;
import com.example.service.ReservationService;
import com.example.service.SeatService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

/**
 * 预约签到模块 - Servlet控制器（同学C）
 * 路由：
 *   /reservation/reserve  预约（参数：seatId、date=yyyy-MM-dd、timeSlot=上午/下午/晚上）
 *                         如果只传了 seatId（同学B座位列表的"预约"链接），会先转发到预约表单页选择日期时段
 *   /reservation/cancel   取消预约（参数：id）
 *   /reservation/signin   签到（参数：id）
 *   /reservation/my       我的预约列表（转发到 student/my_reservation.jsp）
 * 注意：用的是 jakarta.servlet 包，不是 javax！
 */
@WebServlet("/reservation/*")
public class ReservationServlet extends HttpServlet {

    private ReservationService reservationService = new ReservationService();
    private SeatService seatService = new SeatService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 未登录一律回登录页（同学D的Filter也会拦截，这里双保险）
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String path = req.getPathInfo();
        if ("/reserve".equals(path)) {
            reserve(req, resp, user);
        } else if ("/cancel".equals(path)) {
            cancel(req, resp, user);
        } else if ("/signin".equals(path)) {
            signin(req, resp, user);
        } else if ("/my".equals(path) || path == null) {
            my(req, resp, user);
        } else {
            resp.sendRedirect(req.getContextPath() + "/student/my_reservation.jsp");
        }
    }

    // 预约
    private void reserve(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        String dateParam = req.getParameter("date");
        String timeSlot = req.getParameter("timeSlot");

        // 同学B的座位列表"预约"链接只传了 seatId，此时先去预约表单页选日期和时段
        if (dateParam == null || dateParam.trim().isEmpty()
                || timeSlot == null || timeSlot.trim().isEmpty()) {
            int seatId;
            try {
                seatId = Integer.parseInt(req.getParameter("seatId"));
            } catch (Exception e) {
                redirectWithMsg(req, resp, "预约参数有误，请重新选择");
                return;
            }
            // 回显座位信息
            Seat seat = null;
            for (Seat s : seatService.findAll()) {
                if (s.getId() == seatId) {
                    seat = s;
                    break;
                }
            }
            req.setAttribute("seatId", seatId);
            req.setAttribute("seat", seat);
            req.getRequestDispatcher("/student/reserve_form.jsp").forward(req, resp);
            return;
        }

        int seatId;
        Date date;
        try {
            seatId = Integer.parseInt(req.getParameter("seatId"));
            date = Date.valueOf(dateParam);
        } catch (Exception e) {
            redirectWithMsg(req, resp, "预约参数有误，请重新选择");
            return;
        }
        Reservation r = new Reservation();
        r.setUserId(user.getId());
        r.setSeatId(seatId);
        r.setReserveDate(date);
        r.setTimeSlot(timeSlot.trim());
        String error = reservationService.reserve(r);
        redirectWithMsg(req, resp, error == null ? "预约成功" : error);
    }

    // 取消预约
    private void cancel(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            redirectWithMsg(req, resp, "参数有误");
            return;
        }
        String error = reservationService.cancel(id, user.getId());
        redirectWithMsg(req, resp, error == null ? "取消成功" : error);
    }

    // 签到
    private void signin(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            redirectWithMsg(req, resp, "参数有误");
            return;
        }
        String error = reservationService.signIn(id, user.getId());
        redirectWithMsg(req, resp, error == null ? "签到成功" : error);
    }

    // 我的预约列表
    private void my(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        List<Reservation> list = reservationService.findMyReservations(user.getId());
        req.setAttribute("reservations", list);
        req.getRequestDispatcher("/student/my_reservation.jsp").forward(req, resp);
    }

    // 统一跳回我的预约页并带提示
    private void redirectWithMsg(HttpServletRequest req, HttpServletResponse resp, String msg) throws IOException {
        String encoded = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        resp.sendRedirect(req.getContextPath() + "/student/my_reservation.jsp?msg=" + encoded);
    }
}
