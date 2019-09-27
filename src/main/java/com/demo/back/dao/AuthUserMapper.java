package com.demo.back.dao;

import com.demo.back.controller.system.authusercontroller.request.AuthUserPageRequest;
import com.demo.back.entity.AuthUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthUserMapper {
    int insert(@Param("request") AuthUser request);

    int updateByPrimaryKey(@Param("request")AuthUser request);

    List<AuthUser> findRolesByUserId(@Param("userid") String userId);

    List<AuthUser> findPermissionsByUserId(@Param("userid") String userId);

    AuthUser findPasswordByLoginUser(@Param("loginuser") String loginUser);

    List<AuthUser> userList(@Param("request") AuthUserPageRequest request);

    Integer userListCount(@Param("request") AuthUserPageRequest request);

    void userDelete(@Param("request") AuthUserPageRequest request);

    AuthUser userDetail(@Param("id") String id);

    List<AuthUser> findByRoleId(@Param("roleid") String roleId);

    AuthUser selectByUserId(@Param("userid") String id);
}