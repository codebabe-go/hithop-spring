package me.codebabe.engine.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: code.babe
 * date: 2017-09-14 19:29
 */
public class TaskManager {

    public TaskManager() {
    }

    public TaskManager(String name, int poolSize) {
        this.name = name;
        this.poolSize = poolSize;
        pool = Executors.newFixedThreadPool(poolSize, r -> {
            Thread t = new Thread();
            t.setName(String.format("thread-poll-%s-%d", name, count++));
            return t;
        });
    }

    private String name;
    private int poolSize;
    private int count = 0;
    private ExecutorService pool;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ExecutorService getPool() {
        return pool;
    }

    public void setPool(ExecutorService pool) {
        this.pool = pool;
    }
}
