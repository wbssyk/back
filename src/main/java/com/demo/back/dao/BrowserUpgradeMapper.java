package com.demo.back.dao;

import com.demo.back.controller.browser.browserupgrade.request.BrowserUpgradeRequest;
import com.demo.back.entity.BrowserUpgrade;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BrowserUpgradeMapper {
    int insert(@Param("request") BrowserUpgrade record);

    List<Map<String,Object>> browserList(@Param("request") BrowserUpgradeRequest request);

    Integer browserListCount(@Param("request") BrowserUpgradeRequest request);

    void browserUpdate(@Param("request") BrowserUpgrade record);

    BrowserUpgrade selectById(@Param("request") BrowserUpgrade request);

    BrowserUpgrade selectMaxVersion();

    BrowserUpgrade selectUnReviewByName(@Param("browserName") String originalFilename,@Param("browserType") String browserType);

    BrowserUpgrade selectMaxVersionByType(@Param("browserType") String browserType);
}