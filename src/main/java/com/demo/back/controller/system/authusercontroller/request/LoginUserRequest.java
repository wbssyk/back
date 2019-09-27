package com.demo.back.controller.system.authusercontroller.request;

/**
 * @program: browser_template
 * @description: 用户登录
 * @author: yaKun.shi
 * @create: 2019-09-23 11:52
 **/
public class LoginUserRequest {

    private String loginUser;

    private String password;

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
