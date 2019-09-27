package com.demo.back.dao;

import com.demo.back.scheduled.api.ScheduledRequest;
import org.apache.ibatis.annotations.Param;


public interface HomePageAdvertMapper {

    void updateById(@Param("request") ScheduledRequest scheduledRequest);

    Integer selectClickNumById(@Param("id") String id);

    void updateClickNumById(@Param("id") String id, @Param("num") Integer click_num);
}