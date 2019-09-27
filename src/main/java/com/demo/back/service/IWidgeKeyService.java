package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.widgetkey.request.WidgetKeyRequest;
import com.demo.back.entity.WidgetKey;

public interface IWidgeKeyService {
    ServiceResponse widgetKeyInsert(WidgetKey request);

    PageResponse widgetKeyList(WidgetKeyRequest request);

    ServiceResponse widgetKeyUpdate(WidgetKey request);

    ServiceResponse widgetKeyDetail(WidgetKey request);

    ServiceResponse widgetKeyListForWidgetUpgrade();
}
