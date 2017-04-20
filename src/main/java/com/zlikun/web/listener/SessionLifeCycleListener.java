package com.zlikun.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @auther zlikun <zlikun-dev@hotmail.com>
 * @date 2017/4/20 19:21
 */
@WebListener
public class SessionLifeCycleListener implements HttpSessionListener {

    private static final Logger log = LoggerFactory.getLogger(SessionLifeCycleListener.class) ;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("创建session = {}" ,se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("销毁session = {}" ,se.getSession().getId());
    }

}
