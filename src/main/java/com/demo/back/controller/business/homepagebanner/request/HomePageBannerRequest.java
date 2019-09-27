package com.demo.back.controller.business.homepagebanner.request;

import com.demo.back.common.page.PageRequest;

/**
 * @program: demo-back-admin
 * @description: 背景图request
 * @author: yaKun.shi
 * @create: 2019-07-29 16:16
 **/
public class HomePageBannerRequest extends PageRequest {

    private String bannerName;

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }
}
