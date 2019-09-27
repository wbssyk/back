package com.demo.back.controller.business.homepagenotice;

import com.demo.back.common.logaop.operatelogaop.OperateLogCollect;
import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.business.homepagenotice.request.HomePageNoticeRequest;
import com.demo.back.entity.HomePageNotice;
import com.demo.back.service.IHomePageNoticeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author
 */

@RestController
@RequestMapping("notice")
public class HomePageNoticeController {

    @Autowired
    private IHomePageNoticeService homePageNoticeService;

    @PostMapping("insert")
    @ApiOperation("公告接口-存库")
    @OperateLogCollect(logValue = "新加首页公告",simpleValue = "首页公告")
    public ServiceResponse noticeInsert(@RequestBody HomePageNotice request) {
        ServiceResponse response = homePageNoticeService.noticeInsert(request);
        return response;
    }

    @PostMapping("list")
    @ApiOperation("公告接口--列表")
    public PageResponse noticeList(@RequestBody HomePageNoticeRequest request) {
        PageResponse pageResponse = homePageNoticeService.noticeList(request);
        return pageResponse;
    }


    @PostMapping("update")
    @ApiOperation("公告接口--删除--审核")
    @OperateLogCollect(logValue = "首页公告功能",type = true)
    public ServiceResponse noticeUpdate(@RequestBody HomePageNotice request) {
        ServiceResponse serviceResponse = homePageNoticeService.noticeUpdate(request);
        return serviceResponse;
    }

    @GetMapping("client/detail")
    @ApiOperation("消息---浏览器端返回后台配置消息列表")
    public ServiceResponse noticeClientList() {
        ServiceResponse serviceResponse = homePageNoticeService.noticeClientList();
        return serviceResponse;
    }
}
