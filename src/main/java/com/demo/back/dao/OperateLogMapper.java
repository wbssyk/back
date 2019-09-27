package com.demo.back.dao;

import com.demo.back.controller.log.systemlog.request.LogRequest;
import com.demo.back.entity.OperateLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OperateLogMapper {

    void insertLog(@Param("request") OperateLog operateLog);

    List<Map<String,Object>> operateLogList(@Param("request") LogRequest request);

    Integer operateLogListCount(@Param("request") LogRequest request);
}
