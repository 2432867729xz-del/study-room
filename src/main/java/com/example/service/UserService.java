package com.example.service;

import com.example.bean.User;
import com.example.dao.UserDao;

public class UserService {
    private UserDao userDao = new UserDao();

    // 登录校验，返回null表示用户名或密码错
    public User login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // 注册，返回提示信息
    public String register(User user) {
        // 先查用户名是否已存在
        if (userDao.findByUsername(user.getUsername()) != null) {
            return "用户名已存在";
        }
        if (userDao.add(user)) {
            return "注册成功";
        }
        return "注册失败，请重试";
    }
}
