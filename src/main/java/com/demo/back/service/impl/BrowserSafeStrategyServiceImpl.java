package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.browsersafestrategy.request.BrowserStrategyRequest;
import com.demo.back.dao.BrowserSafeStrategyMapper;
import com.demo.back.dao.BrowserUrlSafeStrategyMapper;
import com.demo.back.entity.BrowserSafeStrategy;
import com.demo.back.service.IBrowserSafeStrategyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shiyakun
 * @since 2019-08-16
 */
@Service
public class BrowserSafeStrategyServiceImpl  implements IBrowserSafeStrategyService {

    @Autowired
    private BrowserSafeStrategyMapper browserSafeStrategyMapper;

    @Autowired
    private BrowserUrlSafeStrategyMapper browserUrlSafeStrategyMapper;

    @Override
    public PageResponse browserSafeStrategyInfoList(BrowserStrategyRequest request) {
        List<BrowserSafeStrategy> BrowserLog = browserSafeStrategyMapper.browserSafeStrategyInfoList(request);
        Integer count = browserSafeStrategyMapper.browserSafeStrategyInfoListCount(request);
        return PageResponse.createResponse(count, BrowserLog, request);
    }

    @Override
    public ServiceResponse browserSafeStrategyInfoUpdate(BrowserSafeStrategy request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            serviceResponse.createErrorServcieResponse();
        }
        browserSafeStrategyMapper.browserSafeStrategyInfoUpdate(request);
        return serviceResponse;
    }

    @Override
    public ServiceResponse selectSafeStrategyService() {
        ServiceResponse serviceResponse = new ServiceResponse();
        List<Map<String,Object>> browserSafeStrategies = browserSafeStrategyMapper.selectSafeStrategyService();
        List<Map<String,Object>> browserUrlSafeStrategies = browserUrlSafeStrategyMapper.selectUrlSafeStrategyService();
        Map<String, Object> map = new HashMap<>(16);
        map.put("defaultclient", browserSafeStrategies);
        map.put("appclients", browserUrlSafeStrategies);
        serviceResponse.setData(map);
        return serviceResponse;
    }

    @Override
    public ServiceResponse browserSafeStrategyInfoDetail(BrowserSafeStrategy request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            serviceResponse.createErrorServcieResponse();
        }
        Map<String,Object> map = browserSafeStrategyMapper.browserSafeStrategyInfoDetail(request);
        serviceResponse.setData(map);
        return serviceResponse;
    }

}
