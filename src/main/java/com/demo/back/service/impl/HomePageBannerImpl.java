package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.business.homepagebanner.request.HomePageBannerRequest;
import com.demo.back.dao.HomePageBannerMapper;
import com.demo.back.entity.HomePageBanner;
import com.demo.back.exception.ParamNullException;
import com.demo.back.service.IHomePageBannerService;
import com.demo.back.utils.Constants;
import com.demo.back.utils.StringUtil;
import com.demo.back.utils.upload.UploadCommon;
import com.demo.back.utils.upload.UploadUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @program: demo-back-admin
 * @description: 背景图实现类
 * @author: yaKun.shi
 * @create: 2019-07-29 09:14
 **/
@Service
public class HomePageBannerImpl implements IHomePageBannerService {

    private final static String BANNER_KEY = "banner_key:";

//    @Autowired
//    private FtpUtils ftpUtils;

    @Autowired
    private HomePageBannerMapper homePageBannerMapper;
//
//    @Autowired
//    private FtpProperties ftpProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UploadUtils uploadUtils;

    @Override
    public ServiceResponse bannerUpload(MultipartFile file) throws IOException {
        ServiceResponse response = new ServiceResponse();
        String filePath = "";
        String bannerType = null;
        try {
            if(StringUtil.isPhoto(file.getOriginalFilename())){
                filePath = "/photos";
                bannerType = "0";
            }
            if(StringUtil.isVideo(file.getOriginalFilename())){
                filePath = "/videos";
                bannerType = "1";
            }
            UploadCommon upload = uploadUtils.upload(file,filePath);
            //上传结束
            Map<String, Object> map = new HashMap<>(16);
            map.put("fileName", file.getOriginalFilename());
            map.put("filePath", upload.getDownLoadUrl());
            map.put("id",upload.getUuid());
            map.put("bannerType",bannerType);
            response.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public ServiceResponse bannerInsert(HomePageBanner request) throws Exception {
        ServiceResponse serviceResponse = new ServiceResponse();
        request.setReviewStatus("1");
        request.setCreateTime(new Date());
        homePageBannerMapper.bannerInsert(request);
        Map<String, String> map = new HashMap<>(16);
        map.put("id", request.getId());
        serviceResponse.setData(map);
        return serviceResponse;
    }

    @Override
    public PageResponse bannerList(HomePageBannerRequest request) {
        List<Map<String, Object>> homePage = homePageBannerMapper.bannerList(request);
        Integer count = homePageBannerMapper.bannerListCount(request);
        return PageResponse.createResponse(count, homePage, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse bannerUpdate(HomePageBanner request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            throw new ParamNullException();
        }
        homePageBannerMapper.bannerUpdate(request);
        String id = request.getId();
        String key = BANNER_KEY + id;
        try {
            Map<String, Object> homePageBanner = homePageBannerMapper.selectById(request);
            if (StringUtils.isNotEmpty(request.getReviewStatus())) {
                if (request.getReviewStatus().equals(Constants.CHECK_STATUS_PASS)) {
                    redisTemplate.opsForValue().set(key, homePageBanner);
                }
                if (request.getReviewStatus().equals(Constants.CHECK_STATUS_NOT_PASS)) {
                    if (redisTemplate.opsForValue().get(key) != null) {
                        redisTemplate.delete(key);
                    }
                }
            }
            if (request.getCheckStatus() != null) {
                if (request.getCheckStatus().equals(Constants.DELETE_STATUE_FRBB) ||
                        request.getCheckStatus().equals(Constants.DELETE_STATUE_NOT)) {
                    if (redisTemplate.opsForValue().get(key) != null) {
                        redisTemplate.delete(key);
                    }
                }if(request.getCheckStatus().equals(0)){
                    redisTemplate.opsForValue().set(key, homePageBanner);
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
    public ServiceResponse bannerClientList() {
        ServiceResponse serviceResponse = new ServiceResponse();
        List<Map<String,Object>> resultFromRedis = getResultFromRedis();
        serviceResponse.setData(resultFromRedis);
        return serviceResponse;
    }
    private List<Map<String,Object>> getResultFromRedis() {
        Set keys = redisTemplate.keys(BANNER_KEY+"*");
        List<Map<String,Object>> list = redisTemplate.opsForValue().multiGet(keys);
        return list;
    }
}
