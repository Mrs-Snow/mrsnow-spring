package com.mrsnow.filters;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mrsnow.utils.R;
import com.mrsnow.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-12-19  08:55
 **/
@Component
@Slf4j
public class LoginFilter implements Filter {
    @Autowired
    private RedisTemplate redisTemplate;
    //过滤模式 0:白名单模式 1:黑名单模式
    @Value("${mrsnow.auth.mode}")
    private int mode;
    private String[] whiteNames = {"/login", "/test"};
    private String[] authNames = {"/auth"};
    private final int needLoginCode = 601;
    private final int loginTimeOut = 602;
    private final int unLawToken = 603;
    private final int successCode = 200;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
        log.info("登录过滤器注册-------------------------------------");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
        String url = request.getRequestURL().toString();
        if (mode == 0) {
            //白名单直接放行
            for (String whiteName : whiteNames) {
                if (url.contains(whiteName)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        //黑名单模式，指定接口需要登录权限
        if (mode == 1) {
            for (String authName : authNames) {
                if (!url.contains(authName)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        String token = request.getHeader("token");
        if (token == null || token.length() == 0) {
            servletResponse.getWriter().write(JSONObject.toJSONString(R.fail(needLoginCode, "请登录!")));
            return;
        }
        try {
            boolean verify = TokenUtil.verify(token);
            if (verify) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                servletResponse.getWriter().write(JSONObject.toJSONString(R.fail(unLawToken, "非法的token!")));
            }
        } catch (TokenExpiredException e) {
            servletResponse.getWriter().write(JSONObject.toJSONString(R.fail(loginTimeOut, "登录已过期!")));
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
