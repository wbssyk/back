package com.demo.back.shiro.util;

import com.demo.back.entity.AuthUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Method
 * @Author yakun.shi
 * @Description
 * @Return
 * @Date 2019/9/23 11:57
 */
public class PasswordHelper {
    //private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static String algorithmName = "md5";
    private static int hashIterations = 1024;

    public static String encryptPassword(AuthUser user) {
        String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getLoginUser() + user.getPassword()), hashIterations).toHex();
        return newPassword;
    }

    public static String encryptPassword(String loginUser,String password) {
        String newPassword = new SimpleHash(algorithmName, password, ByteSource.Util.bytes(loginUser + password), hashIterations).toHex();
        return newPassword;
    }

    public static void main(String[] args) {
        AuthUser user = new AuthUser();
        user.setLoginUser("admin");
        user.setPassword("123456");
        String s = PasswordHelper.encryptPassword(user);
        System.out.println(s);
    }
}
