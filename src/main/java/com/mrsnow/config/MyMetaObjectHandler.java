package com.mrsnow.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-04-09  20:35
 **/
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        fillCreated(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        try {
            Field updateTime = metaObject.getOriginalObject().getClass().getDeclaredField("updateTime");
            updateTime.setAccessible(true);
            //如果已经有值，就不设置
            if (updateTime.get(originalObject)!=null){
                return;
            }
            this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.info("此表不包含createdBy字段");
        }
    }

    private void fillCreated(MetaObject metaObject){
        Object originalObject = metaObject.getOriginalObject();
        try {
            Field createdTime = metaObject.getOriginalObject().getClass().getDeclaredField("createdTime");
            createdTime.setAccessible(true);
            //如果已经有值，就不设置
            if (createdTime.get(originalObject)!=null){
                return;
            }
            this.setFieldValByName("createdTime", LocalDateTime.now(),metaObject);

            Field updateTime = metaObject.getOriginalObject().getClass().getDeclaredField("updateTime");
            updateTime.setAccessible(true);
            //如果已经有值，就不设置
            if (updateTime.get(originalObject)!=null){
                return;
            }
            this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.info("此表不包含createdBy字段");
        }
    }
}
