package com.demo.back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class SystemClickCollect implements Serializable {

    private String id;

    private Long noticeClickNum;

    private Long videoClickNum;

    private Long advertClickNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String remark;

    private Integer checkStatus;

    private Date lastCheckTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNoticeClickNum() {
        return noticeClickNum;
    }

    public void setNoticeClickNum(Long noticeClickNum) {
        this.noticeClickNum = noticeClickNum;
    }

    public Long getVideoClickNum() {
        return videoClickNum;
    }

    public void setVideoClickNum(Long videoClickNum) {
        this.videoClickNum = videoClickNum;
    }

    public Long getAdvertClickNum() {
        return advertClickNum;
    }

    public void setAdvertClickNum(Long advertClickNum) {
        this.advertClickNum = advertClickNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(Date lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }
}