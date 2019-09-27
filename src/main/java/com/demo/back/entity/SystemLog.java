package com.demo.back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SystemLog
 * @Author yakun.shi
 * @Date 2019/6/27 9:11
 * @Version 1.0
 **/
public class SystemLog implements Serializable {

    private String id;
    private String ipAddress;
    private String loginUser;
    private String logValue;
    private String operateType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String className;
    private String methodName;
    private Integer checkStatus;
    private Date lastCheckTime;

    public String getId() {
        return id;
    }

    public SystemLog setId(String id) {
        this.id = id;
        return this;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public SystemLog setLoginUser(String loginUser) {
        this.loginUser = loginUser;
        return this;
    }

    public String getLogValue() {
        return logValue;
    }

    public SystemLog setLogValue(String logValue) {
        this.logValue = logValue;
        return this;
    }

    public String getOperateType() {
        return operateType;
    }

    public SystemLog setOperateType(String operateType) {
        this.operateType = operateType;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public SystemLog setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SystemLog setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public SystemLog setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public SystemLog setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
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

    @Override
    public String toString() {
        return "SystemLog{" +
                "id='" + id + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", loginUser='" + loginUser + '\'' +
                ", logValue='" + logValue + '\'' +
                ", operateType='" + operateType + '\'' +
                ", createTime=" + createTime +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", checkStatus=" + checkStatus +
                ", lastCheckTime=" + lastCheckTime +
                '}';
    }
}
