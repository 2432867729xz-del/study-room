package com.example.servlet;

import com.example.bean.Seat;
import com.example.service.SeatService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/seat/*")
public class SeatServlet extends HttpServlet {

    private SeatService seatService = new SeatService();

    // GET：查所有座位，学生端和管理员端共用（按路径区分转发页面）
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo(); // 例如 /list 或 /admin

        // 删除座位（JSP里用<a>链接发的是GET）
        if ("/delete".equals(path)) {
            int id = Integer.parseInt(req.getParameter("id"));
            seatService.deleteById(id);
            resp.sendRedirect(req.getContextPath() + "/seat/admin");
            return;
        }

        List<Seat> seatList = seatService.findAll();
        req.setAttribute("seatList", seatList);

        if (path != null && path.contains("admin")) {
            // 管理员端：读取上一次操作的结果提示（session中取一次就删除）
            HttpSession session = req.getSession();
            String msg = (String) session.getAttribute("msg");
            if (msg != null) {
                req.setAttribute("msg", msg);
                session.removeAttribute("msg");
            }
            req.getRequestDispatcher("/admin/seat_list.jsp").forward(req, resp);
        } else {
            // 学生端座位列表页（默认）
            req.getRequestDispatcher("/student/seat_list.jsp").forward(req, resp);
        }
    }

    // POST：管理员加座位 / 删座位
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        HttpSession session = req.getSession();

        if ("/add".equals(path)) {
            // ===== 管理员新增座位 =====
            String room = req.getParameter("room");
            String seatNo = req.getParameter("seatNo");

            // 校验1：区域和座位号不能为空
            if (room == null || seatNo == null || room.trim().isEmpty() || seatNo.trim().isEmpty()) {
                session.setAttribute("msg", "新增失败：区域和座位号不能为空");
            } else if (seatService.existsBySeatNo(seatNo.trim())) {
                // 校验2：座位号不能重复
                session.setAttribute("msg", "新增失败：座位号 " + seatNo.trim() + " 已存在");
            } else {
                Seat seat = new Seat();
                seat.setRoom(room.trim());
                seat.setSeatNo(seatNo.trim());
                boolean ok = seatService.add(seat);
                session.setAttribute("msg", ok ? "新增座位 " + seatNo.trim() + " 成功" : "新增失败：数据库异常");
            }
        } else if ("/delete".equals(path)) {
            // ===== 管理员删除座位 =====
            int id;
            try {
                id = Integer.parseInt(req.getParameter("id"));
            } catch (NumberFormatException e) {
                // 校验1：id参数不是合法数字
                session.setAttribute("msg", "删除失败：座位id参数无效");
                resp.sendRedirect(req.getContextPath() + "/seat/admin");
                return;
            }

            // 校验2：该座位已被预约时不允许删除（外键约束）
            if (seatService.hasReservation(id)) {
                session.setAttribute("msg", "删除失败：该座位已被预约，无法删除");
            } else {
                boolean ok = seatService.deleteById(id);
                session.setAttribute("msg", ok ? "删除成功" : "删除失败：数据库异常");
            }
        }

        // 操作完重定向回管理员座位管理页（防止表单重复提交），提示消息存session
        resp.sendRedirect(req.getContextPath() + "/seat/admin");
    }
}
