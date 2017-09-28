package me.codebabe.engine.kafka;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * author: code.babe
 * date: 2017-09-16 16:57
 *
 * kafka消费者单例模式
 */
public class CBKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(CBKafkaProducer.class);

    private CBKafkaProducer() {
    }

    private Producer<String, byte[]> wetNurse; // 奶妈, 生产者
    private static CBKafkaProducer instance;

    public static CBKafkaProducer getInstance() {
        if (instance == null) {
            synchronized (CBKafkaProducer.class) {
                if (instance == null) {
                    Properties prop = new Properties();
                    try {
                        prop.load(new InputStreamReader(CBKafkaProducer.class.getClassLoader().getResourceAsStream("properties/kafka-producer.properties")));
                        instance = new CBKafkaProducer();
                        instance.wetNurse = new KafkaProducer<>(prop);

                        logger.info(String.format("acquire kafka producer instance in %d", System.currentTimeMillis()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return instance;
    }

    public Future<RecordMetadata> send(String topic, String key, byte[] value) {
        return wetNurse.send(new ProducerRecord<>(topic, key, value), null);
    }

    public Future<RecordMetadata> send(ProducerRecord<String, byte[]> record) {
        return wetNurse.send(record, null);
    }

    public Future<RecordMetadata> send(ProducerRecord<String, byte[]> record, Callback callback) {
        return wetNurse.send(record, callback);
    }

}
