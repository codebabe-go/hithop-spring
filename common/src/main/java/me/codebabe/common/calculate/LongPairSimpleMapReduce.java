package me.codebabe.common.calculate;

import me.codebabe.common.dataparam.LongPair;

import java.util.concurrent.ExecutorService;

/**
 * author: code.babe
 * date: 2017-08-17 15:47
 */
public class LongPairSimpleMapReduce extends SimpleMapReduce<LongPair> {

    public LongPairSimpleMapReduce() {
    }

    public LongPairSimpleMapReduce(ExecutorService pool) {
        super(pool);
    }

    @Override
    public LongPair summary() {
        return null;
    }

    @Override
    public LongPair deviation() {
        return null;
    }

    @Override
    public LongPair product() {
        return null;
    }

    @Override
    public LongPair consult() {
        return null;
    }
}
