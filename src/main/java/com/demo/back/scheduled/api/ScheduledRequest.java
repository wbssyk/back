package com.demo.back.scheduled.api;

/**
 * @ClassName ScheduledRequest
 * @Author yakun.shi
 * @Date 2019/7/1 11:14
 * @Version 1.0
 **/
public class ScheduledRequest {
    private String id;
    private Integer clickNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }
}
