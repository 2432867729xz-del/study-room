-- 校园自习室座位预约与签到系统 - 数据库脚本
SET NAMES utf8mb4;
CREATE DATABASE IF NOT EXISTS study_room DEFAULT CHARACTER SET utf8mb4;
USE study_room;

-- 用户表（区分普通用户user和管理员admin）
CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '登录账号',
    password VARCHAR(50) NOT NULL COMMENT '登录密码',
    name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    role VARCHAR(20) NOT NULL DEFAULT 'user' COMMENT '角色：user普通用户,admin管理员'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 座位表
CREATE TABLE IF NOT EXISTS seat (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room VARCHAR(50) NOT NULL COMMENT '自习室区域，如A区/B区',
    seat_no VARCHAR(20) NOT NULL COMMENT '座位编号，如A001',
    status INT DEFAULT 0 COMMENT '状态：0空闲 1已被预约'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 预约记录表
CREATE TABLE IF NOT EXISTS reservation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL COMMENT '预约用户ID',
    seat_id INT NOT NULL COMMENT '座位ID',
    reserve_date DATE NOT NULL COMMENT '预约日期',
    time_slot VARCHAR(20) NOT NULL COMMENT '时段：上午/下午/晚上',
    status INT DEFAULT 0 COMMENT '状态：0已预约 1已签到 2已取消',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '预约时间',
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (seat_id) REFERENCES seat(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入初始数据：管理员账号 admin/admin123
INSERT INTO user (username, password, name, role) VALUES
('admin', 'admin123', '系统管理员', 'admin'),
('zhangsan', '123456', '张三', 'user');

-- 插入示例座位：A区10个座位，B区10个座位
INSERT INTO seat (room, seat_no, status) VALUES
('A区', 'A001', 0), ('A区', 'A002', 0), ('A区', 'A003', 0),
('A区', 'A004', 0), ('A区', 'A005', 0), ('A区', 'A006', 0),
('A区', 'A007', 0), ('A区', 'A008', 0), ('A区', 'A009', 0),
('A区', 'A010', 0),
('B区', 'B001', 0), ('B区', 'B002', 0), ('B区', 'B003', 0),
('B区', 'B004', 0), ('B区', 'B005', 0), ('B区', 'B006', 0),
('B区', 'B007', 0), ('B区', 'B008', 0), ('B区', 'B009', 0),
('B区', 'B010', 0);
