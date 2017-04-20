package com.zlikun.web;

import okhttp3.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 测试使用OkHttpClient请求，同时发送Cookie消息头
 * @auther zlikun <zlikun-dev@hotmail.com>
 * @date 2017/4/19 10:08
 */
public class OkHttpClientTest {

    private static final Logger log = LoggerFactory.getLogger(OkHttpClientTest.class) ;

    private static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    private static final OkHttpClient.Builder builder = new OkHttpClient.Builder() ;
    private static final OkHttpClient client = builder
            .protocols(Arrays.asList(Protocol.HTTP_1_1))
            .connectTimeout(1000 , TimeUnit.MILLISECONDS)
            .readTimeout(500 ,TimeUnit.MILLISECONDS)
            // 用于保存Cookie(Android下存储Cookie用)
            .cookieJar(new CookieJar() {
                // 基于host存储Cookie信息，仅作测试用，实际需要持久化，否则本类执行结束后，信息将被清空(注意Android组件的生命周期)
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();
                // 保存响应中的Cookie
                @Override
                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                    if(list != null && !list.isEmpty()) {
                        cookieStore.put(httpUrl.host(), list);
                        // 遍历输出响应Cookie信息
                        for(Cookie cookie : list) {
                            log.info("Set-Cookie: {}" ,cookie);
                        }
                    }
                }
                // 获取请求中的Cookie
                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = cookieStore.get(httpUrl.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .dns(new Dns() {
                @Override
                public List<InetAddress> lookup(String s) throws UnknownHostException {
                    String host = s ;
                    // 指定域名，方便测试Cookie跨域
                    if(s.endsWith(".zlikun.com")) {
                        host = "localhost" ;
                    }
                    return Arrays.asList(InetAddress.getByName(host)) ;
                }
            })
            .build() ;

    @Test
    public void test() throws IOException {

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
    }

}
