package com.demo.back.dao;


import com.demo.back.controller.system.authresourcecontroller.request.PermissionRequest;
import com.demo.back.entity.AuthResources;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface AuthResourcesMapper {

    List<HashMap<String,Object>> findRoleNameAndResourceUrl();

    void resourceInsert(@Param("request") AuthResources request);

    void resourceDetailByRoleId(@Param("roleid") String roleId);

    List<Map> resourceList(@Param("request") PermissionRequest request);

    Integer resourceListCount(@Param("request") PermissionRequest request);

    void resourceDelete(@Param("roleid") String roleId);

    List<Map<String, Object>> roleResourceList(@Param("type") Integer type);

    List<Map<String, Object>> roleResourceListByRoleId(@Param("request") PermissionRequest request);
}