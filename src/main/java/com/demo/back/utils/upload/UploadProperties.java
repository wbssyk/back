package com.demo.back.utils.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @program: browser_template
 * @description: upload配置文件读取
 * @author: yaKun.shi
 * @create: 2019-09-20 10:16
 **/
@Configuration
@PropertySource("classpath:upload.properties")
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {

    private String type = "http";
    private String path = "/upload";
    private String downLoadUrl = "http://localhost:9080";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }
}
