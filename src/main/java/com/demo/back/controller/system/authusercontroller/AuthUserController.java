package com.demo.back.controller.system.authusercontroller;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authrolecontroller.request.AuthRoleRequest;
import com.demo.back.controller.system.authusercontroller.request.AuthUserPageRequest;
import com.demo.back.entity.AuthUser;
import com.demo.back.service.IAuthResourcesService;
import com.demo.back.service.IAuthRoleService;
import com.demo.back.service.IAuthUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Method
 * @Author yakun.shi
 * @Description
 * @Return
 * @Date 2019/9/23 11:57
 */
@RestController
@RequestMapping("user")
public class AuthUserController {

    @Autowired
    private IAuthUserService authUserService;

    @Autowired
    private IAuthRoleService authRoleService;

    @Autowired
    private IAuthResourcesService authResourcesService;

    @PostMapping("register")
    @ApiOperation(value = "注册用户")
    public ServiceResponse register(@RequestBody AuthUser request) {
        ServiceResponse serviceResponse = authUserService.register(request);
        return serviceResponse;
    }

    @PostMapping("update")
    @ApiOperation(value = "更新用户")
    public ServiceResponse update(@RequestBody AuthUser request, HttpServletRequest servletRequest) {
        ServiceResponse serviceResponse = authUserService.update(request,servletRequest);
        return serviceResponse;
    }

    @PostMapping("list")
    @ApiOperation(value = "用户列表")
    public PageResponse userList(@RequestBody AuthUserPageRequest request) {
        PageResponse pageResponse = authUserService.userList(request);
        return pageResponse;
    }

    @PostMapping("delete")
    @ApiOperation(value = "用户禁用--解禁--删除")
    public ServiceResponse userDelete(@RequestBody AuthUserPageRequest request) {
        ServiceResponse serviceResponse = authUserService.userDelete(request);
        return serviceResponse;
    }

    @PostMapping("detail")
    @ApiOperation(value = "用户详情")
    public ServiceResponse userDetail(@RequestBody AuthUserPageRequest request) {
        ServiceResponse serviceResponse = authUserService.userDetail(request);
        return serviceResponse;
    }

    @PostMapping("user_role/list")
    @ApiOperation(value = "用户添加角色--选取角色列表")
    public ServiceResponse selectRoleListForUser(@RequestBody AuthRoleRequest request) {
        ServiceResponse serviceResponse = authRoleService.selectRoleListForUser(request);
        return serviceResponse;
    }

}
