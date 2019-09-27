package com.demo.back.exception;

/**
 * ParamInsertException
 * @author lin.cong
 *
 */
@SuppressWarnings("serial")
public class ParamInsertException extends RuntimeException {
    public ParamInsertException() {
    }

    public ParamInsertException(String message) {
        super(message);
    }

    public ParamInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamInsertException(Throwable cause) {
        super(cause);
    }
}
