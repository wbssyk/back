package com.demo.back.dao;

import com.demo.back.controller.business.homepagenotice.request.HomePageNoticeRequest;
import com.demo.back.entity.HomePageNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HomePageNoticeMapper {

    void noticeInsert(@Param("request") HomePageNotice request);

    List<Map<String,Object>> noticeList(@Param("request") HomePageNoticeRequest request);

    Integer noticeListCount(@Param("request") HomePageNoticeRequest request);

    void noticeUpdate(@Param("request") HomePageNotice request);

    Map<String, Object> selectById(@Param("request") HomePageNotice request);

    List<HomePageNotice> selectList();
}