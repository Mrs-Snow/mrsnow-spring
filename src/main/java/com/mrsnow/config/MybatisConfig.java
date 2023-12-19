package com.mrsnow.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-03-17  17:08
 **/
@Configuration
public class MybatisConfig {

//    @Bean
//    public PaginationInterceptor paginationInterceptor(){return new PaginationInterceptor();}

    /**
     * Mybatis Plus 注入器
     *
     * @return 注入器
     */
    @Bean("myMetaObjectHandler")
    @ConditionalOnMissingBean
    public MetaObjectHandler getMyMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }
}
