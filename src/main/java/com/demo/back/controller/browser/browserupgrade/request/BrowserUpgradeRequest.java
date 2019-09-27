package com.demo.back.controller.browser.browserupgrade.request;

import com.demo.back.common.page.PageRequest;

/**
 * @ClassName BrowserUpgradeRequest
 * @Author yakun.shi
 * @Date 2019/7/10 16:38
 * @Version 1.0
 **/
public class BrowserUpgradeRequest extends PageRequest {

    private String browserName;

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }
}
