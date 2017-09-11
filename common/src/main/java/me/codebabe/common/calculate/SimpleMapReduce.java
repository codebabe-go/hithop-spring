package me.codebabe.common.calculate;

import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * author: code.babe
 * date: 2017-08-16 19:22
 */
public abstract class SimpleMapReduce<T> {

    // 一般来说不要这个方法
    public SimpleMapReduce() {
        this.tasks = new ArrayList<>();
        this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 8);
    }

    // 传入一个线程池
    public SimpleMapReduce(ExecutorService pool) {
        this.tasks = new ArrayList<>();
        this.pool = pool;
    }

    private List<Future<T>> tasks;
    private ExecutorService pool;

    public void addTask(final Function<Object, T> function) {
        tasks.add(pool.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                // 没有传入参数的一个运算
                return function.apply(null);
            }
        }));
    }

    /**
     * 求和
     *
     * @return
     */
    public abstract T summary();

    /**
     * 求差
     *
     * @return
     */
    public abstract T deviation();

    /**
     * 求乘积
     *
     * @return
     */
    public abstract T product();

    /**
     * 求商值
     *
     * @return
     */
    public abstract T consult();

    public List<Future<T>> getTasks() {
        return tasks;
    }

    public void setTasks(List<Future<T>> tasks) {
        this.tasks = tasks;
    }

}
