package com.demo.back.dao;

import com.demo.back.controller.browser.widgetupgrade.request.WidgetUpgradeRequest;
import com.demo.back.entity.WidgetUpgrade;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WidgetUpgradeMapper {
    int insert(@Param("request") WidgetUpgrade record);

    List<WidgetUpgrade> widgetList(@Param("request") WidgetUpgradeRequest request);

    Integer widgetListCount(@Param("request") WidgetUpgradeRequest request);

    void widgetUpdate(@Param("request") WidgetUpgrade record);
    
    List<WidgetUpgrade> selectWidgetUpgradeByAppType(@Param("appType") String appType);
    
    List<WidgetUpgrade> selectWidgetUpgradeByMaxVersion(@Param("appType") String appType);
    
    WidgetUpgrade selectById(@Param("id") String id);
    
    List<WidgetUpgrade> selectWidgetUpgradeByFilename(@Param("widgetName") String widgetName,@Param("appType") String appType);
}