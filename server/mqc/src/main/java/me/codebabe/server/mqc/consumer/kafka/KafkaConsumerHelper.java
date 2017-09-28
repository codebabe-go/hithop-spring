package me.codebabe.server.mqc.consumer.kafka;

import me.codebabe.server.mqc.consumer.ConsumerQueue;
import me.codebabe.server.mqc.consumer.MQCConsumer;
import me.codebabe.server.mqc.processor.MessageProcessor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
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

    // TODO: 28/09/2017 线程池 随机分配, 可以做一个线程数组, 自己控制

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerHelper.class);

    private Map<ConsumerQueue, MessageProcessor> messageQueue = new HashMap<>();
    private Map<String, ConsumerQueue> queueMapping = new HashMap<>();
    private ExecutorService workers = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread();
            t.setName(String.format("kafka-consumer-%d", threadSize++));
            return t;
        }
    });
    private int threadSize = 0;
    private KafkaConsumer<String, byte[]> kafkaConsumer;

    private void init() {
        Properties props = new Properties();
        List<String> topics = new ArrayList<>();
        try {
            props.load(new InputStreamReader(KafkaConsumerHelper.class.getClassLoader().getResourceAsStream("properties/kafka-consumer.properties")));
            topics.addAll(Arrays.asList(props.getProperty("kafka.topics", "test").split(",")));
        } catch (IOException e) {
            logger.warn("load local properties file failed", e);
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "1");
            topics.add("test"); // 默认给一个test topic
        }
        kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(topics); // 订阅test
    }

    @Override
    public void start() {
        init();
        while (true) {
            try {
                for (ConsumerRecord<String, byte[]> record : kafkaConsumer.poll(200)) {
                    logger.info("record: {}", record.toString());
                    String topic = record.topic();
                    MessageProcessor processor = messageQueue.get(queueMapping.get(topic));
                    processor.process(record.value());
                }
                if (queueMapping.size() != messageQueue.size()) {
                    logger.error("concurrent error, queueMapping: {}, messageQueue: {}", queueMapping.size(), messageQueue.size());
                    shutdown();
                    break;
                }
                Thread.sleep(1000); // 每次都休眠1秒, test
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public int register(ConsumerQueue queue, MessageProcessor processor) {
        Object result = messageQueue.put(queue, processor);
        if (result == null) { // 注册成功
            logger.info("register success, queue name: " + queue.getName());
            queueMapping.put(queue.getName(), queue);
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
