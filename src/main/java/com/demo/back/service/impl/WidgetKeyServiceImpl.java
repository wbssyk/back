package com.demo.back.service.impl;


import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.widgetkey.request.WidgetKeyRequest;
import com.demo.back.dao.WidgetKeyMapper;
import com.demo.back.entity.WidgetKey;
import com.demo.back.exception.ParamNullException;
import com.demo.back.service.IWidgeKeyService;
import com.demo.back.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lin.cong
 * @ClassName WidgetUpgradeServiceImpl
 */
@Service
public class WidgetKeyServiceImpl implements IWidgeKeyService {

    @Autowired
    private WidgetKeyMapper widgetKeyMapper;

    @Override
    public ServiceResponse widgetKeyInsert(WidgetKey request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        String id = UUIDUtil.getUUID();
        request.setId(id);
        request.setKeyMark(id);
        request.setReviewStatus("1");
        request.setCreateTime(new Date());
        widgetKeyMapper.widgetKeyInsert(request);
        Map<String, String> map = new HashMap<>(16);
        map.put("id", id);
        serviceResponse.setData(map);
        return serviceResponse;
    }

    @Override
    public ServiceResponse widgetKeyDetail(WidgetKey request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            throw new ParamNullException();
        }
        Map<String,Object> widgetKey = widgetKeyMapper.widgetKeyDetail(request);
        serviceResponse.setData(widgetKey);
        return serviceResponse;
    }

    @Override
    public ServiceResponse widgetKeyListForWidgetUpgrade() {
        ServiceResponse serviceResponse = new ServiceResponse();
        List<Map<String,Object>> maps = widgetKeyMapper.widgetKeyListForWidgetUpgrade();
        serviceResponse.setData(maps);
        return serviceResponse;
    }

    @Override
    public PageResponse widgetKeyList(WidgetKeyRequest request) {
        List<Map<String, Object>> browserUpgrades = widgetKeyMapper.widgetKeyList(request);
        Integer count = widgetKeyMapper.widgetKeyListCount(request);
        return PageResponse.createResponse(count, browserUpgrades, request);
    }

    @Override
    public ServiceResponse widgetKeyUpdate(WidgetKey request) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            throw new ParamNullException();
        }
        widgetKeyMapper.widgetKeyUpdate(request);
        return serviceResponse;
    }


}
