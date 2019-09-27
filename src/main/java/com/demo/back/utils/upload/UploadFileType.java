package com.demo.back.utils.upload;

/**
 * @program: browser_template
 * @description: 上传文件分类
 * @author: yaKun.shi
 * @create: 2019-09-20 11:31
 **/
public enum UploadFileType {
    BROWSER_TYPE(0,"上传的为浏览器包"),
    WIDGET_TYPE(1,"上传的为控件");

    private Integer value;
    private String msg;

    UploadFileType(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
