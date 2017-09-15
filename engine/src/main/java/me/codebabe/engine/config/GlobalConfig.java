package me.codebabe.engine.config;

import me.codebabe.common.exception.config.ConfigException;
import me.codebabe.common.exception.config.ConfigNotExistException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * author: code.babe
 * date: 2017-09-08 22:28
 *
 * 原理: 定时任务去轮询zk和本地配置文件, 将值读到内存中(加快读的速度)
 * zk优先级 > 本地配置优先级
 */
public class GlobalConfig implements ConfigLoader, Runnable {

    public GlobalConfig(List<ConfigLoader> localLoader) {
        this.romLoader = new ConcurrentHashMap<>();
        this.localLoader = localLoader;
        localLoader.sort(Comparator.comparingInt(ConfigLoader::getOrder));
    }

    private ConcurrentHashMap<String, Object> romLoader;
    private List<ConfigLoader> localLoader;
    private int count;

    @Override
    public boolean containKey(String key) {
        return romLoader.containsKey(key);
    }

    /**
     * 同步zk和本地配置中的数据到内存中
     */
    @Override
    public void load() {
        for (ConfigLoader loader : localLoader) {
            List<String> keys = loader.getAllKeys();
            for (String key : keys) {
                if (!containKey(key)) {
                    romLoader.put(key, loader.getValue(key));
                }
            }
        }
    }

    @Override
    public Object getValue(String key) throws ConfigNotExistException {
        if (romLoader.containsKey(key)) {
            return romLoader.get(key);
        } else {
            for (ConfigLoader loader : localLoader) {
                if (loader.containKey(key)) {
                    Object value = loader.getValue(key);
                    if (value != null) {
                        // 如果缓存中没有, 但是其他里面有, 就塞进去好了
                        romLoader.put(key, value);
                        return loader.getValue(key);
                    }
                }
            }
        }
        throw new ConfigNotExistException();
    }

    @Override
    public Integer getOrder() {
        return 0;
    }

    @Override
    public void check() throws ConfigException {
        if (localLoader.isEmpty()) {
            throw new ConfigException("GlobalConfig.localLoader is empty");
        }
    }

    // TODO: 15/09/2017 这个可以改成增加一个field来解决, 防止每次都要遍历, 但是要注意并发问题
    @Override
    public List<String> getAllKeys() {
        List<String> keys = new ArrayList<>();
        while (romLoader.keys().hasMoreElements()) {
            keys.add(romLoader.keys().nextElement());
        }
        return keys;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 更新配置, 默认10分钟一次, 重新读到内存中
     */
    @Override
    public void run() {
        Executors.newScheduledThreadPool(8, r -> {
            Thread t = new Thread();
            t.setName(String.format("[GlobalConfig]schedule-%s-%d", "global", count++));
            return t;
        }).schedule(this::load, 10, TimeUnit.MINUTES);
    }
}
