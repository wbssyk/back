package com.demo.back.controller.browser.browsersafestrategy.request;

import com.demo.back.common.page.PageRequest;

/**
 * @program: browser_template
 * @description: request
 * @author: yaKun.shi
 * @create: 2019-09-23 09:08
 **/
public class BrowserStrategyRequest extends PageRequest {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
