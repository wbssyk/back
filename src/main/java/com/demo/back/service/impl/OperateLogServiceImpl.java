package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.controller.log.systemlog.request.LogRequest;
import com.demo.back.dao.OperateLogMapper;
import com.demo.back.entity.OperateLog;
import com.demo.back.service.IOperateLogService;
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
public class OperateLogServiceImpl implements IOperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    @Transactional
    public void insertLog(OperateLog operateLog) {
        operateLogMapper.insertLog(operateLog);
    }

    @Override
    public PageResponse operateLogList(LogRequest request) {
        List<Map<String,Object>> logList = operateLogMapper.operateLogList(request);
        Integer count = operateLogMapper.operateLogListCount(request);
        PageResponse response = PageResponse.createResponse(count, logList, request);
        return response;
    }
}
