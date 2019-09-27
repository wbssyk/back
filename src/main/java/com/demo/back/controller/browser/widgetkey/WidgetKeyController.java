package com.demo.back.controller.browser.widgetkey;


import com.demo.back.common.logaop.operatelogaop.OperateLogCollect;
import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.widgetkey.request.WidgetKeyRequest;
import com.demo.back.entity.WidgetKey;
import com.demo.back.service.IWidgeKeyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: dg-back-admin
 * @description: widgetkey控制层
 * @author: yaKun.shi
 * @create: 2019-08-30 10:28
 **/
@RestController
@RequestMapping("widgetKey")
public class WidgetKeyController {

    @Autowired
    private IWidgeKeyService widgeKeyService;


    @PostMapping("insert")
    @ApiOperation("控件类型-存库")
    @OperateLogCollect(logValue = "添加控件类型",simpleValue = "控件类型")
    public ServiceResponse widgetKeyInsert(@RequestBody WidgetKey request) {
        ServiceResponse response = widgeKeyService.widgetKeyInsert(request);
        return response;
    }

    @PostMapping("detail")
    @ApiOperation("控件类型-详情")
    public ServiceResponse widgetKeyDetail(@RequestBody WidgetKey request) {
        ServiceResponse response = widgeKeyService.widgetKeyDetail(request);
        return response;
    }

    @PostMapping("list")
    @ApiOperation("控件类型--列表")
    public PageResponse widgetKeyList(@RequestBody WidgetKeyRequest request) {
        PageResponse pageResponse = widgeKeyService.widgetKeyList(request);
        return pageResponse;
    }


    @GetMapping("upgrade/keyList")
    @ApiOperation("控件类型--控件升级选择控件列表")
    public ServiceResponse widgetKeyListForWidgetUpgrade() {
        ServiceResponse pageResponse = widgeKeyService.widgetKeyListForWidgetUpgrade();
        return pageResponse;
    }
    @PostMapping("update")
    @ApiOperation("控件类型--更新--删除--审核")
    @OperateLogCollect(logValue = "控件类型",type = true)
    public ServiceResponse widgetKeyUpdate(@RequestBody WidgetKey request) {
        ServiceResponse serviceResponse = widgeKeyService.widgetKeyUpdate(request);
        return serviceResponse;
    }
}
