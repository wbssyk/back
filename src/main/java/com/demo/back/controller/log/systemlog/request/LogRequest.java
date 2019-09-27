package com.demo.back.controller.log.systemlog.request;

import com.demo.back.common.page.PageRequest;

/**
 * @ClassName BrowserLogRequest
 * @Author yakun.shi
 * @Date 2019/7/9 14:59
 * @Version 1.0
 **/
public class LogRequest extends PageRequest {

    private String loginUser;
    private String operateType;

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}
