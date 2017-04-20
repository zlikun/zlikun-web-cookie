package com.zlikun.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther zlikun <zlikun-dev@hotmail.com>
 * @date 2017/4/20 18:41
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class) ;

    /**
     * 响应登录视图
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req ,resp);
    }

    /**
     * 处理登录请求
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username") ;
        String password = req.getParameter("password") ;

        log.info("执行登录：{} / {}" ,username ,password);

        if(username == null || password == null) {
            req.setAttribute("message" ,"用户名或密码为空!");
            req.getRequestDispatcher("/WEB-INF/jsp/login-error.jsp").forward(req ,resp);
            return ;
        }

        // 假设帐号、密码相同，即算认证通过，仅供测试
        if(!username.equals(password)) {
            req.setAttribute("message" ,"用户名或密码不正确!");
            req.getRequestDispatcher("/WEB-INF/jsp/login-error.jsp").forward(req ,resp);
            return ;
        }

        // 先注销session，后面将重新创建session，从页实现登录前后sessionid发生改变
        req.getSession().invalidate();

        // 将用户信息写入session
        req.getSession(true).setAttribute("login_username" ,username);

        // 重定向到首页
        resp.sendRedirect("/");
    }

}
