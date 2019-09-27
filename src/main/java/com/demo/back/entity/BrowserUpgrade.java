package com.demo.back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class BrowserUpgrade implements Serializable {
    private String id;

    private String browserName;

    private String browserDescribe;

    private String url;

    private String version;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String remark;

    private Integer checkStatus;

    private Date lastCheckTime;

    private String browserType;

    private String reviewStatus;

    private String originalFilename;
    private String filename;

    private Double doubleVersion;

    private String filePath;

    private Double numVersion;

    private Integer versionMark;

    private String review;
    
    private String constraintStatus;

    private static final long serialVersionUID = 1L;

    private String md5;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getVersionMark() {
        return versionMark;
    }

    public void setVersionMark(Integer versionMark) {
        this.versionMark = versionMark;
    }

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

    public Double getNumVersion() {
        return numVersion;
    }

    public void setNumVersion(Double numVersion) {
        this.numVersion = numVersion;
    }

    public Double getDoubleVersion() {
        return doubleVersion;
    }

    public void setDoubleVersion(Double doubleVersion) {
        this.doubleVersion = doubleVersion;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName == null ? null : browserName.trim();
    }

    public String getBrowserDescribe() {
        return browserDescribe;
    }

    public void setBrowserDescribe(String browserDescribe) {
        this.browserDescribe = browserDescribe == null ? null : browserDescribe.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
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
        this.remark = remark == null ? null : remark.trim();
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

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

	@Override
	public String toString() {
		return "BrowserUpgrade [id=" + id + ", browserName=" + browserName + ", browserDescribe=" + browserDescribe
				+ ", url=" + url + ", version=" + version + ", createTime=" + createTime + ", remark=" + remark
				+ ", checkStatus=" + checkStatus + ", lastCheckTime=" + lastCheckTime + ", browserType=" + browserType
				+ ", reviewStatus=" + reviewStatus + ", originalFilename=" + originalFilename + ", doubleVersion="
				+ doubleVersion + ", numVersion=" + numVersion + ", review=" + review + ", constraintStatus="
				+ constraintStatus + "]";
	}
     
    
}