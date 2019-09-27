package com.demo.back.utils.ftputil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName FtpUtils
 * @Author yakun.shi
 * @Date 2019/7/2 11:35
 * @Version 1.0
 **/
@Component
public class FtpUtils {

    private static Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    @Autowired
    private FtpProperties properties;

    /**
     * @return boolean
     * @Author yakun.shi
     * @Description //ftp上传
     * @Date 2019/7/2 11:43
     * @Param [originFileName, input]
     **/
    public boolean uploadFile(String originFileName, InputStream inputStream , String filePath) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            logger.info("建立ftp连接----->{}", properties);
            int reply;
            // 连接FTP服务器
            ftp.connect(properties.getIp(), properties.getPort());
            // 登录
            ftp.login(properties.getUser(), properties.getPassword());
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                logger.info("ftp连接失败");
                return success;
            }
            logger.info("ftp连接成功");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            if(StringUtils.isEmpty(filePath)){
                filePath = properties.getFilePath();
            }
            ftp.makeDirectory(filePath);
            ftp.changeWorkingDirectory(filePath);
            //设置缓存池提高上传速度
            ftp.setBufferSize(1024 * 1024);
            //进行流包装 提高上传速度
            BufferedInputStream input = new BufferedInputStream(inputStream);
            logger.info("开始文件传输");
            ftp.storeFile(originFileName, input);
            input.close();
            ftp.logout();
            success = true;
            logger.info("文件传输成功");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("文件传输失败");
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

}
