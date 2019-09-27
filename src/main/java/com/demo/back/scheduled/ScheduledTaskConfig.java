package com.demo.back.scheduled;

import com.demo.back.common.clickcollectaop.ClickElementType;
import com.demo.back.dao.HomePageAdvertMapper;
import com.demo.back.scheduled.work.AdvertRedisToDb2Scheduled;
import com.demo.back.utils.redisutil.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @ClassName SchecduledConfig
 * @Author yakun.shi
 * @Date 2019/7/1 17:10
 * @Version 1.0
 **/
@Component
@EnableScheduling
public class ScheduledTaskConfig {

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HomePageAdvertMapper homePageAdvertMapper;

    /**
     * @return void
     * @Author yakun.shi
     * @Description //每天 晚上23点 从redis中同步广告点击量到数据库
     * @Date 2019/7/1 9:36
     * @Param []
     **/
//    @Scheduled(cron = "0 0/1 * * * ?")
//    @Scheduled(cron = "0 0 23 * * ?")
    public void advert(){
        AbstractRedisToDb2Scheduled toDb2Scheduled =
                new AdvertRedisToDb2Scheduled(redisLock,redisTemplate,ClickElementType.ADVERT);
        ((AdvertRedisToDb2Scheduled) toDb2Scheduled).setHomePageAdvertMapper(homePageAdvertMapper);
        toDb2Scheduled.execute();
    }
}
