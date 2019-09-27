package com.demo.back.dao;

import com.demo.back.controller.browser.browsersafestrategy.request.BrowserStrategyRequest;
import com.demo.back.entity.BrowserUrlSafeStrategy;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shiyakun
 * @since 2019-08-27
 */
public interface BrowserUrlSafeStrategyMapper{

    List<BrowserUrlSafeStrategy> browserUrlSafeStrategyList(@Param("request") BrowserStrategyRequest request);

    Integer browserUrlSafeStrategyListCount(@Param("request") BrowserStrategyRequest request);

    BrowserUrlSafeStrategy browserUrlSafeStrategyDetail(@Param("request") BrowserStrategyRequest request);

    void insert(@Param("request") BrowserUrlSafeStrategy strategy);

    void updateById(@Param("request") BrowserUrlSafeStrategy browserUrlSafeStrategy);

    List<Map<String,Object>> selectUrlSafeStrategyService();

    Map<String,Object> browserUrlSafeStrategyInfoDetail(@Param("request") BrowserUrlSafeStrategy request);
}
