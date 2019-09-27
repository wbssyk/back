package com.demo.back.utils;


/**
 * @program: demo-back-admin
 * @description: 文件字符串工具
 * @author: yaKun.shi
 * @create: 2019-07-29 14:27
 **/
public class StringUtil {

    private static final String[] videos
            = {"mp4", "avi", "rmvb", "rm", "MPEG", "MPG", "DAT", "MOV", "QT", "ASF", "WMV"};
    private static final String[] photos
            = {"jpg", "png", "bmp", "tif", "gif", "pcx", "webp", "tga", "JPEG"};
    private static final String[] exe
            = {"exe","rpm"};

    public static boolean isVideo(String name) {
        return handle(name, videos);
    }

    public static boolean isPhoto(String name) {
        return handle(name, photos);
    }

    public static boolean isExe(String name) {
        return handle(name, exe);
    }

    public static String getRandomName(String name, String uuid) {
        return split(name, uuid);
    }

    public static String getRandomName(String name) {
        return split(name, null);
    }

    public static String getSuffixId(String name) {
        String[] split = name.split("_");
        String s = split[split.length-1];
        return s.split(".")[0];
    }

    private static boolean handle(String name, String[] banner) {
        boolean flag = false;
        if (org.apache.commons.lang3.StringUtils.isEmpty(name)) {
            throw new NullPointerException();
        }
        String[] split = org.apache.commons.lang3.StringUtils.split(name, ".");
        String suffix = split[split.length - 1];
        for (int i = 0; i < banner.length; i++) {
            if (suffix.equals(banner[i])) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private static String join(String name, String uuid) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(uuid)) {
            uuid = UUIDUtil.getUUID();
        }
        StringBuilder builder = new StringBuilder();
        String[] split = org.apache.commons.lang3.StringUtils.split(name, ".");
        for (int i = 1; i <= split.length; i++) {
            if (i == split.length) {
                builder.append(split[i - 1]);
                break;
            }
            builder.append(".").append(split[i - 1]);
            if (i == split.length - 1) {
                builder.append("_").append(uuid).append(".");
            }
        }
        String s = builder.toString();
        return org.apache.commons.lang3.StringUtils.substring(s, 1);
    }

    private static String split(String name, String uuid) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(uuid)) {
            uuid = UUIDUtil.getUUID();
        }
        StringBuilder builder = new StringBuilder(uuid);
        String[] split = org.apache.commons.lang3.StringUtils.split(name, ".");
        return builder.append(".").append(split[split.length - 1]).toString();
    }

}
