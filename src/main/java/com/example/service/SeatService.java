package com.example.service;

import com.example.bean.Seat;
import com.example.dao.SeatDao;

import java.util.List;

public class SeatService {

    private SeatDao seatDao = new SeatDao();

    // 查询所有座位
    public List<Seat> findAll() {
        return seatDao.findAll();
    }

    // 管理员新增座位
    public boolean add(Seat seat) {
        return seatDao.add(seat);
    }

    // 管理员删除座位
    public boolean deleteById(int id) {
        return seatDao.deleteById(id);
    }

    // 修改座位状态（0=空闲，1=已预约）——同学C的预约模块会调用
    public boolean updateStatus(int seatId, int status) {
        return seatDao.updateStatus(seatId, status);
    }

    // 检查该座位是否有预约记录（删除前调用）
    public boolean hasReservation(int seatId) {
        return seatDao.hasReservation(seatId);
    }

    // 检查座位号是否已存在（新增前调用）
    public boolean existsBySeatNo(String seatNo) {
        return seatDao.existsBySeatNo(seatNo);
    }
}
