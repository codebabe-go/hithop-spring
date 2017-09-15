package me.codebabe.engine.config;

import me.codebabe.common.exception.config.ConfigException;
import me.codebabe.common.exception.config.ConfigNotExistException;
import me.codebabe.engine.zk.CBZKHolder;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * author: code.babe
 * date: 2017-09-08 23:15
 */
public class ZKConfigLoader implements ConfigLoader {

    private Logger logger = LoggerFactory.getLogger(ZKConfigLoader.class);

    public ZKConfigLoader() {
    }

    public ZKConfigLoader(String moduleName) {
        // 这个要求要以'/'开头
        this.moduleName = moduleName;
        this.keySet = new ConcurrentSkipListSet<>();
        init();
    }

    private String moduleName;
    private Set<String> keySet;

    private void init() {
        try {
            PathChildrenCache cache = CBZKHolder.getInstance().getPathChildCache(moduleName, (client, event) -> {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        logger.info(String.format("[getPathChildCache]add moduleName: %s, event: %s", moduleName, event.toString()));
                        break;
                    case INITIALIZED:
                        logger.info(String.format("[getPathChildCache]init moduleName: %s, event: %s", moduleName, event.toString()));
                        break;
                    case CHILD_REMOVED:
                        logger.info(String.format("[getPathChildCache]remove moduleName: %s, event: %s", moduleName, event.toString()));
                        break;
                    case CHILD_UPDATED:
                        logger.info(String.format("[getPathChildCache]update moduleName: %s, event: %s", moduleName, event.toString()));
                        break;
                    case CONNECTION_LOST:
                        logger.info(String.format("[getPathChildCache]lost moduleName: %s, event: %s", moduleName, event.toString()));
                        break;
                    case CONNECTION_SUSPENDED:
                        logger.info(String.format("[getPathChildCache]suspend moduleName: %s, event: %s", moduleName, event.toString()));
                        break;
                    case CONNECTION_RECONNECTED:
                        logger.info(String.format("[getPathChildCache]reconnection moduleName: %s, event: %s", moduleName, event.toString()));
                        break;
                }
            });
        } catch (Exception e) {
            logger.error("zk operate error", e);
        }
    }

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

    // 在getValue中已经实现了
    @Override
    public void load() {}

    @Override
    public Object getValue(String key) throws ConfigNotExistException {
        try {
            Object value = CBZKHolder.getInstance().getData(moduleName.concat("/").concat(key), Object.class);
            if (value != null) {
                keySet.add(key);
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getOrder() {
        return 1;
    }

    @Override
    public void check() throws ConfigException {
        if (moduleName == null) {
            throw new ConfigException("ZKConfigLoader.moduleName is null");
        }
    }

    @Override
    public List<String> getAllKeys() {
        return new ArrayList<>(keySet);
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Set<String> getKeySet() {
        return keySet;
    }

    public void setKeySet(Set<String> keySet) {
        this.keySet = keySet;
    }
}
