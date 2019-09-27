package com.demo.back.controller.system.authusercontroller.request;

import com.demo.back.common.page.PageRequest;

import java.io.Serializable;


/**
 * @ClassName AuthUserPageRequest
 * @Author yakun.shi
 * @Date 2019/6/14 16:18
 * @Version 1.0
 **/
public class AuthUserPageRequest extends PageRequest implements Serializable {
    private String id;

    private String username;

    private String loginUser;

    private String createTime;

    private String userId;

    private Integer checkStatus;

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
