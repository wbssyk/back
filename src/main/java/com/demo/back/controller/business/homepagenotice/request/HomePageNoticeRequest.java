package com.demo.back.controller.business.homepagenotice.request;

import com.demo.back.common.page.PageRequest;

/**
 * @program: demo-back-admin
 * @description: request
 * @author: yaKun.shi
 * @create: 2019-07-29 11:14
 **/
public class HomePageNoticeRequest extends PageRequest {
    private String noticeName;

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }
}
