package com.demo.back.controller.browser.browsersafestrategy;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.browsersafestrategy.request.BrowserStrategyRequest;
import com.demo.back.entity.BrowserSafeStrategy;
import com.demo.back.service.IBrowserSafeStrategyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: browser_template
 * @description: 浏览器全局安全控制
 * @author: yaKun.shi
 * @create: 2019-09-23 09:59
 **/
@RestController
@RequestMapping("browserSafeStrategy")
public class BrowserSafeStrategyController {

    @Autowired
    private IBrowserSafeStrategyService browserSafeStrategyService;

    /**
     * @Method
     * @Description 策略列表
     * @Return
     */
    @PostMapping("list")
    @ApiOperation(value = "策略--列表")
    public PageResponse browserSafeStrategyInfoList(@RequestBody BrowserStrategyRequest request){
        return browserSafeStrategyService.browserSafeStrategyInfoList(request);
    }

    /**
     * @Method
     * @Description 策略更新
     * @Return
     */
    @PostMapping("update")
    @ApiOperation(value = "策略--更新")
    public ServiceResponse browserSafeStrategyInfoUpdate(@RequestBody BrowserSafeStrategy request){
        return browserSafeStrategyService.browserSafeStrategyInfoUpdate(request);
    }

    /**
     * @Method
     * @Description 策略详情
     * @Return
     */
    @PostMapping("detail")
    @ApiOperation(value = "策略--详情")
    public ServiceResponse browserSafeStrategyInfoDetail(@RequestBody BrowserSafeStrategy request){
        return browserSafeStrategyService.browserSafeStrategyInfoDetail(request);
    }

    /**
     * @Method
     * @Description 查询后台配置策略返回给浏览器
     * @Return
     */
    @GetMapping("client/detail")
    @ApiOperation(value = "后台配置策略返回给浏览器")
    public ServiceResponse selectSafeStrategyService() {
        return browserSafeStrategyService.selectSafeStrategyService();
    }
}
