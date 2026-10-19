package com.example.filter;

import com.example.bean.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();

        // 1. 放行公开资源：登录页、注册页、登录注册接口、静态资源
        if (uri.equals(contextPath + "/login.jsp")
                || uri.equals(contextPath + "/register.jsp")
                || uri.equals(contextPath + "/login")
                || uri.equals(contextPath + "/register")
                || uri.contains("/css/")
                || uri.contains("/js/")
                || uri.contains("/images/")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. 检查是否登录
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            resp.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        // 3. 管理员权限校验：访问 /admin/ 开头的路径必须是管理员
        if (uri.startsWith(contextPath + "/admin/")) {
            if (!"admin".equals(loginUser.getRole())) {
                resp.sendRedirect(contextPath + "/student/index.jsp");
                return;
            }
        }

        // 已登录且权限合法，放行
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
