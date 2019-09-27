package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.browsersafestrategy.request.BrowserStrategyRequest;
import com.demo.back.entity.BrowserUrlSafeStrategy;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shiyakun
 * @since 2019-08-27
 */
public interface IBrowserUrlSafeStrategyService {

    ServiceResponse browserUrlSafeStrategyInsert(BrowserUrlSafeStrategy strategy);

    ServiceResponse browserUrlSafeStrategyUpdate(BrowserUrlSafeStrategy strategy);

    PageResponse browserUrlSafeStrategyList(BrowserStrategyRequest request);

    ServiceResponse browserUrlSafeStrategyInfoDetail(BrowserUrlSafeStrategy request);
}
