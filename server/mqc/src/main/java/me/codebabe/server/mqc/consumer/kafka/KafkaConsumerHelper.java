package me.codebabe.server.mqc.consumer.kafka;

import me.codebabe.server.mqc.consumer.ConsumerQueue;
import me.codebabe.server.mqc.consumer.MQCConsumer;
import me.codebabe.server.mqc.processor.MessageProcessor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * author: code.babe
 * date: 2017-09-27 20:00
 *
 * kafka消费者工具类, 非线程安全类, 需要在线程封闭安全代码块内执行, 通常放在application中执行
 */
public class KafkaConsumerHelper implements MQCConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerHelper.class);

    private Map<ConsumerQueue, MessageProcessor> messageQueue = new HashMap<>();
    private ExecutorService workers = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread();
            t.setName(String.format("kafka-%d", threadSize++));
            return t;
        }
    });
    private int threadSize = 0;
    private KafkaConsumer kafkaConsumer;

    @Override
    public void start() {
        while (true) {
            for (Map.Entry<ConsumerQueue, MessageProcessor> entry : messageQueue.entrySet()) {
                try {

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public int register(ConsumerQueue queue, MessageProcessor processor) {
        Object result = messageQueue.put(queue, processor);
        if (result == null) { // 注册成功
            logger.info("register success, queue name: " + queue.getName());
        }
        return messageQueue.size();
    }

    @Override
    public boolean remove(ConsumerQueue queue) {
        return messageQueue.remove(queue) != null;
    }

    @Override
    public void shutdown() {
        messageQueue.clear();
        workers.shutdown();
    }

    public int poolSize() {
        return threadSize;
    }
}
