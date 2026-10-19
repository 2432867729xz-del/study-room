package com.example.servlet;

import com.example.bean.Seat;
import com.example.service.SeatService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
            // 管理员端座位管理页
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

        if ("/add".equals(path)) {
            // 加座位：取 room、seatNo 参数
            String room = req.getParameter("room");
            String seatNo = req.getParameter("seatNo");
            Seat seat = new Seat();
            seat.setRoom(room);
            seat.setSeatNo(seatNo);
            seatService.add(seat);
        } else if ("/delete".equals(path)) {
            // 删座位：取 id 参数
            int id = Integer.parseInt(req.getParameter("id"));
            seatService.deleteById(id);
        }

        // 操作完回到管理员座位管理页
        resp.sendRedirect(req.getContextPath() + "/seat/admin");
    }
}
