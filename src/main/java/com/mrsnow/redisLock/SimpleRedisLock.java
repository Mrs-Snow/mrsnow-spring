package com.mrsnow.redisLock;

import cn.hutool.core.lang.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-03-29  13:28
 * 分布式锁实现
 **/
@Component
@RequiredArgsConstructor
public class SimpleRedisLock implements ILock{
    @Resource
    private RedisTemplate<String,String> stringRedisTemplate;
    private static final String KEY_PREFIX = "lock:";
    public static final String ID_PREFIX = UUID.randomUUID().toString(true)+"-";
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;
    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("/lua/unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }
    @Override
    public boolean tryLock(String name, long timeoutSec, TimeUnit timeUnit) {
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name, threadId, timeoutSec, timeUnit);
        return Boolean.TRUE.equals(success);
    }

    @Override
    public boolean tryLock(String name,long timeoutSec) {
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name, threadId, timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    @Override
    public void unlock(String name) {
       // call lua
        stringRedisTemplate.execute(UNLOCK_SCRIPT,
                Collections.singletonList(KEY_PREFIX + name),
                ID_PREFIX + Thread.currentThread().getId());
    }

    ///容易因为GC阻塞主线程导致被动超时，出现线程不安全
//    @Override
//    public void unlock() {
//        String threadId = ID_PREFIX + Thread.currentThread().getId();
//        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
//        if(threadId.equals(id)){
//            stringRedisTemplate.delete(KEY_PREFIX + name);
//        }
//    }
}
