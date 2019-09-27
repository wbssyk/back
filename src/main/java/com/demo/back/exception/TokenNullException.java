package com.demo.back.exception;

/**
 * @ClassName ParamNullException
 * @Author yakun.shi
 * @Date 2019/6/14 14:54
 * @Version 1.0
 **/
public class TokenNullException extends RuntimeException {
    public TokenNullException() {
    }

    public TokenNullException(String message) {
        super(message);
    }

    public TokenNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenNullException(Throwable cause) {
        super(cause);
    }
}
