package com.demo.back.exception;

import com.demo.back.common.page.ServiceResponse;
import com.demo.back.enums.ResponseEnum;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author yakun.shi
 * @Description //TODO
 * @Date 2019/6/13 13:21
 **/
@RestControllerAdvice
public class ExceptionCatch {

    /**
     * @Author yakun.shi
     * @Description //TODO 
     * @Date 2019/5/17 9:11
     * @Param [e]
     * @return com.demo.browser.config.HTResponseBody
     **/
    @ExceptionHandler(Exception.class)
    public ServiceResponse exceptionHandler(Exception e){
        e.printStackTrace();
        ServiceResponse responseBody = new ServiceResponse();
        return responseBody.createErrorServcieResponse();
    }

    @ExceptionHandler(ParamNullException.class)
    public ServiceResponse paramExceptionHandler(Exception e){
        e.printStackTrace();
        ServiceResponse responseBody = new ServiceResponse();
        responseBody.setResult(ResponseEnum.LACK_PARAMETERS.getCode());
        responseBody.setMessage(ResponseEnum.LACK_PARAMETERS.getMsg());
        return responseBody;
    }

    @ExceptionHandler(ParamTypeException.class)
    public ServiceResponse paramTypeExceptionHandler(Exception e){
        e.printStackTrace();
        ServiceResponse responseBody = new ServiceResponse();
        responseBody.setResult(ResponseEnum.PARAM_TYPE_ERROR.getCode());
        responseBody.setMessage(ResponseEnum.PARAM_TYPE_ERROR.getMsg());
        return responseBody;
    }

    @ExceptionHandler(UserNotExistException.class)
    public ServiceResponse userNotExist(UserNotExistException e){
        e.printStackTrace();
        ServiceResponse responseBody = new ServiceResponse();
        responseBody.setResult(ResponseEnum.USER_NOTFOUND.getCode());
        responseBody.setMessage(ResponseEnum.USER_NOTFOUND.getMsg());
        return responseBody;
    }

    @ExceptionHandler(TokenNullException.class)
    public ServiceResponse tokenNullException(TokenNullException e){
        e.printStackTrace();
        ServiceResponse responseBody = new ServiceResponse(ResponseEnum.TOKEN_NULL.getCode(),ResponseEnum.TOKEN_NULL.getMsg());
        return responseBody;
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ServiceResponse incorrectCredentialsException(IncorrectCredentialsException e){
        e.printStackTrace();
        ServiceResponse responseBody = new ServiceResponse(ResponseEnum.PSSSWORD_OR_NAMEIDNULL.getCode(),ResponseEnum.PSSSWORD_OR_NAMEIDNULL.getMsg());
        return responseBody;
    }
    
    @SuppressWarnings("rawtypes")
	@ExceptionHandler(ParamInsertException.class)
    public ServiceResponse paramInsertExceptionHandler(Exception e){
        e.printStackTrace();
        ServiceResponse responseBody = new ServiceResponse();
        responseBody.setResult(ResponseEnum.INS_PARAMETERS.getCode());
        responseBody.setMessage(ResponseEnum.INS_PARAMETERS.getMsg());
        return responseBody;
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ServiceResponse unauthorizedException(UnauthorizedException e){
        e.printStackTrace();
        ServiceResponse responseBody = new ServiceResponse(ResponseEnum.USER_NOTLOGIN.getCode(),ResponseEnum.USER_NOTLOGIN.getMsg());
        return responseBody;
    }


}
