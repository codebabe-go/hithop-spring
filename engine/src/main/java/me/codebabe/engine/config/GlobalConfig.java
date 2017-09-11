package me.codebabe.engine.config;

import me.codebabe.common.exception.config.ConfigException;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author: code.babe
 * date: 2017-09-08 22:28
 */
public class GlobalConfig implements ConfigLoader {

    public GlobalConfig(List<ConfigLoader> localLoader) {
        this.romLoader = new ConcurrentHashMap<>();
        this.localLoader = localLoader;
    }

    private ConcurrentHashMap<String, Object> romLoader;
    private List<ConfigLoader> localLoader;

    @Override
    public boolean containKey(String key) {
        return false;
    }

    @Override
    public void load() {}

    @Override
    public Object getValue(String key) {
        return null;
    }

    @Override
    public Integer getOrder() {
        return 1;
    }

    @Override
    public void check() throws ConfigException {
        if (localLoader.isEmpty()) {
            throw new ConfigException("GlobalConfig.localLoader is empty");
        }
    }

    public ConcurrentHashMap<String, Object> getRomLoader() {
        return romLoader;
    }

    public void setRomLoader(ConcurrentHashMap<String, Object> romLoader) {
        this.romLoader = romLoader;
    }

    public List<ConfigLoader> getLocalLoader() {
        return localLoader;
    }

    public void setLocalLoader(List<ConfigLoader> localLoader) {
        this.localLoader = localLoader;
    }
}
