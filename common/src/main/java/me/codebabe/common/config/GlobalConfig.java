package me.codebabe.common.config;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author: code.babe
 * date: 2017-09-08 22:28
 */
public class GlobalConfig implements ConfigLoader {

    private ConcurrentHashMap<String, Object> romLoader;
    private List<ConfigLoader> localLoader;


    @Override
    public boolean containKey(String key) {
        return false;
    }

    @Override
    public void load() {
    }

    @Override
    public Object getValue(String key) {
        return null;
    }

    @Override
    public Integer getOrder() {
        return 1;
    }
}
