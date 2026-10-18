package com.example.bean;

import java.sql.Date;

public class Reservation {
    private int id;
    private int userId;
    private int seatId;
    private Date reserveDate;
    private String timeSlot;
    private int status;
    private String createTime;

    // 关联显示用的额外字段
    private String username;
    private String name;
    private String seatNo;
    private String room;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getSeatId() { return seatId; }
    public void setSeatId(int seatId) { this.seatId = seatId; }
    public Date getReserveDate() { return reserveDate; }
    public void setReserveDate(Date reserveDate) { this.reserveDate = reserveDate; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
}

