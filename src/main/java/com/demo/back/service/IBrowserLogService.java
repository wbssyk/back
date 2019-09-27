package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.log.browserlog.request.BrowserLogRequest;
import com.demo.back.entity.BrowserLog;

/**
 * @program: browser_template
 * @description: 浏览器日志
 * @author: yaKun.shi
 * @create: 2019-09-23 11:06
 **/
public interface IBrowserLogService {
    ServiceResponse browserLogInsert(BrowserLog request);

    PageResponse browserLogList(BrowserLogRequest request);
}
