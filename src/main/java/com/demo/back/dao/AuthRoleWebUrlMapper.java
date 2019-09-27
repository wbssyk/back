package com.demo.back.dao;

import com.demo.back.controller.system.authresourcecontroller.request.PermissionInsertParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthRoleWebUrlMapper {

    void deleteByRoleId(@Param("roleid") String roleId);

    void roleWebPermissionBatchInsert(@Param("request") List<PermissionInsertParam> params);
}