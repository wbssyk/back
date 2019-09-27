package com.demo.back.dao;

import com.demo.back.controller.log.systemlog.request.LogRequest;
import com.demo.back.entity.SystemLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemLogMapper {

    void insertLog(@Param("request") SystemLog systemLog);

    List<Map<String,Object>> systemLogList(@Param("request") LogRequest request);

    Integer systemLogListCount(@Param("request") LogRequest request);
}
