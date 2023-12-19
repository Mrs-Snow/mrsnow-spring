package com.mrsnow.filters;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mrsnow.utils.R;
import com.mrsnow.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private String[] whiteNames = {"login"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
        log.info("登录过滤器注册-------------------------------------");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
        String url = request.getRequestURL().toString();
        //白名单直接放行
        for (String whiteName : whiteNames) {
            if (url.contains(whiteName)){
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }
        String token = request.getHeader("token");
        if (token==null || token.length()==0){
            servletResponse.getWriter().write(JSONObject.toJSONString(R.fail(-1,"请登录!")));
            return;
        }
        try {
            boolean verify = TokenUtil.verify(token);
            if (verify) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                servletResponse.getWriter().write(JSONObject.toJSONString(R.fail(-1,"非法的token!")));
            }
        } catch (TokenExpiredException e) {
            servletResponse.getWriter().write(JSONObject.toJSONString(R.fail(-1,"登录已过期!")));
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
