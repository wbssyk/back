package com.demo.back.controller.system.authrolecontroller;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authresourcecontroller.request.PermissionRequest;
import com.demo.back.controller.system.authrolecontroller.request.AuthRoleRequest;
import com.demo.back.entity.AuthRole;
import com.demo.back.service.IAuthResourcesService;
import com.demo.back.service.IAuthRoleService;
import com.demo.back.service.IAuthWebUrlService;
import com.demo.back.shiro.config.ICustomFilterChainDefinitions;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @ClassName AuthResourceController
 * @Author yakun.shi
 * @Date 2019/6/14 13:44
 * @Version 1.0
 **/
@RestController
@RequestMapping("role")
public class AuthRoleController {

    @Autowired
    private IAuthRoleService authRoleService;

    @Autowired
    private ICustomFilterChainDefinitions iCustomFilterChainDefinitions;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private IAuthResourcesService authResourcesService;

    @Autowired
    private IAuthWebUrlService webUrlService;

    @PostMapping("insert")
    @ApiOperation(value = "角色管理---新建角色")
    public ServiceResponse roleInsert(@RequestBody AuthRole request) {
        ServiceResponse serviceResponse = authRoleService.roleInsert(request);
        return serviceResponse;
    }

    @PostMapping("list")
    @ApiOperation(value = "角色管理--角色列表")
    public PageResponse roleDataList(@RequestBody AuthRoleRequest request) {
        PageResponse pageResponse = authRoleService.roleDataList(request);
        return pageResponse;
    }

    @PostMapping("update")
    @ApiOperation(value = "修改角色")
    @ApiIgnore
    public ServiceResponse roleUpdate(@RequestBody AuthRole request) {
        ServiceResponse serviceResponse = authRoleService.roleUpdate(request);
        return serviceResponse;
    }

    @PostMapping("delete")
    @ApiOperation(value = "角色管理---禁用--删除--解禁")
    public ServiceResponse roleDeleteByUserId(@RequestBody AuthRoleRequest request) {
        ServiceResponse serviceResponse = authRoleService.roleDeleteByUserIdAndRoleId(request);
        //更新用户角色权限
//        iCustomFilterChainDefinitions.updatePermission(shiroFilterFactoryBean,request.getRoleId());
        return serviceResponse;
    }

    @PostMapping("role_permission/insert")
    @ApiOperation(value = "角色--添加角色访问页面权限")
    public ServiceResponse roleWebPermissionInsert(@RequestBody PermissionRequest request) {
        ServiceResponse serviceResponse = webUrlService.roleWebPermissionInsert(request);
        return serviceResponse;
    }

    @PostMapping("role_permission/list")
    @ApiOperation(value = "角色--页面权限列表")
    public ServiceResponse roleWebResourceList(@RequestBody PermissionRequest request) {
        ServiceResponse serviceResponse = webUrlService.roleWebResourceList(request);
        return serviceResponse;
    }



//    @PostMapping("role_permission/insert")
    @ApiOperation(value = "角色--添加角色具体接口权限")
    @ApiIgnore
    public ServiceResponse rolePermissionInsert(@RequestBody PermissionRequest request) {
        ServiceResponse serviceResponse = authResourcesService.rolePermissionInsert(request);
        iCustomFilterChainDefinitions.updatePermission(shiroFilterFactoryBean,request.getRoleId());
        return serviceResponse;
    }

//    @PostMapping("role_permission/list")
    @ApiOperation(value = "角色--具体接口权限列表")
    @ApiIgnore
    public ServiceResponse roleResourceList(@RequestBody PermissionRequest request) {
        ServiceResponse serviceResponse = authResourcesService.roleResourceList(request);
        return serviceResponse;
    }



}
