//package com.demo.back.scheduled;
//
//import com.demo.back.common.clickcollectaop.ClickElementType;
//import com.demo.back.dao.HomePageAdvertMapper;
//import com.demo.back.scheduled.request.ScheduledRequest;
//import com.demo.back.utils.DateUtils;
//import com.demo.back.utils.redisutil.Lock;
//import com.demo.back.utils.redisutil.RedisLock;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
///**
// * @ClassName RedisToDb2Scheduled
// * @Author yakun.shi
// * @Date 2019/6/28 16:38
// * @Version 1.0
// **/
//@Component
//@EnableScheduling
//public class AdvertRedisToDb2Scheduled {
//
//    private static Logger logger = LoggerFactory.getLogger(AdvertRedisToDb2Scheduled.class);
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Autowired
//    private HomePageAdvertMapper advertMapper;
//
//    @Autowired
//    private RedisLock redisLock;
//
//    /**
//     * @return void
//     * @Author yakun.shi
//     * @Description //每天 晚上23点 从redis中同步广告点击量到数据库
//     * @Date 2019/7/1 9:36
//     * @Param []
//     **/
////    @Scheduled(cron = "0 0/1 * * * ?")
////    @Scheduled(cron = "0 0 23 * * ?")
//    @Transactional(rollbackFor = Exception.class)
//    public void advertRedisToDb2() {
//        Lock lock = null;
//        try {
//            lock = redisLock.tryLock("advert");
//            if(lock==null){
//                return;
//            }
//            String now = DateUtils.dateToString(LocalDateTime.now());
//            StringBuilder builder = new StringBuilder();
//            /** 获取redis中的 单个广告的key  行如 2019-07-01:ADVERT:sdadasdadasdads  :后的字符串为id*/
//            String keyPrefix = builder.append(now).append(":").
//                    append(ClickElementType.ADVERT.toString()).
//                    append(":").toString();
//            Set keys = redisTemplate.keys(keyPrefix + "*");
//            if (CollectionUtils.isEmpty(keys)) {
//                logger.info("redis数据中没有数据");
//                return;
//            }
//            /** 获取广告id*/
//            ScheduledRequest scheduledRequest = new ScheduledRequest();
//            Iterator iterator = keys.iterator();
//            while (iterator.hasNext()) {
//                Object key = iterator.next();
//                Integer click_num = (Integer) redisTemplate.opsForValue().get(key);
//                String id = getID(key.toString());
//                Integer num = advertMapper.selectClickNumById(scheduledRequest.getId());
//                scheduledRequest.setId(id);
//                scheduledRequest.setClickNum(click_num + num);
//                advertMapper.updateById(scheduledRequest);
//            }
//
//            /** 同步成功后 删除redis中的数据*/
//            logger.info("同步redis数据都db2成功,清除redis中的同步过的数据");
//            redisTemplate.delete(keys);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            redisLock.release(lock);
//        }
//
//    }
//
//    public String getID(String key) {
//        int i = StringUtil.lastIndexOf(key, ":");
//        String substring = StringUtil.substring(key, i + 1, key.length());
//        return substring;
//    }
//}
