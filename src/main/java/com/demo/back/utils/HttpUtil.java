package com.demo.back.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @ClassName HttpUtil
 * @Author yakun.shi
 * @Date 2019/7/2 10:16
 * @Version 1.0
 **/
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    /**
     * @return boolean
     * @Author yakun.shi
     * @Description //http文件上传
     * @Date 2019/7/2 10:27
     * @Param [file]
     **/
    public static String upload(MultipartFile file, String fileName, String filePath) {
        String path = null;
        try {
            path = uploadFile(file,fileName,filePath);
        } catch (Exception e) {
            logger.error("error---->{}", e.getMessage());
        }
        return path;
    }

    /**
     * @return boolean
     * @Author yakun.shi
     * @Description //http文件上传
     * @Date 2019/7/2 10:27
     * @Param [file]
     **/
    public static String upload(MultipartFile file, String filePath) {
        String path = null;
        try {
            path = uploadFile(file, file.getOriginalFilename(),filePath);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return path;
    }


    private static String uploadFile(MultipartFile multipartFile,  String fileName,String filePath) throws Exception {
        File targetFile = new File(filePath);
        System.out.println(targetFile.getAbsolutePath());
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        byte[] bytes = multipartFile.getBytes();
        String path = filePath + fileName;
        FileOutputStream out = new FileOutputStream(path);
        out.write(bytes);
        out.flush();
        out.close();
        return path;
    }

    public static void deleteTempFile(String filePath) {
        File file = new File(filePath);
        file.delete();
    }
}
