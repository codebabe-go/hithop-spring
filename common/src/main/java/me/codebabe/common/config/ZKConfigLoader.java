package me.codebabe.common.config;

/**
 * author: code.babe
 * date: 2017-09-08 23:15
 */
public class ZKConfigLoader implements ConfigLoader {
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
        return 0;
    }
}
