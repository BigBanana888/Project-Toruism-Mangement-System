package com.api.config;

import lombok.Data;

@Data
public class JsonResult <T> {
    private int code;
    private String message;
    private T data;
    public static <T> JsonResult<T> success(String msg,T data){
        JsonResult<T> responseResult=new JsonResult<>();
        responseResult.setCode(200);
        responseResult.setMessage(msg);
        responseResult.setData(data);
        return  responseResult;
    }
    public static <T> JsonResult<T> success(String msg){
        JsonResult<T> responseResult=new JsonResult<>();
        responseResult.setCode(200);
        responseResult.setMessage(msg);
        return  responseResult;
    }

    public static <T> JsonResult fail(int code,String message){
        JsonResult<T> responseResult=new JsonResult<>();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        return responseResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}