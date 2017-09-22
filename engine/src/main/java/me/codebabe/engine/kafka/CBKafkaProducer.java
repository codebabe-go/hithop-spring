package me.codebabe.engine.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * author: code.babe
 * date: 2017-09-16 16:57
 */
public class CBKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(CBKafkaProducer.class);

    private CBKafkaProducer() {
    }

    private KafkaProducer wetNurse; // 奶妈, 生产者
    private static CBKafkaProducer instance;

    public static CBKafkaProducer getInstance() {
        if (instance == null) {
            synchronized (CBKafkaProducer.class) {
                if (instance == null) {
                    Properties prop = new Properties();
                    try {
                        prop.load(new InputStreamReader(CBKafkaProducer.class.getClassLoader().getResourceAsStream("properties/kafka-producer.properties")));
                        instance = new CBKafkaProducer();
                        instance.wetNurse = new KafkaProducer(prop);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return instance;
    }

    public <K, V> Future send(ProducerRecord<K, V> record) {
        return wetNurse.send(record, null);
    }

    public <K, V> Future send(ProducerRecord<K, V> record, Callback callback) {
        return wetNurse.send(record, callback);
    }

}
