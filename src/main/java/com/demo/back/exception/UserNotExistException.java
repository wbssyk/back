package com.demo.back.exception;

import org.apache.shiro.authc.UnknownAccountException;

/**
 * @ClassName UserNotExistException
 * @Author yakun.shi
 * @Date 2019/6/27 13:52
 * @Version 1.0
 **/
public class UserNotExistException extends UnknownAccountException {
    public UserNotExistException() {
        super();
    }

    public UserNotExistException(String message) {
        super(message);
    }

    public UserNotExistException(Throwable cause) {
        super(cause);
    }

    public UserNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
