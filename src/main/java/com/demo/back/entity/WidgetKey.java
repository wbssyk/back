package com.demo.back.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @program: dg-back-admin
 * @description: 控件key类型
 * @author: yaKun.shi
 * @create: 2019-08-30 10:12
 **/
public class WidgetKey {

    private String id;
    private String keyName;
    private String keyDescribe;
    private String keyMark;
    private Integer checkStatus;
    private String reviewStatus;
    private Date lastCheckTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String remark;
    private String review;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyDescribe() {
        return keyDescribe;
    }

    public void setKeyDescribe(String keyDescribe) {
        this.keyDescribe = keyDescribe;
    }

    public String getKeyMark() {
        return keyMark;
    }

    public void setKeyMark(String keyMark) {
        this.keyMark = keyMark;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(Date lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
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
}
