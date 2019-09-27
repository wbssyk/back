package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.browsersafestrategy.request.BrowserStrategyRequest;
import com.demo.back.entity.BrowserSafeStrategy;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shiyakun
 * @since 2019-08-16
 */
public interface IBrowserSafeStrategyService{

	PageResponse browserSafeStrategyInfoList(BrowserStrategyRequest request);
	
	@SuppressWarnings("rawtypes")
	ServiceResponse browserSafeStrategyInfoUpdate(BrowserSafeStrategy request);
	
	@SuppressWarnings("rawtypes")
	ServiceResponse selectSafeStrategyService();

    ServiceResponse browserSafeStrategyInfoDetail(BrowserSafeStrategy request);
}
