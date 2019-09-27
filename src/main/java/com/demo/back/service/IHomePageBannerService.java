package com.demo.back.service;


import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.business.homepagebanner.request.HomePageBannerRequest;
import com.demo.back.entity.HomePageBanner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: demo-back-admin
 * @description: 背景图service
 * @author: yaKun.shi
 * @create: 2019-07-29 09:13
 **/
public interface IHomePageBannerService {

    ServiceResponse bannerUpload(MultipartFile file) throws IOException;

    ServiceResponse bannerInsert(HomePageBanner request) throws Exception;

    PageResponse bannerList(HomePageBannerRequest request);

    ServiceResponse bannerUpdate(HomePageBanner request);

    ServiceResponse bannerClientList();
}
