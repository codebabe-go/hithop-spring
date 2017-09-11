package me.codebabe.common.calculate;

import com.google.common.util.concurrent.AtomicDouble;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * author: code.babe
 * date: 2017-08-16 19:38
 */
public class DoubleSimpleMapReduce extends SimpleMapReduce<Double> {

    public DoubleSimpleMapReduce() {
    }

    public DoubleSimpleMapReduce(ExecutorService pool) {
        super(pool);
    }

    @Override
    public Double summary() {
        AtomicDouble sum = new AtomicDouble(0D);
        for (Future<Double> future : getTasks()) {
            try {
                sum.addAndGet(future.get());
            } catch (Exception e) {
            }
        }
        return sum.get();
    }

    @Override
    public Double deviation() {
        return null;
    }

    @Override
    public Double product() {
        return null;
    }

    @Override
    public Double consult() {
        return null;
    }
}
