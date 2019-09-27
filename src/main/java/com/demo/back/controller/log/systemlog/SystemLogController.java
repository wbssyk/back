package com.demo.back.controller.log.systemlog;

import com.demo.back.common.page.PageResponse;
import com.demo.back.controller.log.systemlog.request.LogRequest;
import com.demo.back.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SystemLogController
 * @Author yakun.shi
 * @Date 2019/7/9 14:41
 * @Version 1.0
 **/
@RestController
@RequestMapping("log")
public class SystemLogController {

    @Autowired
    private ISystemLogService systemLogService;

    @PostMapping("system_log_list")
    @Cacheable(value = "system_log_list",keyGenerator = "wiselyKeyGenerator")
    public PageResponse systemLogList(@RequestBody LogRequest request){
        PageResponse serviceResponse = systemLogService.systemLogList(request);
        return serviceResponse;
    }
}
