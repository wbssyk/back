package com.demo.back.controller.log.systemlog;

import com.demo.back.common.page.PageResponse;
import com.demo.back.controller.log.systemlog.request.LogRequest;
import com.demo.back.service.IOperateLogService;
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
public class OperateLogController {

    @Autowired
    private IOperateLogService operateLogService;

    @PostMapping("operate_log_list")
    @Cacheable(value = "operate_log_list",keyGenerator = "wiselyKeyGenerator")
    public PageResponse systemLogList(@RequestBody LogRequest request){
        PageResponse serviceResponse = operateLogService.operateLogList(request);
        return serviceResponse;
    }
}
