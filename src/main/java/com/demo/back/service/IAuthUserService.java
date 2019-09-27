package com.demo.back.service;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authusercontroller.request.AuthUserPageRequest;
import com.demo.back.controller.system.authusercontroller.request.LoginUserRequest;
import com.demo.back.entity.AuthUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface IAuthUserService {
    Set<String> findRolesByUserId(String userId);

    Set<String> findPermissionsByUserId(String userId);

    AuthUser findPasswordByLoginUser(String username);

    ServiceResponse login(LoginUserRequest authUser);

    ServiceResponse logout(HttpServletRequest request);

    ServiceResponse register( AuthUser request);

    ServiceResponse update(AuthUser request,HttpServletRequest servletRequest);

    PageResponse userList(AuthUserPageRequest request);

    ServiceResponse userDelete(AuthUserPageRequest request);

    ServiceResponse userDetail(AuthUserPageRequest request);
}
