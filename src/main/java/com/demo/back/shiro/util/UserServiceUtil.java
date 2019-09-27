package com.demo.back.shiro.util;

import com.demo.back.entity.AuthUser;
import com.demo.back.utils.UserInfoUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author : liangbin
 * @Description : 获取当前登陆用户信息
 * @Date : Created in 2018/10/26 16:01
 * @modified By :
 */
public class UserServiceUtil {
    @Autowired
    private static UserInfoUtil userInfo;

    private static AuthUser getUser(){
        AuthUser user = (AuthUser) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    public static String getUserName(){
        return getUser().getUsername();
    }

    public static String getUserId(){
        return getUser().getId();
    }
}
