package com.demo.back.dao;

import com.demo.back.controller.system.authresourcecontroller.request.AuthWebUrlRequest;
import com.demo.back.controller.system.authresourcecontroller.request.PermissionRequest;
import com.demo.back.entity.AuthWebUrl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AuthWebUrlMapper {
    void resourceInsert(@Param("request") AuthWebUrl request);

    void resourceDelete(@Param("request") AuthWebUrl request);

    void resourceUpdate(@Param("request") AuthWebUrl request);

    List<Map<String,Object>> resourceList(@Param("request") AuthWebUrlRequest request);

    Integer resourceListCount(@Param("request") AuthWebUrlRequest request);

    List<Map<String, Object>> selectForLeftSideBar(@Param("request") PermissionRequest request);

    List<Map<String,Object>> selectAllForLeftSideBar();

    AuthWebUrl selectByHtmlUrl(@Param("htmlUrl") String htmlUrl);
}