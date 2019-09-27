package com.demo.back.utils;



import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 常量定义
 * 
 * @author jingbiaoCai
 * 
 */
public class Constants {

    //未审核
    public static final String CHECK_STATUS_NOT_CHECK = "1"; 
    //通过
    public static final String CHECK_STATUS_PASS = "2";
    //拒绝
    public static final String CHECK_STATUS_NOT_PASS = "3";
    
    //上传控件类型  
    public static final String UPLOAD_APP_TYPE_A = "1";
    //上传控件类型 
    public static final String UPLOAD_APP_TYPE_B = "2";
    
    //同意
    public static final String SELECT_ID_YES = "1";
    //不同意
    public static final String SELECT_ID_NO = "2";
    
    //版本号
    public static final String VERSION_NUM_INIT = "1";
    
    //删除状态 （0：使用，1：禁用，2删除）
    public static final int DELETE_STATUE_APP = 0;

    public static final int DELETE_STATUE_FRBB = 1;
    
    public static final int DELETE_STATUE_NOT = 2;
    
    //获取安装类型（1：win,2：mac）
    public static final String BROWSER_TYPE_WIN = "1";

    public static final String BROWSER_TYPE_MAC = "2";
    
    public static Map<String, String> getHeadMap() {
		Map<String, String> headMap = new LinkedHashMap<String, String>();                                           
        
        return headMap;
	}
	
    
}
