package com.demo.back.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.UUID;

/**
 * @ClassName UUIDUtil
 * @Author yakun.shi
 * @Date 2019/6/14 15:52
 * @Version 1.0
 **/
public class UUIDUtil {

    public static String getUUID(){
        String s = UUID.randomUUID().toString();
        String replace = StringUtils.replace(s, "-", "");
        return replace;
    }

    public static void main(String[] args) {
        String uuid = getUUID();
        File file = new File("D:/Program Files/apache-tomcat-8.5.40-windows-x64/apache-tomcat-8.5.40/webapps/upload/");
        file.mkdirs();
        System.out.println(uuid);
    }
}
