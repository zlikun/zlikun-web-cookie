<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>javascript操作Cookie</title>
</head>
<body>

    <h3>javascript操作Cookie</h3>

    <script>

        // 读取Cookie
        // name=jackson; JSESSIONID=59aiu13n9uz11dm7yz7vbewlv
        console.log(document.cookie)

        // 封装取得单台Cookie函数
        function getCookie(name) {
            var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg))
                return decodeURI(arr[2]);
            else
                return null;
        }

        // jackson
        console.log(getCookie("name"))
        // 注意HttpOnly属性对js操作Cookie的影响
        console.log(getCookie("JSESSIONID"))

        // 写入Cookie
        // 其它使用默认值：domain = localhost ,path = /cookie ,Max-Age = Session
        document.cookie = "name=jackson" ;
        document.cookie = "name=kevin" ;        // 重新赋值将更新

        // 封装写入Cookie函数
        function setCookie(name, value ,sec) {
            var exp = new Date();
            exp.setTime(exp.getTime() + (sec || 0) * 1000);
            // 这里的时间采用格林威治时间
            document.cookie = name + "=" + encodeURI(value) + ";expires=" + exp.toGMTString();
        }

        // 设置有效期为3分钟，中文字符将被编码
        setCookie("books" ,"货币战争;国富论" ,180)
        // 如果时间设置为0，Cookie不会被设置，已有Cookie将被删除
        setCookie("target" ,"http://localhost/cookie/display.jsp" ,30)
        setCookie("name" ,"zlikun" ,0)

        // 封装删除Cookie函数
        function delCookie(name) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval = getCookie(name);
            if (cval != null)
                document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
        }

        delCookie("author")

    </script>

</body>
</html>
