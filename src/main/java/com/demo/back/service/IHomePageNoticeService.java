package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.business.homepagenotice.request.HomePageNoticeRequest;
import com.demo.back.entity.HomePageNotice;

public interface IHomePageNoticeService {
    ServiceResponse noticeInsert(HomePageNotice request);

    PageResponse noticeList(HomePageNoticeRequest request);

    ServiceResponse noticeUpdate(HomePageNotice request);

    ServiceResponse noticeClientList();
}
