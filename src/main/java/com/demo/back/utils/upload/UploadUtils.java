package com.demo.back.utils.upload;

import com.demo.back.entity.BrowserUpgrade;
import com.demo.back.entity.WidgetUpgrade;
import com.demo.back.utils.HttpUtil;
import com.demo.back.utils.StringUtil;
import com.demo.back.utils.ToolMD5;
import com.demo.back.utils.UUIDUtil;
import com.demo.back.utils.sftputil.SftpProperties;
import com.demo.back.utils.sftputil.SftpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;

import static com.demo.back.utils.upload.UploadFileType.BROWSER_TYPE;
import static com.demo.back.utils.upload.UploadFileType.WIDGET_TYPE;

/**
 * @program: browser_template
 * @description: 上传工具
 * @author: yaKun.shi
 * @create: 2019-09-20 10:33
 **/
@Component
public class UploadUtils {

    @Autowired
    private SftpUtils sftpUtils;

    @Autowired
    private UploadProperties uploadProperties;

    @Autowired
    private SftpProperties sftpProperties;


    private static final String HTTP_TYPE = "http";
    private static final String SFTP_TYPE = "sftp";

    /**
     * @Method
     * @Author yakun.shi
     * @Description 上传
     * @Return
     * @Date 2019/9/20 13:23
     */
    public UploadCommon upload(MultipartFile file) throws Exception {
        UploadCommon execute = execute(file,null);
        return execute;
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description 上传
     * @Return
     * @Date 2019/9/20 13:23
     */
    public UploadCommon upload(MultipartFile file,String filePath) throws Exception {
        UploadCommon execute = execute(file,filePath);
        return execute;
    }


    private UploadCommon execute(MultipartFile file,String filePath) throws Exception {
        String temp = HttpUtil.upload(file, "temp");
        InputStream inputStream = null;
        try {
            UploadCommon commonParam = getCommonParam(file,filePath);
            String fileName = commonParam.getFileName();
            inputStream = file.getInputStream();
            if (uploadProperties.getType().equals(HTTP_TYPE)) {
                // http上传文件
                String absolutePath = getAbsolutePath();
                if(filePath!=null){
                    filePath = absolutePath + uploadProperties.getPath()+filePath+"/";
                }else {
                    filePath = absolutePath + uploadProperties.getPath();
                }
                HttpUtil.upload(file, fileName, filePath);
            }
            if (uploadProperties.getType().equals(SFTP_TYPE)) {
                //sftp上传文件
                if(filePath==null){
                    sftpUtils.uploadFile(fileName, inputStream);
                }else {
                    sftpUtils.uploadFile(fileName, inputStream,filePath);
                }
            }
            String md5 = ToolMD5.encodeSha256HexFile(temp);
            HttpUtil.deleteTempFile(temp);
            commonParam.setMd5(md5);
            return commonParam;
        } catch (IOException e) {
            throw new Exception();
        } finally {
            inputStream.close();
        }
    }


    /**
     * @Method
     * @Author yakun.shi
     * @Description 获取公共参数
     * @Return
     * @Date 2019/9/20 13:23
     */
    private UploadCommon getCommonParam(MultipartFile file,String filePath) {
        String originalFilename = file.getOriginalFilename();
        String uuid = UUIDUtil.getUUID();
        String fileName = StringUtil.getRandomName(originalFilename, uuid);
        String downLoadUrl = null;
        if (uploadProperties.getType().equals(HTTP_TYPE)) {
            String path = uploadProperties.getDownLoadUrl() + uploadProperties.getPath();
            if(filePath==null){
                downLoadUrl = path + "/" + fileName;
            }else {
                downLoadUrl = path + filePath + "/" + fileName;
            }
        }
        if (uploadProperties.getType().equals(SFTP_TYPE)) {
            downLoadUrl = sftpProperties.getDownLoadUrl() + fileName;
        }
        UploadCommon uploadCommon = new UploadCommon();
        uploadCommon.setDownLoadUrl(downLoadUrl);
        uploadCommon.setFileName(fileName);
        uploadCommon.setUuid(uuid);
        uploadCommon.setOriginalFilename(originalFilename);
        return uploadCommon;
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description 获取项目绝对地址
     * @Return
     * @Date 2019/9/20 15:26
     */
    private String getAbsolutePath() {
        int flag = 3;
        String path = this.getClass().getResource("/").getPath();
        String realPath = null;
        try {
            // 转换成utf8，不然会出现乱码
            realPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] split = realPath.split("/");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 1; i < split.length - flag; i++) {
            stringBuffer.append(split[i]).append("/");
        }
        String s = stringBuffer.toString();
        String substring = s.substring(0, s.length() - 1);
        return substring;
    }

    public static void main(String[] args) {
        UploadUtils uploadUtils = new UploadUtils();
        String absolutePath = uploadUtils.getAbsolutePath();
        System.out.println(absolutePath);
    }




    /**
     * @Method
     * @Author yakun.shi
     * @Description 获取浏览器上传参数
     * @Return
     * @Date 2019/9/20 13:23
     */
    public BrowserUpgrade getBrowser(MultipartFile file) {
        UploadCommon uploadCommon = getCommonParam(file,null);
        BrowserUpgrade browserUpgrade = new BrowserUpgrade();
        browserUpgrade.setUrl(uploadCommon.getDownLoadUrl());
        browserUpgrade.setOriginalFilename(uploadCommon.getOriginalFilename());
        browserUpgrade.setId(uploadCommon.getUuid());
        browserUpgrade.setBrowserName(uploadCommon.getOriginalFilename());
        browserUpgrade.setFilename(uploadCommon.getFileName());
        return browserUpgrade;
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description 获取控件上传参数
     * @Return
     * @Date 2019/9/20 13:23
     */
    public WidgetUpgrade getWidget(MultipartFile file) {
        UploadCommon uploadCommon = getCommonParam(file,null);
        WidgetUpgrade widgetUpgrade = new WidgetUpgrade();
        widgetUpgrade.setUrl(uploadCommon.getDownLoadUrl());
        widgetUpgrade.setOriginalFilename(uploadCommon.getOriginalFilename());
        widgetUpgrade.setId(uploadCommon.getUuid());
        widgetUpgrade.setWidgetName(uploadCommon.getOriginalFilename());
        widgetUpgrade.setKeyAppName(uploadCommon.getOriginalFilename());
        widgetUpgrade.setFileName(uploadCommon.getFileName());
        return widgetUpgrade;
    }

}
