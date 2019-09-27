package com.demo.back.utils.redisutil;

/**
 * @ClassName Lock
 * @Author yakun.shi
 * @Date 2019/7/1 14:56
 * @Version 1.0
 **/
public class Lock {
    //
    private String lockKey;
    private long expriyTime;

    public Lock(String lockKey, long expriyTime) {
        this.lockKey = lockKey;
        this.expriyTime = expriyTime;
    }

    public String getLockKey() {
        return lockKey;
    }

    public String getLockValue() {
        return String.valueOf(expriyTime);
    }
}
