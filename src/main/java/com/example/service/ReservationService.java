package com.example.service;

import com.example.bean.Reservation;
import com.example.dao.ReservationDao;
import com.example.dao.SeatDao;

import java.sql.Date;
import java.util.List;

/**
 * 预约签到模块 - Service层（同学C）
 * 业务规则：
 * 1. 同一座位同一日期同一时段只能被一个人预约（状态0/1都算占用）
 * 2. 同一用户同一日期同一时段不能预约多个座位
 * 3. 预约成功后把座位状态改为已预约(1)，取消/签到后座位释放(0)
 * 4. 超过预约日期当天不能再签到（时段级时间窗可由小组后续补充）
 */
public class ReservationService {

    private ReservationDao reservationDao = new ReservationDao();
    private SeatDao seatDao = new SeatDao();

    // 预约：返回null表示成功，否则返回错误提示
    public String reserve(Reservation r) {
        // 0. 不能预约过去的日期
        Date today = new Date(System.currentTimeMillis());
        if (r.getReserveDate().before(today)) {
            return "不能预约过去的日期";
        }
        // 1. 该座位在该时段是否已被占用
        Reservation existed = reservationDao.findTodayReserved(
                r.getSeatId(), r.getReserveDate().toString(), r.getTimeSlot());
        if (existed != null) {
            return "该座位在此时间段已被预约，请选择其他座位";
        }
        // 2. 同一用户同一时段是否已有预约
        for (Reservation mine : reservationDao.findMyReservations(r.getUserId())) {
            if (mine.getStatus() != 2
                    && mine.getReserveDate().toString().equals(r.getReserveDate().toString())
                    && mine.getTimeSlot().equals(r.getTimeSlot())) {
                return "你在这个时段已有预约，请先取消再预约其他座位";
            }
        }
        // 3. 插入预约记录
        if (!reservationDao.add(r)) {
            return "预约失败，请重试";
        }
        // 4. 座位标记为已预约
        seatDao.updateStatus(r.getSeatId(), 1);
        return null;
    }

    // 取消预约：返回null表示成功，否则返回错误提示
    public String cancel(int id, int userId) {
        Reservation r = reservationDao.findById(id);
        if (r == null) {
            return "预约记录不存在";
        }
        if (r.getUserId() != userId) {
            return "只能取消自己的预约";
        }
        if (r.getStatus() == 1) {
            return "该预约已签到，不能取消";
        }
        if (r.getStatus() == 2) {
            return "该预约已取消";
        }
        if (reservationDao.cancelById(id, userId)) {
            // 座位释放为空闲
            seatDao.updateStatus(r.getSeatId(), 0);
            return null;
        }
        return "取消失败，请重试";
    }

    // 签到：返回null表示成功，否则返回错误提示
    public String signIn(int id, int userId) {
        Reservation r = reservationDao.findById(id);
        if (r == null) {
            return "预约记录不存在";
        }
        if (r.getUserId() != userId) {
            return "只能给自己的预约签到";
        }
        if (r.getStatus() == 1) {
            return "该预约已签到，请勿重复签到";
        }
        if (r.getStatus() == 2) {
            return "该预约已取消，不能签到";
        }
        // 超过预约日期当天视为迟到超时，不可签到
        Date today = new Date(System.currentTimeMillis());
        if (r.getReserveDate().before(today)) {
            return "已超过预约日期，无法签到";
        }
        if (reservationDao.signIn(id, userId)) {
            // 签到完成后该座位释放，供其他时段预约（该时段的占用由reservation记录保护）
            seatDao.updateStatus(r.getSeatId(), 0);
            return null;
        }
        return "签到失败，请重试";
    }

    // 我的预约列表
    public List<Reservation> findMyReservations(int userId) {
        return reservationDao.findMyReservations(userId);
    }

    // 所有预约列表（管理员端用）
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }
}
