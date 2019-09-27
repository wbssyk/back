package com.demo.back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author shiyakun
 * @since 2019-08-16
 */
public class BrowserSafeStrategy  {

    private static final long serialVersionUID = 1L;


    private String id;

    /**
     * 是否允许拷贝 0允许，1禁止
     */
    private Integer copyState;

    /**
     * 是否允许拖拽 0允许，1禁止
     */
    private Integer dragState;

    /**
     * 是否允许打印 0允许，1禁止
     */
    private Integer printState;

    /**
     * 是否允许下载 0允许，1禁止
     */
    private Integer downloadState;

    /**
     * 是否允许加水印 0允许，1禁止
     */
    private Integer watermarkState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 审核状态 1：未审核，2：通过，3：拒绝
     */
    private String reviewStatus;

    /**
     * 状态  0正常 1删除
     */
    private Integer checkStatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCopyState() {
        return copyState;
    }

    public void setCopyState(Integer copyState) {
        this.copyState = copyState;
    }

    public Integer getDragState() {
        return dragState;
    }

    public void setDragState(Integer dragState) {
        this.dragState = dragState;
    }

    public Integer getPrintState() {
        return printState;
    }

    public void setPrintState(Integer printState) {
        this.printState = printState;
    }

    public Integer getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(Integer downloadState) {
        this.downloadState = downloadState;
    }

    public Integer getWatermarkState() {
        return watermarkState;
    }

    public void setWatermarkState(Integer watermarkState) {
        this.watermarkState = watermarkState;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createtime) {
        this.createTime = createtime;
    }


    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Override
    public String toString() {
        return "BrowserSafeStrategy{" +
        ", id=" + id +
        ", copyState=" + copyState +
        ", dragState=" + dragState +
        ", printState=" + printState +
        ", downloadState=" + downloadState +
        ", watermarkState=" + watermarkState +
        ", createTime=" + createTime +
        ", reviewStatus=" + reviewStatus +
        ", checkStatus=" + checkStatus +
        "}";
    }
}
