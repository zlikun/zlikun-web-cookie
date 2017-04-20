<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>jQuery操作Cookie</title>
</head>
<body>

    <h3>jQuery操作Cookie</h3>

    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>

    <script>

        // 添加会话Cookie
        $.cookie('author', 'zlikun');
        // 添加Cookie，并指定相关属性
        // 有效时间为7天，path为/cookie
        $.cookie('SID', 'xxxxxx', { expires: 7 ,path: '/cookie/jquery-operate.jsp' });

        // 读取Cookie
        // xxxxxx
        console.log($.cookie("SID"))

        // 删除Cookie
        $.cookie('SID', null , { expires: -1 });
        // 删除带有效path的Cookie
//        $.cookie('SID', null ,{ expires: -1 ,path: '/cookie' });

        // 部分可选属性列表
        // expires: 365 ，单位：天
        // path: '/'
        // domain: '.zlikun.com'
        // secure: true ，默认值：false。如果为true，cookie的传输需要使用安全协议（HTTPS）
        // raw: true ，默认值：false，默认情况下，读取和写入 cookie 的时候自动进行编码和解码（使用encodeURIComponent 编码，decodeURIComponent 解码）。要关闭这个功能设置 raw: true 即可。

    </script>

</body>
</html>
