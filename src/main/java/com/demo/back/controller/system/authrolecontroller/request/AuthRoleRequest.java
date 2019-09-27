package com.demo.back.controller.system.authrolecontroller.request;

import com.demo.back.common.page.PageRequest;

import java.io.Serializable;

/**
 * @ClassName PermissionRequest
 * @Author yakun.shi
 * @Date 2019/6/14 17:26
 * @Version 1.0
 **/
public class AuthRoleRequest extends PageRequest implements Serializable {
    private String username;
    private String roleName;
    private String userId;
    private String roleId;

    private String userRoleId;
    private Integer checkStatus;

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
