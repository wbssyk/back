package com.demo.back.controller.business.homepagebanner;

import com.demo.back.common.logaop.operatelogaop.OperateLogCollect;
import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.business.homepagebanner.request.HomePageBannerRequest;
import com.demo.back.entity.HomePageBanner;
import com.demo.back.service.IHomePageBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author
 */

@RestController
@RequestMapping("banner")
public class HomePageBannerController {

    @Autowired
    private IHomePageBannerService homePageBannerService;

    @PostMapping("upload")
    @ApiOperation("上传视频或者图片")
    public ServiceResponse bannerUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return homePageBannerService.bannerUpload(file);
    }

    @PostMapping("insert")
    @ApiOperation("上传视频或者图片-存库")
    @OperateLogCollect(logValue = "上传视频或者图片",simpleValue = "视频和图片上传")
    public ServiceResponse bannerInsert(@RequestBody HomePageBanner request) throws Exception {
        ServiceResponse response = homePageBannerService.bannerInsert(request);
        return response;
    }

    @PostMapping("list")
    @ApiOperation("上传视频或者图片--列表")
    public PageResponse bannerList(@RequestBody HomePageBannerRequest request) {
        PageResponse pageResponse = homePageBannerService.bannerList(request);
        return pageResponse;
    }

    @PostMapping("update")
    @ApiOperation("上传视频或者图片--删除--审核")
    @OperateLogCollect(logValue = "上传视频或者图片功能",type = true)
    public ServiceResponse bannerUpdate(@RequestBody HomePageBanner request) {
        ServiceResponse serviceResponse = homePageBannerService.bannerUpdate(request);
        return serviceResponse;
    }

    @GetMapping("client/detail")
    @ApiOperation("登录轮播图---浏览器端返回后台配置轮播图列表")
    public ServiceResponse noticeClientList() {
        ServiceResponse serviceResponse = homePageBannerService.bannerClientList();
        return serviceResponse;
    }
}
