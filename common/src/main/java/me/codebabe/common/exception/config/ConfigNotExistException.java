package me.codebabe.common.exception.config;

import me.codebabe.common.exception.HithopException;

/**
 * author: code.babe
 * date: 2017-09-15 15:45
 */
public class ConfigNotExistException extends HithopException {

    public ConfigNotExistException() {
    }

    public ConfigNotExistException(String message) {
        super(message);
    }

    public ConfigNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigNotExistException(Throwable cause) {
        super(cause);
    }

    public ConfigNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
