package me.codebabe.server.mqc.consumer;

/**
 * author: code.babe
 * date: 2017-09-27 21:01
 *
 * 消费者队列, 包含队列的基本信息
 */
public class ConsumerQueue {

    public ConsumerQueue() {
    }

    public ConsumerQueue(int partition, String name) {
        this.partition = partition;
        this.name = name;
    }

    private int partition; // 分区
    private String name; // 队列名字, 这个名字可能是kafka的topic, rmq的exchange等

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
