package com.demo.back.controller.system.authresourcecontroller.request;

import java.io.Serializable;

/**
 * @ClassName PermissionInsertParam
 * @Author yakun.shi
 * @Date 2019/6/17 17:32
 * @Version 1.0
 **/
public class PermissionInsertParam implements Serializable {

    private String roleId;
    private String resourceId;
    private String resourceRoleId;

    public String getResourceRoleId() {
        return resourceRoleId;
    }

    public void setResourceRoleId(String resourceRoleId) {
        this.resourceRoleId = resourceRoleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
