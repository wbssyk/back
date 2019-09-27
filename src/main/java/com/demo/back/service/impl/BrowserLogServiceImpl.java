package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.log.browserlog.request.BrowserLogRequest;
import com.demo.back.dao.BrowserLogMapper;
import com.demo.back.entity.BrowserLog;
import com.demo.back.service.IBrowserLogService;
import com.demo.back.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: browser_template
 * @description: 浏览器日志
 * @author: yaKun.shi
 * @create: 2019-09-23 11:07
 **/
@Service
public class BrowserLogServiceImpl implements IBrowserLogService {

    @Autowired
    private BrowserLogMapper browserLogMapper;

    @Override
    public ServiceResponse browserLogInsert(BrowserLog request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        String id = UUIDUtil.getUUID();
        request.setId(id);
        request.setCreateTime(new Date());
        browserLogMapper.insertLog(request);
        Map<String, String> map = new HashMap<>(16);
        map.put("id", id);
        serviceResponse.setData(map);
        return serviceResponse;
    }

    @Override
    public PageResponse browserLogList(BrowserLogRequest request) {
        List<Map<String, Object>> maps = browserLogMapper.browserLogList(request);
        Integer integer = browserLogMapper.browserLogListCount(request);
        return PageResponse.createResponse(integer,maps,request);
    }
}
