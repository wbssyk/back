package com.demo.back.dao;


import com.demo.back.controller.system.authrolecontroller.request.AuthRoleRequest;
import com.demo.back.entity.AuthRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AuthRoleMapper {

    void roleInsert(@Param("request") AuthRole request);

    void roleUpdate(@Param("id") String id);

    void roleDelete(@Param("request") AuthRoleRequest request);

    List<Map> selectRoleListForUser(@Param("request") AuthRoleRequest request);

    void roleDeleteByUserIdAndRoleId(@Param("userid") String userId, @Param("roelid") String roleId);

    List<Map> roleDataList(@Param("request") AuthRoleRequest request);

    Integer roleDataListCount(@Param("request") AuthRoleRequest request);

    AuthRole selectByRoleName(@Param("request") AuthRole request);

    AuthRole selectByRoelId(@Param("roleId") String roleId);
}