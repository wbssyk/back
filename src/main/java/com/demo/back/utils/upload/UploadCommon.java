package com.demo.back.utils.upload;

/**
 * @program: browser_template
 * @description: upload
 * @author: yaKun.shi
 * @create: 2019-09-20 11:27
 **/
public class UploadCommon {

    private String md5;

    private String uuid;

    private String originalFilename;
    private String fileName;
    private String downLoadUrl;



    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }
}
