package com.demo.back.controller.system.authresourcecontroller;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.system.authresourcecontroller.request.AuthWebUrlRequest;
import com.demo.back.controller.system.authusercontroller.request.AuthUserPageRequest;
import com.demo.back.entity.AuthWebUrl;
import com.demo.back.service.IAuthResourcesService;
import com.demo.back.service.IAuthWebUrlService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @ClassName AuthResourceController
 * @Author yakun.shi
 * @Date 2019/6/14 13:44
 * @Version 1.0
 **/
@RestController
@RequestMapping("resource")
public class AuthResourceController {

    @Autowired
    private IAuthWebUrlService authWebUrlService;

    @Autowired
    private IAuthResourcesService authResourcesService;


    @PostMapping("insert")
    @ApiOperation(value = "添加权限")
    public ServiceResponse resourceInsert(@RequestBody AuthWebUrl request) {
        ServiceResponse serviceResponse = authWebUrlService.resourceInsert(request);
        return serviceResponse;
    }

    @PostMapping("list")
    @ApiOperation(value = "权限管理---列表")
    public PageResponse resourceList(@RequestBody AuthWebUrlRequest request) {
        PageResponse pageResponse = authWebUrlService.resourceList(request);
        return pageResponse;
    }

    @PostMapping("delete")
    @ApiOperation(value = "权限管理---删除--禁用--解禁")
    public ServiceResponse resourceDelete(@RequestBody AuthWebUrl request) {
        ServiceResponse serviceResponse = authWebUrlService.resourceDelete(request);
        return serviceResponse;
    }

    @PostMapping("update")
    @ApiOperation(value = "权限管理---更新")
    public ServiceResponse resourceUpdate(@RequestBody AuthWebUrl request) {
        ServiceResponse serviceResponse = authWebUrlService.resourceUpdate(request);
        return serviceResponse;
    }

    @PostMapping("left_data")
    @ApiOperation(value = "左侧菜单栏")
    public ServiceResponse LeftSideBar(@RequestBody AuthWebUrl request) {
        ServiceResponse serviceResponse = authWebUrlService.LeftSideBar(request);
        return serviceResponse;
    }

//    @PostMapping("left_data")
    @ApiOperation(value = "左侧菜单栏")
    @ApiIgnore
    public ServiceResponse leftData(@RequestBody AuthUserPageRequest request) {
        ServiceResponse serviceResponse = authResourcesService.leftData(request);
        return serviceResponse;
    }
}
