package com.example.dao;

import com.example.bean.Seat;
import com.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDao {

    // 查询所有座位（学生端列表、管理员端列表共用）
    public List<Seat> findAll() {
        List<Seat> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM seat ORDER BY id");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setId(rs.getInt("id"));
                seat.setRoom(rs.getString("room"));
                seat.setSeatNo(rs.getString("seat_no"));
                seat.setStatus(rs.getInt("status"));
                list.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // 管理员新增座位（新增时状态默认0=空闲）
    public boolean add(Seat seat) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("INSERT INTO seat (room, seat_no, status) VALUES (?, ?, 0)");
            pstmt.setString(1, seat.getRoom());
            pstmt.setString(2, seat.getSeatNo());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
        return false;
    }

    // 管理员删除座位
    public boolean deleteById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("DELETE FROM seat WHERE id = ?");
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
        return false;
    }

    // 修改座位状态：0=空闲，1=已预约（同学C的预约/取消/签到会调用这个方法）
    public boolean updateStatus(int seatId, int status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("UPDATE seat SET status = ? WHERE id = ?");
            pstmt.setInt(1, status);
            pstmt.setInt(2, seatId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
        return false;
    }
}
