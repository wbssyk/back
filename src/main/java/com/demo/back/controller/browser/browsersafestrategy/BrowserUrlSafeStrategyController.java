package com.demo.back.controller.browser.browsersafestrategy;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.browsersafestrategy.request.BrowserStrategyRequest;
import com.demo.back.entity.BrowserUrlSafeStrategy;
import com.demo.back.service.IBrowserUrlSafeStrategyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: browser_template
 * @description: 浏览器 细粒度 url策略控制
 * @author: yaKun.shi
 * @create: 2019-09-23 09:56
 **/
@RestController
@RequestMapping("browserUrlSafeStrategy")
public class BrowserUrlSafeStrategyController {

    @Autowired
    private IBrowserUrlSafeStrategyService browserUrlSafeStrategyService;
    /**
     * @Method
     * @Author yakun.shi
     * @Description url浏览器策略添加
     * @Return
     * @Date 2019-09-23 09:56
     */
    @PostMapping("insert")
    @ApiOperation(value = "url策略---添加")
    public ServiceResponse browserUrlSafeStrategyInsert(@RequestBody BrowserUrlSafeStrategy strategy){
        return browserUrlSafeStrategyService.browserUrlSafeStrategyInsert(strategy);
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description url浏览器策略更新-发布-删除
     * @Return
     * @Date 2019-09-23 09:56
     */
    @PostMapping("update")
    @ApiOperation(value = "url策略更新-发布-删除")
    public ServiceResponse browserUrlSafeStrategyUpdate(@RequestBody BrowserUrlSafeStrategy strategy){
        return browserUrlSafeStrategyService.browserUrlSafeStrategyUpdate(strategy);
    }


    /**
     * @Method
     * @Description 策略详情
     * @Return
     */
    @PostMapping("detail")
    @ApiOperation(value = "策略--详情")
    public ServiceResponse browserUrlSafeStrategyInfoDetail(@RequestBody BrowserUrlSafeStrategy request){
        return browserUrlSafeStrategyService.browserUrlSafeStrategyInfoDetail(request);
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description url浏览器策略列表
     * @Return
     * @Date 2019-09-23 09:56
     */
    @PostMapping("list")
    @ApiOperation(value = "url策略----列表")
    public PageResponse browserUrlSafeStrategyList(@RequestBody BrowserStrategyRequest request){
        return browserUrlSafeStrategyService.browserUrlSafeStrategyList(request);
    }
}
