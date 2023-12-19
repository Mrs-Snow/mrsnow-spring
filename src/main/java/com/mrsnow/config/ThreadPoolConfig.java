package com.mrsnow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-03-17  17:10
 **/
@Configuration
public class ThreadPoolConfig {

    @Bean("myThreadPool")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setKeepAliveSeconds(10);
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setQueueCapacity(3);
        //初始化线程池
        executor.initialize();
        //修改拒绝策略为使用当前线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
