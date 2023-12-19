package com.mrsnow.redisLock;

import java.util.concurrent.TimeUnit;

public interface ILock {
    boolean tryLock(String name, long timeoutSec, TimeUnit timeUnit);
    boolean tryLock(String name, long timeoutSec);
    void unlock(String name);
}
