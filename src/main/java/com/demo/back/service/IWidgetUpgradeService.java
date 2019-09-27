package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.widgetupgrade.request.WidgetUpgradeRequest;
import com.demo.back.entity.WidgetUpgrade;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IWidgetUpgradeService {

    PageResponse widgetList(WidgetUpgradeRequest request);

    @SuppressWarnings("rawtypes")
	ServiceResponse widgetUpdate(WidgetUpgrade request);
    
    @SuppressWarnings("rawtypes")
	ServiceResponse widgetInsert(WidgetUpgrade request) throws Exception;

    @SuppressWarnings("rawtypes")
	ServiceResponse widgetUpload(MultipartFile file) throws IOException;
    
    @SuppressWarnings("rawtypes")
	ServiceResponse browserForWidget();
}
