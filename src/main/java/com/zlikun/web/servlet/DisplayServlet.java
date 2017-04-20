package com.zlikun.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @auther zlikun <zlikun-dev@hotmail.com>
 * @date 2017/4/18 11:41
 */
@WebServlet({"/display" ,"/display/*"})
public class DisplayServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DisplayServlet.class) ;
    private final String enc = "utf-8";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");

        // 获取Cookie信息
        Cookie[] cookies = req.getCookies() ;
        if (cookies != null && cookies.length > 0) {
            for(Cookie cookie : cookies) {
                // 获取Cookie中，使用URLDecoder解码，避免中文乱码
                // 注意decode方法不支持NULL值
                // JSESSIONID : 1cg1x61jwn4gf1einyu8pf8jfm ,null ,null ,-1 ,null
                // email : xxx@zlikun.com ,null ,null ,-1 ,null
                // 由author设置path=/display，所以访问/时，该Cookie不会发送到服务器、访问/display时则会发送，并且/display/目录下的请求共享该Cookie
                // author : 仅供测试 ,null ,null ,-1 ,null
                log.info("{} : {} ,{} ,{} ,{} ,{}" ,cookie.getName()
                        ,URLDecoder.decode(cookie.getValue() , enc) ,cookie.getDomain() ,cookie.getPath() ,cookie.getMaxAge()
                        ,cookie.getComment() != null ? URLDecoder.decode(cookie.getComment() , enc) : null);
            }
        } else {
            log.info("请求没有Cookie信息!");
        }

        // Set-Cookie
        // Set-Cookie: JSESSIONID=tjwp3kgnbfun1rger29fnprbe;Path=/

        // 获取表单参数
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String gender = req.getParameter("gender");

        // 向浏览器写入Cookie
        Cookie authorCookie = new Cookie("author" ,URLEncoder.encode("仅供测试" , enc)) ;
        authorCookie.setComment(URLEncoder.encode("仅供测试" , enc));    // 中文使用URLEncoder编码
//        authorCookie.setDomain(".zlikun.com");  // 默认当前域名
        authorCookie.setMaxAge(120);       // 过期时间，单位：秒，值为0表示删除Cookie、负数表示会话Cookie，关闭浏览器时失效
        authorCookie.setPath(req.getRequestURI());    // PATH属性，默认当前请求URL的PATH
        authorCookie.setSecure(false);    // 设置是否只有HTTPS下才写Cookie
        // Set-Cookie: author="%E4%BB%85%E4%BE%9B%E6%B5%8B%E8%AF%95";Comment="%E4%BB%85%E4%BE%9B%E6%B5%8B%E8%AF%95";Expires=Tue, 18-Apr-2017 11:42:26 GMT
        authorCookie.setHttpOnly(true);
        resp.addCookie(authorCookie);

        // 全部使用默认属性
        Cookie emailCookie = new Cookie("email" ,"xxx@zlikun.com") ;
        // Set-Cookie: email=xxx@zlikun.com
        resp.addCookie(emailCookie);

        // Cookie值是JSON
        Cookie jsonCookie = new Cookie("user" ,"{\"name\":\"zlikun\",\"age\":\"120\",\"gender\":\"MALE\"}") ;
        jsonCookie.setSecure(true);
        // Set-Cookie: user="{\"name\":\"zlikun\",\"age\":\"120\",\"gender\":\"MALE\"}"
        resp.addCookie(jsonCookie);

        // 使用HTTP消息头方式直接写入Cookie，使用变通方式实现"HttpOnly"属性
        // Set-Cookie: rememberMe=true;HttpOnly=true
        resp.addHeader("Set-Cookie" ,"rememberMe=true;HttpOnly=true");

        resp.getWriter().print(String.format("name = %s ,email = %s ,gender = %s", name, email, gender == null ? "未知" : (gender.equals("-1") ? "女" : "男")));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.sendRedirect("/");
        String uri = req.getRequestURI() ;
        // /display     -> redirect /
        if(uri.equals("/display") || uri.equals("/display/")) {
            req.getRequestDispatcher("/").forward(req ,resp);
            return ;
        }
        // /display/*   -> out  uri
        resp.getWriter().print(uri) ;
    }
}
