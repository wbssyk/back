package com.demo.back.controller.system.authresourcecontroller.request;

import com.demo.back.common.page.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PermissionRequest
 * @Author yakun.shi
 * @Date 2019/6/14 17:26
 * @Version 1.0
 **/
public class PermissionRequest extends PageRequest implements Serializable {
    private String roleName;
    private String roleId;
    private String resourceId;
    private String resourceRoleId;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private List<PermissionInsertParam> params;

    public List<PermissionInsertParam> getParams() {
        return params;
    }

    public void setParams(List<PermissionInsertParam> params) {
        this.params = params;
    }

    public String getResourceRoleId() {
        return resourceRoleId;
    }

    public void setResourceRoleId(String resourceRoleId) {
        this.resourceRoleId = resourceRoleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
