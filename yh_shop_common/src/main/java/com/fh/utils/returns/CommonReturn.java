package com.fh.utils.returns;

import io.swagger.annotations.ApiModelProperty;

public class CommonReturn {

@ApiModelProperty("状态码")
    private Integer code;
@ApiModelProperty("请求信息")
    private String  msg;
@ApiModelProperty("数据展示")
    private Object data;


    private CommonReturn(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private CommonReturn(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private CommonReturn() {
    }

    public static CommonReturn success(){
        return new CommonReturn(ReturnCode.SUCCESS.getCode(),ReturnCode.SUCCESS.getMsg());
    }

    public static CommonReturn success(Integer code){
        return new CommonReturn(code,ReturnCode.SUCCESS.getMsg());
    }
    public static CommonReturn success(Object data){
        return new CommonReturn(ReturnCode.SUCCESS.getCode(),ReturnCode.SUCCESS.getMsg(),data);
    }

    public static CommonReturn success(Integer code,Object data){
        return new CommonReturn(code,ReturnCode.SUCCESS.getMsg(),data);
    }
    public static CommonReturn success(Integer code, String msg, Object data){
        return new CommonReturn(code,msg,data);
    }
    public static CommonReturn success(ReturnCode returnCode){
        return new CommonReturn(returnCode.getCode(),returnCode.getMsg());
    }
    public static CommonReturn success(ReturnCode returnCode,Object data){
        return new CommonReturn(returnCode.getCode(),returnCode.getMsg(),data);
    }



    public static CommonReturn error(){
        return new CommonReturn(ReturnCode.ERROR.getCode(),ReturnCode.ERROR.getMsg());
    }
    public static CommonReturn error(Integer code){
        return new CommonReturn(code,ReturnCode.ERROR.getMsg());
    }
    public static CommonReturn error(Object data){
        return new CommonReturn(ReturnCode.ERROR.getCode(),ReturnCode.ERROR.getMsg(),data);
    }
    public static CommonReturn error(Integer code,Object data){
        return new CommonReturn(code,ReturnCode.ERROR.getMsg(),data);
    }
    public static CommonReturn error(Integer code, String msg, Object data){
        return new CommonReturn(code,msg,data);
    }
    public static CommonReturn error(ReturnCode returnCode){
        return new CommonReturn(returnCode.getCode(),returnCode.getMsg());
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
