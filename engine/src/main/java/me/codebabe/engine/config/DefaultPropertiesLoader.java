package me.codebabe.engine.config;

import me.codebabe.common.exception.config.ConfigException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * author: code.babe
 * date: 2017-09-08 23:15
 */
public class DefaultPropertiesLoader implements ConfigLoader {

    public DefaultPropertiesLoader() {
    }

    public DefaultPropertiesLoader(String path) {
        this.path = path;
    }

    private String path;
    private Properties props;

    @Override
    public boolean containKey(String key) {
        return false;
    }

    @Override
    public void load() {
        props = new Properties();
        ClassLoader loader = this.getClass().getClassLoader();
        try {
            props.load(new InputStreamReader(loader.getResourceAsStream(path), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 只返回String的
    @Override
    public Object getValue(String key) {
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
}
