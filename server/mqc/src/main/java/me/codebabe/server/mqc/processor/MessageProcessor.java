package me.codebabe.server.mqc.processor;

/**
 * author: code.babe
 * date: 2017-09-28 10:11
 */
public interface MessageProcessor {

    /**
     * 元数据处理, 消息队列消息拿出来后做的相关处理
     *
     * @param meta
     */
    void process(byte[] meta);

}
