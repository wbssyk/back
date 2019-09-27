package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.business.homepagenotice.request.HomePageNoticeRequest;
import com.demo.back.dao.HomePageNoticeMapper;
import com.demo.back.entity.HomePageNotice;
import com.demo.back.exception.ParamNullException;
import com.demo.back.service.IHomePageNoticeService;
import com.demo.back.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

import static com.demo.back.utils.Constants.*;

/**
 * @ClassName HomePageNoticeServiceImpl
 * @Author yakun.shi
 * @Date 2019/7/1 10:37
 * @Version 1.0
 **/
@Service
public class HomePageNoticeServiceImpl implements IHomePageNoticeService {

    private static final String NOTICE_KEY = "notice_key:";

    @Autowired
    private HomePageNoticeMapper homePageNoticeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ServiceResponse noticeInsert(HomePageNotice request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        String id = UUIDUtil.getUUID();
        request.setId(id);
        request.setReviewStatus("1");
        request.setCreateTime(new Date());
        homePageNoticeMapper.noticeInsert(request);
        Map<String, String> map = new HashMap<>(16);
        map.put("id", id);
        serviceResponse.setData(map);
        return serviceResponse;
    }

    @Override
    public PageResponse noticeList(HomePageNoticeRequest request) {
        List<Map<String, Object>> browserUpgrades = homePageNoticeMapper.noticeList(request);
        Integer count = homePageNoticeMapper.noticeListCount(request);
        return PageResponse.createResponse(count, browserUpgrades, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse noticeUpdate(HomePageNotice request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            throw new ParamNullException();
        }
        homePageNoticeMapper.noticeUpdate(request);
        String id = request.getId();
        String key = NOTICE_KEY + id;
        try {
            Map<String, Object> homePageNotice = homePageNoticeMapper.selectById(request);
            if (StringUtils.isNotEmpty(request.getReviewStatus())) {
                if (request.getReviewStatus().equals(CHECK_STATUS_PASS)) {
                    redisTemplate.opsForValue().set(key, homePageNotice);
                }
                if (request.getReviewStatus().equals(CHECK_STATUS_NOT_PASS)) {
                    if (redisTemplate.opsForValue().get(key) != null) {
                        redisTemplate.delete(key);
                    }
                }
            }
            if (request.getCheckStatus() != null) {
                if (request.getCheckStatus().equals(DELETE_STATUE_FRBB) ||
                        request.getCheckStatus().equals(DELETE_STATUE_NOT)) {
                    if (redisTemplate.opsForValue().get(key) != null) {
                        redisTemplate.delete(key);
                    }
                }if(request.getCheckStatus().equals(DELETE_STATUE_APP)){
                    redisTemplate.opsForValue().set(key, homePageNotice);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("id", request.getId());
        serviceResponse.setData(map);
        return serviceResponse;
    }

    @Override
    public ServiceResponse noticeClientList() {
        ServiceResponse serviceResponse = new ServiceResponse();
        List<Map<String,Object>> resultFromRedis = getResultFromRedis();
        serviceResponse.setData(resultFromRedis);
        return serviceResponse;
    }
    private List<Map<String,Object>> getResultFromRedis() {
        Set keys = redisTemplate.keys(NOTICE_KEY+"*");
        List<Map<String,Object>> list = redisTemplate.opsForValue().multiGet(keys);
        return list;
    }
}
