package com.demo.back.utils.sftputil;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;

/**
 * @ClassName FtpUtils
 * @Author yakun.shi
 * @Date 2019/7/2 11:35
 * @Version 1.0
 **/
@Component
public class SftpUtils {

    private static Logger logger = LoggerFactory.getLogger(SftpUtils.class);

    @Autowired
    private SftpConnect sftpConnect;

    @Autowired
    private SftpProperties config;

    /**
     * @Method
     * @Author yakun.shi
     * @Description 将inputStream上传到指定路径下(单级或多级目录)
     * @Return
     * @Date 2019/8/26 14:14
     */
    public boolean uploadFile(String fileName, InputStream inputStream, String targetPath) {
        ChannelSftp sftp = null;
        try {
            sftp = sftpConnect.createSftp();
            try {
                sftp.cd(targetPath);
            } catch (SftpException e) {
                // 服务器没有此文件
                logger.info("Create new path {}", config.getRoot() + targetPath);
                sftp.cd(config.getSftpRoot());
                // 新建文件夹
                this.createDirs(targetPath, sftp);
            }
            sftp.put(inputStream, fileName);
            return true;
        }catch (Exception e){
            logger.error("Upload file failure. TargetPath: {}", targetPath, e);
        }finally {
            sftpConnect.disconnect(sftp);
        }
      return false;
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description 将inputStream上传到指定路径下(单级或多级目录)
     * @Return
     * @Date 2019/8/26 14:14
     */
    public boolean uploadFile(String fileName,InputStream inputStream) {
        ChannelSftp sftp = null;
        try {
            sftp = sftpConnect.createSftp();
            sftp.cd(config.getSftpRoot());
            sftp.put(inputStream, fileName);
            return true;
        }catch (Exception e){
            logger.error("Upload file failure. TargetPath: {}", e);
        }
        finally {
            sftpConnect.disconnect(sftp);
        }
        return false;
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description 下载文件
     * @Return
     * @Date 2019/8/26 14:15
     */
    public File downloadFile(String targetPath) throws Exception {
        ChannelSftp sftp = sftpConnect.createSftp();
        OutputStream outputStream = null;
        try {
            sftp.cd(config.getRoot());
            logger.info("Change path to {}", config.getRoot());

            File file = new File(targetPath.substring(targetPath.lastIndexOf("/") + 1));

            outputStream = new FileOutputStream(file);
            sftp.get(targetPath, outputStream);
            logger.info("Download file success. TargetPath: {}", targetPath);
            return file;
        } catch (Exception e) {
            logger.error("Download file failure. TargetPath: {}", targetPath, e);
            throw new Exception("Download File failure");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            sftpConnect.disconnect(sftp);
        }
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description 删除文件
     * @Return
     * @Date 2019/8/26 14:16
     */
    public boolean deleteFile(String targetPath) throws Exception {
        ChannelSftp sftp = null;
        try {
            sftp = sftpConnect.createSftp();
            sftp.cd(config.getRoot());
            sftp.rm(targetPath);
            return true;
        } catch (Exception e) {
            logger.error("Delete file failure. TargetPath: {}", targetPath, e);
            throw new Exception("Delete File failure");
        } finally {
            sftpConnect.disconnect(sftp);
        }
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description 创建多级目录
     * @Return
     * @Date 2019/8/26 14:15
     */
    private boolean createDirs(String dirPath, ChannelSftp sftp) {
        if (dirPath != null && !dirPath.isEmpty()
                && sftp != null) {
            String[] dirs = Arrays.stream(dirPath.split("/"))
                    .filter(StringUtils::isNotBlank)
                    .toArray(String[]::new);

            for (String dir : dirs) {
                try {
                    sftp.cd(dir);
                    logger.info("Change directory {}", dir);
                } catch (Exception e) {
                    try {
                        String filePath = config.getRoot();
                        sftp.mkdir(dir);
                        logger.info("Create directory {}", filePath + dir);
                    } catch (SftpException e1) {
                        logger.error("Create directory failure, directory:{}", dir, e1);
                        e1.printStackTrace();
                    }
                    try {
                        sftp.cd(dir);
                        logger.info("Change directory {}", dir);
                    } catch (SftpException e1) {
                        logger.error("Change directory failure, directory:{}", dir, e1);
                        e1.printStackTrace();
                    }
                }
            }
            return true;
        }
        return false;
    }
}
