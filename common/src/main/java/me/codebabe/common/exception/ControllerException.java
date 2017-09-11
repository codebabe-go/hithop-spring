package me.codebabe.common.exception;

/**
 * author: code.babe
 * date: 2017-09-11 19:23
 *
 * 在controller层的返回
 */
public class ControllerException extends HithopException {

    private int code;

    public ControllerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ControllerException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
