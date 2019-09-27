package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.controller.log.systemlog.request.LogRequest;
import com.demo.back.entity.OperateLog;
import org.apache.ibatis.annotations.Param;

public interface IOperateLogService {
    void insertLog(OperateLog operateLog);

    PageResponse operateLogList(@Param("request") LogRequest request);
}
