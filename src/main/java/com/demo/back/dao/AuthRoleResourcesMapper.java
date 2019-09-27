package com.demo.back.dao;


import com.demo.back.controller.system.authresourcecontroller.request.PermissionRequest;
import com.demo.back.controller.system.authresourcecontroller.request.PermissionInsertParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthRoleResourcesMapper {
    Integer selectCountByResourceIdAndRoleId(@Param("resourceid") String resourceId,
                                             @Param("roleid") String roleId);

    void rolePermissionInsert(@Param("request") PermissionRequest request);

    void deleteByBatchId(@Param("request") List<PermissionInsertParam> params);

    void rolePermissionBatchInsert(@Param("request") List<PermissionInsertParam> params);

    void deleteByRoleId(@Param("roleid") String roleId);
}