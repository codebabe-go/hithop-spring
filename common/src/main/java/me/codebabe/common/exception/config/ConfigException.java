package me.codebabe.common.exception.config;

import me.codebabe.common.exception.HithopException;

/**
 * author: code.babe
 * date: 2017-09-11 20:18
 *
 * 配置中心的异常
 */
public class ConfigException extends HithopException {

    public ConfigException() {
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

    public ConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
