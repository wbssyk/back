package com.demo.back.scheduled;

import com.demo.back.common.clickcollectaop.ClickElementType;
import com.demo.back.utils.DateUtils;
import com.demo.back.utils.redisutil.Lock;
import com.demo.back.utils.redisutil.RedisLock;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @ClassName AbstractRedisToDb2Scheduled
 * @Author yakun.shi
 * @Date 2019/7/1 17:19
 * @Version 1.0
 **/
public abstract class AbstractRedisToDb2Scheduled {

    private static Logger logger = LoggerFactory.getLogger(AbstractRedisToDb2Scheduled.class);

    private RedisLock redisLock;

    private RedisTemplate redisTemplate;

    private ClickElementType clickElementType;

    public AbstractRedisToDb2Scheduled(RedisLock redisLock, RedisTemplate redisTemplate, ClickElementType clickElementType) {
        this.redisLock = redisLock;
        this.redisTemplate = redisTemplate;
        this.clickElementType = clickElementType;
    }

    /**
     * @Author yakun.shi
     * @Description //获取锁，防止多个服务启动，多个定时器执行
     * @Date 2019/7/1 19:06
     * @Param []
     * @return com.demo.back.utils.redisutil.Lock
     **/
    private Lock lock() throws InterruptedException {
        Lock lock = redisLock.lock(clickElementType.toString());
        return lock;
    }

    /**
     * @Author yakun.shi
     * @Description //获取redis中的所有的key
     * @Date 2019/7/1 19:07
     * @Param []
     * @return java.util.Set
     **/
    public Set getKeyPrefix() {
        String now = DateUtils.dateToString(LocalDateTime.now());
        StringBuilder builder = new StringBuilder();
        /** 获取redis中的 行如 2019-07-01:ADVERT:sdadasdadasdads 的所有key */
        String keyPrefix = builder.append(now).append(":").
                append(clickElementType.toString()).
                append(":").append("*").toString();
        Set keys = redisTemplate.keys(keyPrefix + "*");
        return keys;
    }

    /**
     * @Author yakun.shi
     * @Description //同步成功后清除 redis 中的数据
     * @Date 2019/7/1 19:07
     * @Param [keys]
     * @return void
     **/
    private void deleteKeys(Set keys) {
        redisTemplate.delete(keys);
    }

    /**
     * @Author yakun.shi
     * @Description //获取每个业务的 id
     * @Date 2019/7/1 19:08
     * @Param [key]
     * @return java.lang.String
     **/
    public String getID(String key) {
        int i = StringUtils.lastIndexOf(key, ":");
        String substring = StringUtils.substring(key, i + 1, key.length());
        return substring;
    }

    public abstract void doDb2();

    protected void execute() {
        Lock lock = null;
        try {
            lock = lock();
            if (lock == null) {
                return;
            }

            Set set = getKeyPrefix();
            if (CollectionUtils.isEmpty(set)) {
                logger.info("redis数据中没有数据");
                return;
            }
            doDb2();
            deleteKeys(set);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock != null) {
                redisLock.unLock(lock);
            }
        }
    }
}
