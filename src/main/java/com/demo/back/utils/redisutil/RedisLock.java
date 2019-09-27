package com.demo.back.utils.redisutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisLock
 * @Author yakun.shi
 * @Date 2019/7/1 14:54
 * @Version 1.0
 **/
@Component
public class RedisLock {

    /**
     * 锁过期时间 默认为3分钟
     */
    public final static long DEFAULT_LOCK_TIMEOUT = 180L;
    /**
     * 获取锁时间  默认为2分钟
     */
    public static final long DEFAULT_ACQUIRE_RESOLUTION_MILLIS = 120L;

    /**
     * 分布式锁key前缀
     */
    public static final String KEY = "dg_redis_lock_";

    @Autowired
    private StringRedisTemplate redisTemplate;


    public Lock lock(String lockKey) throws InterruptedException {
        return lock(lockKey, DEFAULT_ACQUIRE_RESOLUTION_MILLIS, DEFAULT_LOCK_TIMEOUT);
    }

    public Lock lock(String lockKey, long acquireTimeoutInMillis) throws InterruptedException {
        return lock(lockKey, acquireTimeoutInMillis, DEFAULT_LOCK_TIMEOUT);
    }

    /**
     * @Author yakun.shi
     * @Description //加锁过程
     * @Date 2019/7/2 9:04
     * @Param [lockKey, acquireTimeoutInMillis, lockExpiryInMillis]
     * @return com.demo.back.utils.redisutil.Lock
     **/
    public Lock lock(String lockKey, long acquireTimeoutInMillis, long lockExpiryInMillis)
            throws InterruptedException {
        String lockKeyPath = KEY + lockKey;
        Lock lock = asLock(lockKey, lockExpiryInMillis);
        long timeout = 0L;
        while (timeout < acquireTimeoutInMillis) {
            // 未锁则直接获取
            if (redisTemplate.opsForValue().setIfAbsent(lockKeyPath, lock.getLockValue())) {
                return lock;
            }
            // 已锁则查看超时时间
            String currentLockExpriyStr = redisTemplate.opsForValue().get(lockKeyPath);
            long currentLockExpriy = Long.parseLong(currentLockExpriyStr);

            // 锁已过期,延长锁时间(即程序已经跑完，锁已经释放)
            if (currentLockExpriy < System.currentTimeMillis()) {
                // 获取上一个锁的过期时间，并设置现在锁的过期时间
                String oldValueStr = redisTemplate.opsForValue().getAndSet(lockKeyPath, lock.getLockValue());
                // 考虑多线程并发的情况，只有一个线程的设置值和当前值相同，它才有权利加锁
                if (currentLockExpriyStr.equals(oldValueStr)) {
                    return lock;
                }
            }

            // 锁没有过期 线程休眠
            TimeUnit.MILLISECONDS.sleep(DEFAULT_ACQUIRE_RESOLUTION_MILLIS);
            timeout += DEFAULT_ACQUIRE_RESOLUTION_MILLIS;
        }
        return null;
    }


    /**
     * @return void
     * @Author yakun.shi
     * @Description //释放锁
     * @Date 2019/7/1 19:24
     * @Param [lock]
     **/
    public void unLock(Lock lock) {
        if (lock == null) {
            return;
        }
        String lockKeyPath = KEY + lock.getLockKey();
        String lockUuid = redisTemplate.opsForValue().get(lockKeyPath);
        String lockValue = lock.getLockValue();
        if (lockValue.equals(lockUuid)) {
            redisTemplate.delete(lockKeyPath);
        } else {
            throw new RuntimeException("can not release timeout lock");
        }
    }

    /**
     * @return com.demo.back.utils.redisutil.Lock
     * @Author yakun.shi
     * @Description //生成lock类
     * @Date 2019/7/1 19:09
     * @Param [lockKey, lockExpiryInMillis]
     **/
    private Lock asLock(String lockKey, long lockExpiryInMillis) {
        return new Lock(lockKey, System.currentTimeMillis() + lockExpiryInMillis);
    }
}
