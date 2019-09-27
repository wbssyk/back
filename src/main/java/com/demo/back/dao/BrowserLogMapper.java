package com.demo.back.dao;

import com.demo.back.controller.log.browserlog.request.BrowserLogRequest;
import com.demo.back.entity.BrowserLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: browser_template
 * @description: 浏览器日志
 * @author: yaKun.shi
 * @create: 2019-09-23 10:58
 **/
public interface BrowserLogMapper {

    void insertLog(@Param("request") BrowserLog browserLog);

    List<Map<String,Object>> browserLogList(@Param("request") BrowserLogRequest request);

    Integer browserLogListCount(@Param("request") BrowserLogRequest request);



}
