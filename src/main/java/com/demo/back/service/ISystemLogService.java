package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.controller.log.systemlog.request.LogRequest;
import com.demo.back.entity.SystemLog;
import org.apache.ibatis.annotations.Param;

public interface ISystemLogService {
    void insertLog(SystemLog systemLog);

    PageResponse systemLogList(@Param("request") LogRequest request);
}
