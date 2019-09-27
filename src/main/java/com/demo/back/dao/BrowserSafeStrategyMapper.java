package com.demo.back.dao;

import com.demo.back.controller.browser.browsersafestrategy.request.BrowserStrategyRequest;
import com.demo.back.entity.BrowserSafeStrategy;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shiyakun
 * @since 2019-08-16
 */
public interface BrowserSafeStrategyMapper  {

	List<BrowserSafeStrategy> browserSafeStrategyInfoList(@Param("request") BrowserStrategyRequest request);

    Integer browserSafeStrategyInfoListCount(@Param("request") BrowserStrategyRequest request);
    
    void browserSafeStrategyInfoUpdate(@Param("request") BrowserSafeStrategy request);

    List<Map<String,Object>> selectSafeStrategyService();

    Map<String,Object> browserSafeStrategyInfoDetail(@Param("request") BrowserSafeStrategy request);
}
