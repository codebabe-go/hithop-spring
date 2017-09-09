package me.codebabe.common.config;

/**
 * author: code.babe
 * date: 2017-09-07 21:50
 *
 * 提供系统全局的取值
 * 1. 如何动态的去获取, 轮询, 一段时间去更新一次
 * 2. 优雅的配置多个源
 * 3. 不借助spring的前提下如何配置, 提供的是静态方法
 */
public class ConfigGetter {

    public static Integer getInteger(String key, Integer defaultValue) {
        return defaultValue;
    }

    public static Long getLong(String key, Long defaultValue) {
        return defaultValue;
    }

    public static Double getDouble(String key, Double defaultValue) {
        return defaultValue;
    }

    public static String getString(String key, String defaultValue) {
        return defaultValue;
    }

    public static Object getValue(String key, Object defaultValue) {
        return defaultValue;
    }
}
