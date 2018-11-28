package com.garylee.tmall_springboot.util;

/**
 * Created by GaryLee on 2018-11-29 01:04.
 * 统一的 REST响应对象。 这个对象里包含是否成功，错误信息，以及数据。
 Result 对象是一种常见的 RESTFUL 风格返回的 json 格式，里面可以有错误代码，错误信息和数据。
 这样就比起以前那样，仅仅返回数据附加了更多的信息，方便前端人员识别和显示给用户可识别信息。
 在前端注册，登陆等地方有更广泛的运用。
 */
public class Result {
    public static int SUCCESS_CODE = 0;
    public static int FAIL_CODE = 1;

    int code;
    String message;
    Object data;

    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(SUCCESS_CODE,null,null);
    }
    public static Result success(Object data) {
        return new Result(SUCCESS_CODE,"",data);
    }
    public static Result fail(String message) {
        return new Result(FAIL_CODE,message,null);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
