package com.mrsnow.config;


import com.mrsnow.filters.CorsFilter;
import com.mrsnow.filters.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-04-07  14:26
 **/
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Autowired
    private ThreadUtilInterceptor threadUtilInterceptor;
    @Autowired
    private LoginFilter loginFilter;

    //拦截器注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(threadUtilInterceptor)
                .addPathPatterns("/**")
                .order(-30);
    }
    //跨域配置
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        //放行原始域
//        corsConfiguration.addAllowedOrigin("*");
//        //是否发送cookie
//        corsConfiguration.setAllowCredentials(true);
//        //放行请求类型
//        corsConfiguration.addAllowedMethod("*");
//        //放行原始头部信息
//        corsConfiguration.addAllowedHeader("*");
//        //暴露哪些头部信息
//        corsConfiguration.addExposedHeader("*");
//        //添加映射路径
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**",corsConfiguration);
//        //返回新的CorsFilter
//        return new CorsFilter(source);
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
//                .allowedHeaders("*")
//                .exposedHeaders("*")
//                .allowedOrigins("*")
//                .allowedMethods("GET","POST","PUT","DELETE");
//    }

    //过滤器注册
    @Bean("getLoginFilter")
    public FilterRegistrationBean<LoginFilter> getLoginFilterRegistrationBean(){
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(loginFilter);
        registrationBean.setOrder(2);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("loginFilter");
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }

    @Bean("getCorsFilter")
    public FilterRegistrationBean<CorsFilter> getCorsFilterRegistrationBean(){
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsFilter());
        registrationBean.setOrder(-99);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("corsFilter");
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }
}
