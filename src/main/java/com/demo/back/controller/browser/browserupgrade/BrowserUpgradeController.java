package com.demo.back.controller.browser.browserupgrade;

import com.demo.back.common.logaop.operatelogaop.OperateLogCollect;
import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.browserupgrade.request.BrowserUpgradeRequest;
import com.demo.back.entity.BrowserUpgrade;
import com.demo.back.service.IBrowserUpgradeService;
import com.demo.back.utils.StringUtil;
import com.demo.back.utils.ToolMD5;
import com.demo.back.utils.UUIDUtil;
import com.demo.back.utils.ftputil.FtpProperties;

import com.demo.back.utils.sftputil.SftpProperties;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName BrowserUpgradeController
 * @Author yakun.shi
 * @Date 2019/7/2 9:48
 * @Version 1.0
 **/
@RestController
@RequestMapping("browser")
public class BrowserUpgradeController {


    @Autowired
    private IBrowserUpgradeService browserUpgradeService;

    @SuppressWarnings({"rawtypes", "unchecked", "unused"})
    @PostMapping("upload")
    @ApiOperation("上传浏览器包")
    public ServiceResponse browserUpload(@RequestParam("file") MultipartFile file) {
        ServiceResponse response = new ServiceResponse();
        //浏览器上传包信息存库
        return browserUpgradeService.browserUpload(file);
    }

    @PostMapping("insert")
    @ApiOperation("浏览器接口-存库")
    @OperateLogCollect(logValue = "上传客户端软件包",simpleValue = "客户端上传")
    public ServiceResponse browserInsert(@RequestBody BrowserUpgrade request) {
        ServiceResponse response = browserUpgradeService.browserInsert(request);
        return response;
    }

    @PostMapping("list")
    @ApiOperation("浏览器接口--列表")
    public PageResponse browserList(@RequestBody BrowserUpgradeRequest request) {
        PageResponse pageResponse = browserUpgradeService.browserList(request);
        return pageResponse;
    }


    @PostMapping("update")
    @ApiOperation("浏览器接口--更新--删除--审核")
    @OperateLogCollect(logValue = "客户端功能",type = true)
    public ServiceResponse browserUpdate(@RequestBody BrowserUpgrade request) {
        ServiceResponse serviceResponse = browserUpgradeService.browserUpdate(request);
        return serviceResponse;
    }


    @GetMapping("client/detail")
    @ApiOperation("浏览器升级包获取--浏览器升级获取后台配置的升级包")
    public ServiceResponse browserForClient(@RequestParam("browserType") String browserType,@RequestParam("versionMark") Integer versionMark) {
        ServiceResponse serviceResponse = browserUpgradeService.browserForClient(browserType,versionMark);
        return serviceResponse;
    }
}
