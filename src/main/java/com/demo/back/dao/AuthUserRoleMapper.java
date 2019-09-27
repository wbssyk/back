package com.demo.back.dao;

import com.demo.back.controller.system.authrolecontroller.request.AuthRoleRequest;
import com.demo.back.controller.system.authusercontroller.request.AuthUserPageRequest;
import com.demo.back.entity.AuthRole;
import com.demo.back.entity.AuthUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthUserRoleMapper {

    AuthUserRole selectCountByUserIdAndRoleId(@Param("userid") String userId, @Param("roleid") String roleId);

    void userRoleInsert(@Param("request") AuthRoleRequest request);

    void deleteByUserId(@Param("request") AuthUserPageRequest request);

    List<String> selectRoleIdByUserId(@Param("userid") String userId);

    List<AuthRole> selectRoleByUserId(@Param("userid") String userId);

    void updateById(@Param("request") AuthRole authRole);

}