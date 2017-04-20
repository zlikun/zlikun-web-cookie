<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>显示服务端接收到的Cookie信息</title>
</head>
<body>

    <%

        // 浏览器GET请求
        // GET http://localhost/cookie/display.jsp
        // 使用CURL请求，指定消息头
        // curl -i -X GET http://localhost/cookie/display.jsp --header 'Cookie: JSESSIONID=59aiu13n9uz11dm7yz7vbewlv; author=zlikun'

        final String enc = "utf-8";

        // 获取Cookie列表
        Cookie [] cookies = request.getCookies() ;

        // 遍历输出Cookie列表
        if (cookies != null && cookies.length > 0) {
            out.println("<ul>") ;
            for(Cookie cookie : cookies) {
                out.println("<li>" + cookie.getName() + "</li>");
            }
            out.println("</ul>") ;
        } else {
            out.println("请求消息头中没有Cookie信息!");
        }

    %>

</body>
</html>
