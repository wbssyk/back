package com.demo.back.enums;

public enum ResponseEnum {

    UNREGISTERED(1,"用户未注册！"),
    WRONG_PASSWORD(2, "密码错误！"),
    AUTHENTICATION_FAILURE(3, "认证失败！"),
    LOGIN_FAILURE(4, "登录失败！"),
    UPDATE_PASSWORD_FAILURE(6,"修改密码失败，请检查后重试！"),
    OPERATION_FAILURE(7,"操作失败，请检查后重试！"),
    REGISTER_USER_FAILURE(8,"注册用户失败，请检查后重试！"),
    NO_PARAMETERS(9,"未传递任何参数，请检查后重试！"),
    ALREADY_REGISTERED(10,"该手机号已经注册！"),
    LACK_PARAMETERS(11,"缺少必传参数，请检查后重试！"),
    CHECK_SMS_FAILURE(12,"校验验证码失败，请检查后重试！"),
    NOT_FOUND_DATA(13,"未找到此数据，请检查后重试！"),
    UPLOAD_FILE_FAILURE(14,"上传文件失败，请检查后重试！"),
    PARAM_TYPE_ERROR(15,"参数格式错误，请检查后重试！"),
    DATA_REPATE(16,"插入数据重复！"),
    USERNAME_PASSWORDNULL(17,"用户名或密码为空"),
    USREIS_EXIST(18,"用户已经存在"),
    IS_SUPERADMIN(19,"用户为超级管理员,不能被删除或者编辑"),
    USER_NOTFOUND(20,"用户未注册"),
    ROLEIS_EXIST(21,"角色已经存在"),
    PSSSWORD_OR_NAMEIDNULL(22,"用户名或密码不正确"),
    ROLE_ISSUPERADMIN(23,"角色为内置角色,不能被删除"),
    FORBID_DELETE(24, "不允许删除"),
    TOKEN_NULL(25, "请求头未传token"),
    TOKEN_TIMEOUT(26, "token过时或者错误,请重新获取"),
    INS_PARAMETERS(27, "插入上传数据失败，请检查后重试"),
    BROWSER_UPLOAD_ERROR(28, "浏览器包名或者格式不符合要求"),
    OLD_PASSWORD_ERROR(29, "旧密码错误"),
    WIDGET_UPLOAD_ERROR(30, "控件上传错误,控件包名不符合要求请重新上传！"),
    LINMIT_ERROR(31, "接口限流出现错误"),
    NOT_NEED_UPGRADE(32, "客户端版本无需升级更新"),
    KEY_EXISIT(33, "此类型key已存在"),
    NO_VERSION(100,"版本号未传"),
    VERSION_ERROE(101,"版本号错误"),
    SUCCESS(200, "成功"),
    USER_NOTLOGIN(300,"用户未登录"),
    USER_NOAUTHORITY(403,"用户没有权限"),
    VISIT_ERROR(500,"访问异常");


    private Integer code;

    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
