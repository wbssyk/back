package com.demo.back.dao;


import com.demo.back.controller.business.homepagebanner.request.HomePageBannerRequest;
import com.demo.back.entity.HomePageBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: demo-back-admin
 * @description: 客户端登陆背景图mapper
 * @author: yaKun.shi
 * @create: 2019-07-29 09:12
 **/
public interface HomePageBannerMapper {

    void bannerInsert(@Param("request") HomePageBanner request);

    List<Map<String,Object>> bannerList(@Param("request") HomePageBannerRequest request);

    Integer bannerListCount(@Param("request") HomePageBannerRequest request);

    void bannerUpdate(@Param("request") HomePageBanner request);

    Map<String, Object> selectById(@Param("request") HomePageBanner request);
}
