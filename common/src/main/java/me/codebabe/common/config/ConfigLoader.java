package me.codebabe.common.config;

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
    Object getValue(String key);

    /**
     * 读取值的优先级, 越小表示优先级越高, 0是最高权限
     *
     * @return
     */
    Integer getOrder();

}
