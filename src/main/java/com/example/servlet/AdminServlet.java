package com.example.servlet;

import com.example.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/admin/dashboard")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();

            // 总座位数
            int totalSeats = 0;
            PreparedStatement ps1 = conn.prepareStatement("SELECT COUNT(*) FROM seat");
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) totalSeats = rs1.getInt(1);
            rs1.close();
            ps1.close();

            // 已预约座位数
            int usedSeats = 0;
            PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) FROM seat WHERE status = 1");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) usedSeats = rs2.getInt(1);
            rs2.close();
            ps2.close();

            // 今日预约数
            int todayReservations = 0;
            PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) FROM reservation WHERE reserve_date = CURDATE()");
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) todayReservations = rs3.getInt(1);
            rs3.close();
            ps3.close();

            // 总用户数
            int totalUsers = 0;
            PreparedStatement ps4 = conn.prepareStatement("SELECT COUNT(*) FROM user WHERE role = 'user'");
            ResultSet rs4 = ps4.executeQuery();
            if (rs4.next()) totalUsers = rs4.getInt(1);
            rs4.close();
            ps4.close();

            req.setAttribute("totalSeats", totalSeats);
            req.setAttribute("usedSeats", usedSeats);
            req.setAttribute("todayReservations", todayReservations);
            req.setAttribute("totalUsers", totalUsers);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DBUtil.close(conn, null, null);
        }

        req.getRequestDispatcher("/admin/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
