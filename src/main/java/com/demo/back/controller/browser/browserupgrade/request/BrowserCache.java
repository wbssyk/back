package com.demo.back.controller.browser.browserupgrade.request;

/**
 * @program: demo-back-admin
 * @description: browser缓存结构
 * @author: yaKun.shi
 * @create: 2019-08-01 09:43
 **/
public class BrowserCache {

    private String downLoadUrl;
    private String constraintStatus;

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getConstraintStatus() {
        return constraintStatus;
    }

    public void setConstraintStatus(String constraintStatus) {
        this.constraintStatus = constraintStatus;
    }
}
