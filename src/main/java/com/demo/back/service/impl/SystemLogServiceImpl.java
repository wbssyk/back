package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.controller.log.systemlog.request.LogRequest;
import com.demo.back.dao.SystemLogMapper;
import com.demo.back.entity.SystemLog;
import com.demo.back.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SystemUserServiceImpl
 * @Author yakun.shi
 * @Date 2019/6/27 9:21
 * @Version 1.0
 **/
@Service
public class SystemLogServiceImpl implements ISystemLogService {

    @Autowired
    private SystemLogMapper systemLogMapper;

    @Override
    @Transactional
    public void insertLog(SystemLog systemLog) {
        systemLogMapper.insertLog(systemLog);
    }

    @Override
    public PageResponse systemLogList(LogRequest request) {
        List<Map<String,Object>> logList = systemLogMapper.systemLogList(request);
        Integer count = systemLogMapper.systemLogListCount(request);
        PageResponse response = PageResponse.createResponse(count, logList, request);
        return response;
    }
}
