package com.demo.back.exception;

/**
 * @ClassName ParamNullException
 * @Author yakun.shi
 * @Date 2019/6/14 14:54
 * @Version 1.0
 **/
public class ParamNullException extends RuntimeException {
    public ParamNullException() {
    }

    public ParamNullException(String message) {
        super(message);
    }

    public ParamNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamNullException(Throwable cause) {
        super(cause);
    }
}
