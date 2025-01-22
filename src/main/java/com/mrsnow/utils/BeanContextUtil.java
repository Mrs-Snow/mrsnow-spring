package com.mrsnow.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-12-05  17:17
 * 获取bean的工具类
 **/
@Component
public class BeanContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name,clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
}
