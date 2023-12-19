package com.mrsnow.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadUtil {
    private static final ThreadLocal<Map<String,Object>> THREAD_LOCAL = new ThreadLocal<>();
    public ThreadUtil(){}

    public static void putAll(Map<String, String> map) {
        map.forEach((k, v) -> {
            set(k, v);
        });
    }

    public static void set(String key, Object value) {
        if (ObjectUtil.isEmpty(value) || StrUtil.isBlankOrUndefined(value.toString())) {
            return;
        }
        Map<String, Object> map = getLocalMap();
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = getLocalMap();
        return map.get(key);
    }

    public static <T> T get(String key, Class<T> type) {
        Map<String, Object> map = getLocalMap();
        return Convert.convert(type, map.get(key));
    }

    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>(10);
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void setSaveId(Long id){
        if(id==null){
            return;
        }
        Map<String, Object> localMap = getLocalMap();
        localMap.put("saveId",id);
    }

    public static void setSaveIds(List<Long> ids){
        if(ids==null||ids.isEmpty()){
            return;
        }
        Map<String, Object> localMap = getLocalMap();
        localMap.put("saveIds",ids);
    }

    public static Long getSaveId(){
        Map<String, Object> localMap = getLocalMap();
        return (Long) localMap.get("saveId");
    }

    public static List<Long> getSaveIds(){
        Map<String, Object> localMap = getLocalMap();
        List<Long> list = (List<Long>) localMap.get("saveIds");
        if (list==null){
            return new CopyOnWriteArrayList<>();
        }
        return list;
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
