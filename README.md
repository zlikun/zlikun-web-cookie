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