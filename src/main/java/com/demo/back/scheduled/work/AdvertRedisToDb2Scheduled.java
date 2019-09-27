package com.demo.back.scheduled.work;

import com.demo.back.common.clickcollectaop.ClickElementType;
import com.demo.back.dao.HomePageAdvertMapper;
import com.demo.back.scheduled.AbstractRedisToDb2Scheduled;
import com.demo.back.utils.redisutil.RedisLock;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Iterator;

/**
 * @ClassName AdvertRedisToDb2Scheduled
 * @Author yakun.shi
 * @Date 2019/7/1 17:54
 * @Version 1.0
 **/
public class AdvertRedisToDb2Scheduled extends AbstractRedisToDb2Scheduled {

    private RedisTemplate redisTemplate;

    private HomePageAdvertMapper homePageAdvertMapper;

    public AdvertRedisToDb2Scheduled(RedisLock redisLock, RedisTemplate redisTemplate,
                                     ClickElementType clickElementType) {
        super(redisLock,redisTemplate,clickElementType);
        this.redisTemplate = redisTemplate;
    }

    /**
     * @Author yakun.shi
     * @Description //具体业务实现，同步redis数据到db2
     * @Date 2019/7/1 19:05
     * @Param []
     * @return void
     **/
    @Override
    public void doDb2(){
        Iterator iterator = super.getKeyPrefix().iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            Integer click_num = (Integer) redisTemplate.opsForValue().get(key);
            String id = super.getID(key.toString());
            Integer num = homePageAdvertMapper.selectClickNumById(id);
            Integer real_num = click_num+num;
            homePageAdvertMapper.updateClickNumById(id,real_num);
        }
    }

    public HomePageAdvertMapper getHomePageAdvertMapper() {
        return homePageAdvertMapper;
    }

    public void setHomePageAdvertMapper(HomePageAdvertMapper homePageAdvertMapper) {
        this.homePageAdvertMapper = homePageAdvertMapper;
    }
}
