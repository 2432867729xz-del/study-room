package com.example.servlet;

import com.example.bean.User;
import com.example.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);

        String result = userService.register(user);
        if ("注册成功".equals(result)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?msg=注册成功，请登录");
        } else {
            req.setAttribute("error", result);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
