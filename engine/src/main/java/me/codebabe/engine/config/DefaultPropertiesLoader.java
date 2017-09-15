package me.codebabe.engine.config;

import me.codebabe.common.exception.config.ConfigException;
import me.codebabe.common.exception.config.ConfigNotExistException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * author: code.babe
 * date: 2017-09-08 23:15
 *
 * <p>不支持热修改</p>
 */
public class DefaultPropertiesLoader implements ConfigLoader {

    public DefaultPropertiesLoader() {
    }

    public DefaultPropertiesLoader(String path) {
        this.path = path;
        this.keySet = new ConcurrentSkipListSet<>();
        this.props = new Properties();
    }

    private String path;
    private Properties props;
    private Set<String> keySet;

    @Override
    public boolean containKey(String key) {
        return key.contains(key);
    }

    @Override
    public void load() {
        ClassLoader loader = this.getClass().getClassLoader();
        try {
            props.load(new InputStreamReader(loader.getResourceAsStream(path), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 只返回String的
    @Override
    public Object getValue(String key) throws ConfigNotExistException {
        return props.getProperty(key);
    }

    @Override
    public Integer getOrder() {
        return 2;
    }

    @Override
    public void check() throws ConfigException {
        if (path == null) {
            throw new ConfigException("DefaultPropertiesLoader.path is null");
        }
    }

    @Override
    public List<String> getAllKeys() {
        return new ArrayList<>(keySet);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public Set<String> getKeySet() {
        return keySet;
    }

    public void setKeySet(Set<String> keySet) {
        this.keySet = keySet;
    }
}
