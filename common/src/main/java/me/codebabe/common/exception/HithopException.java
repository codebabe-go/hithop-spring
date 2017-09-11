package me.codebabe.common.exception;

/**
 * author: code.babe
 * date: 2017-09-11 20:17
 */
public class HithopException extends RuntimeException {

    public HithopException() {
    }

    public HithopException(String message) {
        super(message);
    }

    public HithopException(String message, Throwable cause) {
        super(message, cause);
    }

    public HithopException(Throwable cause) {
        super(cause);
    }

    public HithopException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
