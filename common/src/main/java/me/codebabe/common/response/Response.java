package me.codebabe.common.response;

/**
 * author: code.babe
 * date: 2017-09-09 21:24
 *
 * 通常用于controller的响应层
 */
public class Response {

    public Response() {
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(Object data) {
        code = 200;
        message = "success";
        this.data = data;
    }

    private int code = 200;
    private String message;
    private Object data;

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
