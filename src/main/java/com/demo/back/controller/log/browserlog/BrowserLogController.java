package com.demo.back.controller.log.browserlog;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.log.browserlog.request.BrowserLogRequest;
import com.demo.back.entity.BrowserLog;
import com.demo.back.service.IBrowserLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: browser_template
 * @description: 浏览器日志
 * @author: yaKun.shi
 * @create: 2019-09-23 10:44
 **/
@RestController
@RequestMapping("log")
public class BrowserLogController {

    @Autowired
    private IBrowserLogService browserLogService;

    /**
     * @Method
     * @Description 收集浏览器日志
     * @Return
     */
    @ApiOperation(value = "收集浏览器日志")
    @PostMapping("browser/insert")
    public ServiceResponse browserLogInsert(@RequestBody BrowserLog request){
        ServiceResponse serviceResponse = browserLogService.browserLogInsert(request);
        return serviceResponse;
    }
    /**
     * @Method
     * @Description 后台展示浏览器列表
     * @Return
     */
    @ApiOperation(value = "后台展示浏览器列表")
    @PostMapping("browser_log_list")
    @Cacheable(value = "browser_log_list",keyGenerator = "wiselyKeyGenerator")
    public PageResponse browserLogList(@RequestBody BrowserLogRequest request){
        PageResponse serviceResponse = browserLogService.browserLogList(request);
        return serviceResponse;
    }

}
