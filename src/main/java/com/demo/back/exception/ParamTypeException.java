package com.demo.back.exception;

/**
 * @ClassName ParamNullException
 * @Author yakun.shi
 * @Date 2019/6/14 14:54
 * @Version 1.0
 **/
public class ParamTypeException extends RuntimeException {

    public ParamTypeException() {
    }

    public ParamTypeException(String message) {
        super(message);
    }

    public ParamTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamTypeException(Throwable cause) {
        super(cause);
    }

}
