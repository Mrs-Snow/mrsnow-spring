package com.mrsnow.config;

import com.mrsnow.utils.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-04-02  10:40
 **/
@Slf4j
@Component
public class ThreadUtilInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ThreadUtil.set("currentT_ID",Thread.currentThread().getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("当前线程ID：{}，remove已执行",Thread.currentThread().getId());
        ThreadUtil.remove();
    }
}
