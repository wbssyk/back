package com.demo.back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class WidgetUpgrade implements Serializable {
    private String id;

    private String widgetName;

    private String widgetDescribe;

    private String url;

    private String version;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String remark;

    private Integer checkStatus;

    private Date lastCheckTime;

    private String appType;

    private String reviewStatus;

    private String originalFilename;

    private Double doubleVersion;

    private String review;

    private String constraintStatus;

    private String md5;
    
    private String updateNewKey;
    
    private String keyAppName;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getKeyAppName() {
		return keyAppName;
	}

	public void setKeyAppName(String keyAppName) {
		this.keyAppName = keyAppName;
	}

	public String getUpdateNewKey() {
		return updateNewKey;
	}

	public void setUpdateNewKey(String updateNewKey) {
		this.updateNewKey = updateNewKey;
	}

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    private static final long serialVersionUID = 1L;

    public String getConstraintStatus() {
        return constraintStatus;
    }

    public void setConstraintStatus(String constraintStatus) {
        this.constraintStatus = constraintStatus;
    }

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


    public String getWidgetName() {
        return widgetName;
    }


    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }


    public String getWidgetDescribe() {
        return widgetDescribe;
    }


    public void setWidgetDescribe(String widgetDescribe) {
        this.widgetDescribe = widgetDescribe;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getVersion() {
        return version;
    }


    public void setVersion(String version) {
        this.version = version;
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


    public String getAppType() {
        return appType;
    }


    public void setAppType(String appType) {
        this.appType = appType;
    }


    public String getReviewStatus() {
        return reviewStatus;
    }


    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }


    public String getOriginalFilename() {
        return originalFilename;
    }


    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }


    public Double getDoubleVersion() {
        return doubleVersion;
    }


    public void setDoubleVersion(Double doubleVersion) {
        this.doubleVersion = doubleVersion;
    }

    @Override
    public String toString() {
        return "WidgetUpgrade [id=" + id + ", widgetName=" + widgetName + ", widgetDescribe=" + widgetDescribe
                + ", url=" + url + ", version=" + version + ", createTime=" + createTime + ", remark=" + remark
                + ", checkStatus=" + checkStatus + ", lastCheckTime=" + lastCheckTime + ", appType=" + appType
                + ", reviewStatus=" + reviewStatus + ", originalFilename="
                + originalFilename + ", doubleVersion=" + doubleVersion + ", review=" + review + ", constraintStatus="
                + constraintStatus + "]";
    }


}