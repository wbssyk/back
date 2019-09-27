package com.demo.back.controller.log.browserlog.request;

import com.demo.back.common.page.PageRequest;
import io.swagger.models.auth.In;

/**
 * @ClassName BrowserLogRequest
 * @Author yakun.shi
 * @Date 2019/7/9 14:59
 * @Version 1.0
 **/
public class BrowserLogRequest extends PageRequest {

    private String userName;
    private Integer operateType;
    private String title;
    private String url;
    private String logValue;
    private String ipAddress;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogValue() {
        return logValue;
    }

    public void setLogValue(String logValue) {
        this.logValue = logValue;
    }
}
