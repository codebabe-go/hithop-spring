package me.codebabe.common.calculate;


import me.codebabe.common.dataparam.LongPair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * author: code.babe
 * date: 2017-08-17 14:41
 */
public class LongPairListSimpleMapReduce extends SimpleMapReduce<List<LongPair>> {

    public LongPairListSimpleMapReduce() {
    }

    public LongPairListSimpleMapReduce(ExecutorService pool) {
        super(pool);
    }

    @Override
    public List<LongPair> summary() {
        List<LongPair> list = new ArrayList<>();
        for (Future<List<LongPair>> future : getTasks()) {
            try {
                list.addAll(future.get());
            } catch (Exception e) {
            }
        }
        return list;
    }

    @Override
    public List<LongPair> deviation() {
        return null;
    }

    @Override
    public List<LongPair> product() {
        return null;
    }

    @Override
    public List<LongPair> consult() {
        return null;
    }
}
