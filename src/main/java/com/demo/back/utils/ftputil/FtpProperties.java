package com.demo.back.utils.ftputil;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FtpProperties
 * @Author yakun.shi
 * @Date 2019/7/2 11:38
 * @Version 1.0
 **/
@Configuration
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {

    private String user;
    private String password;
    private String ip;
    private String filePath;
    private int port;
    private String downLoadUrl;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    @Override
    public String toString() {
        return "Ftp信息{" +
                "  服务ip:'" + ip + '\'' +
                ", 端口号port:" + port +
                ", 文件地址filePath:'" + filePath + '\'' +
                ", 下载地址downLoadUrl:'" + downLoadUrl + '\'' +
                '}';
    }
}
