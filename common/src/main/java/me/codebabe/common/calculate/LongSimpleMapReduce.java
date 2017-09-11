package me.codebabe.common.calculate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author: code.babe
 * date: 2017-08-16 19:28
 */
public class LongSimpleMapReduce extends SimpleMapReduce<Long> {

    public LongSimpleMapReduce() {
    }

    public LongSimpleMapReduce(ExecutorService pool) {
        super(pool);
    }

    @Override
    public Long summary() {
        AtomicLong sum = new AtomicLong(0L);
        for (Future<Long> future : getTasks()) {
            try {
                sum.addAndGet(future.get());
            } catch (Exception e) {
            }
        }
        return sum.get();
    }

    @Override
    public Long deviation() {
        return null;
    }

    @Override
    public Long product() {
        return null;
    }

    @Override
    public Long consult() {
        return null;
    }
}
