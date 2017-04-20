# zlikun-web-cookie
Cookie相关知识梳理、学习

## Cookie基础知识

#### Cookie属性
- name cookie的名称
- value cookie的值
- domain 可以访问此cookie的域名
- path 可以访问此cookie的URL
- secure 是否只能通过https来传递此条cookie
- HttpOnly 是否只能通过Http操作读写Cookie
- expires/Max-Age cookie超时时间

#### Java操作Cookie
```java
Cookie cookie = new Cookie("author" ,URLEncoder.encode("仅供测试" , enc)) ;
cookie.setComment(URLEncoder.encode("仅供测试" , enc));
cookie.setMaxAge(120);
cookie.setPath(req.getRequestURI());
cookie.setSecure(false);
cookie.setHttpOnly(true);
resp.addCookie(cookie);
```

#### Javascript操作Cookie
```js
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
```

#### jQuery操作Cookie
```js
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
// $.cookie('SID', null ,{ expires: -1 ,path: '/cookie' });
```

#### OkHttpClient操作Cookie
```java
String url = "http://c0.zlikun.com/cookie/display.jsp" ;

Request request = new Request.Builder()
        .url(url)
        .addHeader("User-Agent" ,"HttpClient")
        .addHeader("Cookie" ,"JSESSIONID=xxxxxx; author=zlikun")
        .post(RequestBody.create(mediaType, "uname=kevin"))
        .build();

Response response = client.newCall(request).execute();
log.info("code : {} / message : {}" ,response.code() ,response.message());

String text = null ;
if(response.isSuccessful()) {
    text = response.body().string();
    log.info("\n{}" ,text);
}

response.close();
```

## Cookie安全

#### JSESSIONID
> Servlet API 中，用于保持会话的Cookie默认名为：JSESSIONID，值通常为难于伪造的较长随机字符串。JSESSIONID的生成通常应交由Servlet容器来实现，以避免安全问题。  
> 测试发现默认情况下，Jetty生成的JSESSIONID(Cookie)，未指定HttpOnly属性，容易引发安全问题

```xml
<!-- 在工程的/WEB-INF/web.xml中，可以配置SESSIONID的COOKIE配置 -->
<session-config>
    <!-- 指定会话超时时间：30分钟 -->
    <session-timeout>30</session-timeout>
    <cookie-config>
        <!-- 修改SESSIONID名为SID，默认：JSESSIONID -->
        <name>SID</name>
        <!-- 修改SESSIONID的HTTP属性为true，默认：false -->
        <http-only>true</http-only>
    </cookie-config>
</session-config>
```

> 怎样解决Cookie禁用时的会话保持(Jetty/Tomcat)  
> 怎样处理URL后面的JSESSIONID参数(未禁用Cookie的情况下，针对Jetty/Tomcat)

#### 固定会话攻击
1. 打开Chrome，模拟正常用户访问，在页面上打印sessionid值，记录该值：$jessionid(模拟攻击者伪造并诱使用户使用了该值)。注意这里不是为了模拟sessionid泄漏的情况(虽然实际上是^_^)
2. 打开Firefox(模拟攻击者电脑)，攻击者使用事先伪造的sessionid访问网站：通过 http://localhost/;jsessionid=$jessionid 方式访问(也可以将essionid写入cookie中，实现会话机制)
3. 此时正常用户登录，因为sessionid并未改变，所以攻击者在正常用户登录后访问，即可访问到正常用户的session信息，从而实现固定会话攻击

解决办法：用户登录后，服务器重新为用户生成jessionid，具体见`LoginServlet`代码。

```java
// 先注销session，后面将重新创建session，从页实现登录前后sessionid发生改变
req.getSession().invalidate();

// 将用户信息写入session
req.getSession(true).setAttribute("login_username" ,username);
```







