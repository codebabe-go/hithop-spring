package me.codebabe.engine.config;

import me.codebabe.common.exception.config.ConfigException;
import me.codebabe.common.exception.config.ConfigNotExistException;

import java.util.List;

/**
 * author: code.babe
 * date: 2017-09-08 23:05
 *
 * 配置加载
 */
public interface ConfigLoader {

    /**
     * 是否包含这个key
     *
     * @param key
     * @return
     */
    boolean containKey(String key);

    /**
     * 加载配置
     */
    void load();

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    Object getValue(String key) throws ConfigNotExistException;

    /**
     * 读取值的优先级, 越小表示优先级越高, 0是最高优先级, 越灵活, 优先级越高
     *
     * @return
     */
    Integer getOrder();

    void check() throws ConfigException;

    /**
     *
     * @return 所有的key
     */
    List<String> getAllKeys();

}
