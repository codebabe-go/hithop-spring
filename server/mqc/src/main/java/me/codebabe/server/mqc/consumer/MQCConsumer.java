package me.codebabe.server.mqc.consumer;

import me.codebabe.server.mqc.processor.MessageProcessor;

/**
 * author: code.babe
 * date: 2017-09-27 20:08
 */
public interface MQCConsumer {

    void init();

    /**
     * 通常来说就是轮询去读消息
     */
    void start();


    /**
     * 注册一个新的队列, 非线程安全
     *
     * @return 当前有多少队列
     */
    int register(ConsumerQueue queue, MessageProcessor processor);

    /**
     * 移除一个队列
     *
     * @param queue
     * @return true表示移除成功
     */
    boolean remove(ConsumerQueue queue);

    /**
     * 关闭
     */
    void shutdown();
}
