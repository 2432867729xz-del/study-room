package com.example.dao;

import com.example.bean.Reservation;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 预约签到模块 - DAO层（同学C）
 * 状态约定：status 0=已预约 1=已签到 2=已取消
 */
public class ReservationDao {

    // 创建预约
    public boolean add(Reservation r) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(
                    "INSERT INTO reservation (user_id, seat_id, reserve_date, time_slot, status) VALUES (?, ?, ?, ?, 0)");
            pstmt.setInt(1, r.getUserId());
            pstmt.setInt(2, r.getSeatId());
            pstmt.setDate(3, r.getReserveDate());
            pstmt.setString(4, r.getTimeSlot());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
        return false;
    }

    // 取消预约：只能取消自己的、且状态为已预约的记录
    public boolean cancelById(int id, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(
                    "UPDATE reservation SET status = 2 WHERE id = ? AND user_id = ? AND status = 0");
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
        return false;
    }

    // 签到：只能签自己的、且状态为已预约的记录
    public boolean signIn(int id, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(
                    "UPDATE reservation SET status = 1 WHERE id = ? AND user_id = ? AND status = 0");
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
        return false;
    }

    // 按ID查预约（带用户、座位关联信息）
    public Reservation findById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(
                    "SELECT r.*, u.username, u.name, s.seat_no, s.room " +
                    "FROM reservation r " +
                    "JOIN user u ON r.user_id = u.id " +
                    "JOIN seat s ON r.seat_id = s.id " +
                    "WHERE r.id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    // 我的预约列表
    public List<Reservation> findMyReservations(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Reservation> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(
                    "SELECT r.*, u.username, u.name, s.seat_no, s.room " +
                    "FROM reservation r " +
                    "JOIN user u ON r.user_id = u.id " +
                    "JOIN seat s ON r.seat_id = s.id " +
                    "WHERE r.user_id = ? " +
                    "ORDER BY r.reserve_date DESC, r.id DESC");
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // 所有预约列表（管理员端用）
    public List<Reservation> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Reservation> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(
                    "SELECT r.*, u.username, u.name, s.seat_no, s.room " +
                    "FROM reservation r " +
                    "JOIN user u ON r.user_id = u.id " +
                    "JOIN seat s ON r.seat_id = s.id " +
                    "ORDER BY r.id DESC");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // 查某座位某天某时段是否已被预约（状态0/1都算占用，已取消的不算）
    public Reservation findTodayReserved(int seatId, String date, String timeSlot) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(
                    "SELECT r.*, u.username, u.name, s.seat_no, s.room " +
                    "FROM reservation r " +
                    "JOIN user u ON r.user_id = u.id " +
                    "JOIN seat s ON r.seat_id = s.id " +
                    "WHERE r.seat_id = ? AND r.reserve_date = ? AND r.time_slot = ? AND r.status IN (0, 1) " +
                    "LIMIT 1");
            pstmt.setInt(1, seatId);
            pstmt.setString(2, date);
            pstmt.setString(3, timeSlot);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    // 结果集行 -> 实体（关联字段一起封装）
    private Reservation mapRow(ResultSet rs) throws SQLException {
        Reservation r = new Reservation();
        r.setId(rs.getInt("id"));
        r.setUserId(rs.getInt("user_id"));
        r.setSeatId(rs.getInt("seat_id"));
        r.setReserveDate(rs.getDate("reserve_date"));
        r.setTimeSlot(rs.getString("time_slot"));
        r.setStatus(rs.getInt("status"));
        r.setCreateTime(rs.getString("create_time"));
        r.setUsername(rs.getString("username"));
        r.setName(rs.getString("name"));
        r.setSeatNo(rs.getString("seat_no"));
        r.setRoom(rs.getString("room"));
        return r;
    }
}
