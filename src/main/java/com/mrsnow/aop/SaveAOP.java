//package com.mrsnow.generator.aop;
//
//import com.mrsnow.generator.utils.ThreadUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @Author MrSnow *** dz
// * @CreateTime: 2023-04-07  15:19
// **/
//@Component
//@Aspect
//@Slf4j
//public class SaveAOP {
//
//    @Pointcut("execution(* com.mrsnow.generator.config.CustomerIdGenerator.nextId(..))")
//    public void saveAOPPointCut(){}
//
//    @AfterReturning(value = "saveAOPPointCut()",returning = "id")
//    public void getId(JoinPoint joinPoint,Long id){
//        try {
//            List<Long> ids = ThreadUtil.getSaveIds();
//            ids.add(id);
//            ThreadUtil.setSaveIds(ids);
//            log.info("当前线程ID: {},保存了雪花ID：{}",ThreadUtil.get("currentT_ID"),id);
//        } catch (Throwable e) {
//            log.error("获取雪花ID出错"+e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//}
