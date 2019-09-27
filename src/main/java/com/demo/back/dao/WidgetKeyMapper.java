package com.demo.back.dao;

import com.demo.back.controller.browser.widgetkey.request.WidgetKeyRequest;
import com.demo.back.entity.WidgetKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WidgetKeyMapper {
    void widgetKeyInsert(@Param("request") WidgetKey request);

    List<Map<String,Object>> widgetKeyList(@Param("request") WidgetKeyRequest request);

    Integer widgetKeyListCount(@Param("request") WidgetKeyRequest request);

    void widgetKeyUpdate(@Param("request") WidgetKey request);

    Map<String,Object> widgetKeyDetail(@Param("request") WidgetKey request);

    List<Map<String,Object>> widgetKeyListForWidgetUpgrade();
}