package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.browsersafestrategy.request.BrowserStrategyRequest;
import com.demo.back.dao.BrowserUrlSafeStrategyMapper;
import com.demo.back.entity.BrowserUrlSafeStrategy;
import com.demo.back.service.IBrowserUrlSafeStrategyService;
import com.demo.back.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shiyakun
 * @since 2019-08-27
 */
@Service
public class BrowserUrlSafeStrategyServiceImpl  implements IBrowserUrlSafeStrategyService {

    @Autowired
    private BrowserUrlSafeStrategyMapper browserUrlSafeStrategyMapper;

    @Override
    public ServiceResponse browserUrlSafeStrategyInsert(BrowserUrlSafeStrategy strategy) {
        ServiceResponse serviceResponse = new ServiceResponse();
        strategy.setCreateTime(LocalDateTime.now());
        String id = UUIDUtil.getUUID();
        strategy.setId(id);
        Map<String, String> map = new HashMap<>(16);
        map.put("id", id);
        serviceResponse.setData(map);
        browserUrlSafeStrategyMapper.insert(strategy);
        return serviceResponse;
    }

    @Override
    public ServiceResponse browserUrlSafeStrategyUpdate(BrowserUrlSafeStrategy strategy) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(strategy.getId())) {
            serviceResponse.createErrorServcieResponse();
        }
        browserUrlSafeStrategyMapper.updateById(strategy);
        return serviceResponse;
    }

    @Override
    public PageResponse browserUrlSafeStrategyList(BrowserStrategyRequest request) {
        List<BrowserUrlSafeStrategy> browserSafeStrategies = browserUrlSafeStrategyMapper.browserUrlSafeStrategyList(request);
        Integer count = browserUrlSafeStrategyMapper.browserUrlSafeStrategyListCount(request);
        return PageResponse.createResponse(count, browserSafeStrategies, request);
    }

    @Override
    public ServiceResponse browserUrlSafeStrategyInfoDetail(BrowserUrlSafeStrategy request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            serviceResponse.createErrorServcieResponse();
        }
        Map<String,Object> map = browserUrlSafeStrategyMapper.browserUrlSafeStrategyInfoDetail(request);
        serviceResponse.setData(map);
        return serviceResponse;
    }
}
