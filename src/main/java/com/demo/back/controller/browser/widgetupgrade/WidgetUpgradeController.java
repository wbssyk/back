package com.demo.back.controller.browser.widgetupgrade;

import java.io.IOException;

import com.demo.back.common.logaop.operatelogaop.OperateLogCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.widgetupgrade.request.WidgetUpgradeRequest;
import com.demo.back.entity.WidgetUpgrade;
import com.demo.back.service.IWidgetUpgradeService;

import io.swagger.annotations.ApiOperation;

/**
 * @ClassName WidGetUpgradeController
 * @Author lin.cong
 * @Date 2019/7/11 13:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("widget")
public class WidgetUpgradeController {
	
    @Autowired
    private IWidgetUpgradeService widgetUpgradeService;

    @SuppressWarnings({"rawtypes"})
    @PostMapping("upload")
    @ApiOperation("上传控件")
    public ServiceResponse widgetUpload(@RequestParam("file") MultipartFile file) throws IOException {
        //控件上传信息存库
        return widgetUpgradeService.widgetUpload(file);
        
    }
    
    @SuppressWarnings("rawtypes")
	@PostMapping("insert")
    @ApiOperation("控件接口-存库")
    @OperateLogCollect(logValue = "上传控件软件包",simpleValue = "控件上传")
    public ServiceResponse widgetInsert(@RequestBody WidgetUpgrade request) throws Exception {
        ServiceResponse response = widgetUpgradeService.widgetInsert(request);
        return response;
    }

    @PostMapping("list")
    @ApiOperation("控件接口--列表")
    public PageResponse widgetList(@RequestBody WidgetUpgradeRequest request) {
        PageResponse pageResponse = widgetUpgradeService.widgetList(request);
        return pageResponse;
    }

    @SuppressWarnings("rawtypes")
	@PostMapping("update")
    @ApiOperation("控件接口--更新--删除--审核")
    @OperateLogCollect(logValue = "控件功能",type = true)
    public ServiceResponse widgetUpdate(@RequestBody WidgetUpgrade request) {
        ServiceResponse serviceResponse = widgetUpgradeService.widgetUpdate(request);
        return serviceResponse;
    }
    
    @SuppressWarnings("rawtypes")
	@GetMapping("client/detail")
    @ApiOperation("控件升级包获取--浏览器升级获取后台配置的升级包")
    public ServiceResponse browserForWidget() {
        ServiceResponse serviceResponse = widgetUpgradeService.browserForWidget();
        return serviceResponse;
    }
}
