package com.fh.utils.returns;

public enum ReturnCode {

    SUCCESS(110,"操作成功"),
    ERROR(220,"操作失败"),
    ERROR_USERANDPWD_NULL(221,"账户密码不能为空"),
    ERROR_USER_NO(222,"账户不存在"),
    SUCCESS_LOGIN_YES(111,"登陆成功"),
    ERROR_PWD_EXE(223,"密码错误"),
    LEAVE_TIME_ERROR(224,"请假时间有误"),
    LOGIN_OUTTIME_ERROR(225,"登录失效重新登录"),
    LOGIN_ERROR(401,"登陆问题"),
    POWER_ERROR(403,"授权问题");
    private Integer code;

    private String msg;

    ReturnCode(Integer code, String msg) {
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
