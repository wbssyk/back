package com.demo.back.controller.system.authresourcecontroller.request;

import com.demo.back.common.page.PageRequest;

/**
 * @ClassName AuthWebUrlRequest
 * @Author yakun.shi
 * @Date 2019/7/10 9:08
 * @Version 1.0
 **/
public class AuthWebUrlRequest extends PageRequest {

   private String UrlName;

   private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getUrlName() {
        return UrlName;
    }

    public void setUrlName(String urlName) {
        UrlName = urlName;
    }
}
