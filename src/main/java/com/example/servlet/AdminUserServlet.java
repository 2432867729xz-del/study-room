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
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Map<String, Object>> users = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT u.id, u.username, u.name, u.role, " +
                    "(SELECT COUNT(*) FROM reservation r WHERE r.user_id = u.id) AS reserve_count " +
                    "FROM user u ORDER BY u.id");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("username", rs.getString("username"));
                row.put("name", rs.getString("name"));
                row.put("role", rs.getString("role"));
                row.put("reserveCount", rs.getInt("reserve_count"));
                users.add(row);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, null, null);
        }
        req.setAttribute("users", users);
        req.getRequestDispatcher("/admin/user_list.jsp").forward(req, resp);
    }
}
