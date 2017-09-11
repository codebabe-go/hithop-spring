package me.codebabe.engine.config;

import me.codebabe.common.exception.config.ConfigException;
import me.codebabe.engine.zk.CBZKHolder;

/**
 * author: code.babe
 * date: 2017-09-08 23:15
 */
public class ZKConfigLoader implements ConfigLoader {

    public ZKConfigLoader() {
    }

    public ZKConfigLoader(String moduleName) {
        this.moduleName = moduleName;
    }

    private String moduleName;

    // 这个key实际上是一个路径
    @Override
    public boolean containKey(String key) {
        try {
            return CBZKHolder.getInstance().isExist(moduleName.concat("/").concat(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 直接从holder中读取
    @Override
    public void load() {}

    @Override
    public Object getValue(String key) {
        try {
            return CBZKHolder.getInstance().getData(moduleName.concat("/").concat(key), Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getOrder() {
        return 0;
    }

    @Override
    public void check() throws ConfigException {
        if (moduleName == null) {
            throw new ConfigException("ZKConfigLoader.moduleName is null");
        }
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
