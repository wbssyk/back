package com.demo.back.controller.system.authusercontroller;

import com.demo.back.common.page.ServiceResponse;
import com.demo.back.common.logaop.systemlogaop.LogCollect;
import com.demo.back.controller.system.authusercontroller.request.LoginUserRequest;
import com.demo.back.entity.AuthUser;
import com.demo.back.service.IAuthUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Method
 * @Author yakun.shi
 * @Description
 * @Return
 * @Date 2019/9/23 11:57
 */
@Controller
public class UserController {

    @Autowired
    private IAuthUserService authUserService;

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    @ResponseBody
    @LogCollect(logValue = "收集用户登录系统日志",simpleValue = "登录")
    public ServiceResponse login(@RequestBody LoginUserRequest request) {
        ServiceResponse serviceResponse = authUserService.login(request);
        return serviceResponse;
    }

//    @GetMapping("login")
//    @ApiOperation(value = "用户登录")
//    @LogCollect(logValue = "收集用户登录系统日志",operateType = "登录")
//    public void login(String username, String pwd,
//                      HttpServletResponse response) throws IOException {
//        AuthUser authUser = new AuthUser();
//        authUser.setLoginUser(username);
//        authUser.setPassword(pwd);
//        ServiceResponse serviceResponse = authUserService.login(authUser);
//        Map<String, Object> data = (Map<String, Object>) serviceResponse.getData();
//        String token = data.get("token").toString();
//        response.setStatus(302);
//        response.setHeader("location","http://localhost:8080/html/11.html");
////        response.setHeader("token",token);
//        Cookie cookie = new Cookie("token",token);
//        response.addCookie(cookie);
//    }


    @GetMapping("logout")
    @ApiOperation(value = "退出登录")
    @ResponseBody
    public ServiceResponse logout(HttpServletRequest request) {
        ServiceResponse serviceResponse = authUserService.logout(request);
        return serviceResponse;
    }

}
