package test.codebabe.server.mqc.kafka;

import me.codebabe.server.mqc.consumer.ConsumerQueue;
import me.codebabe.server.mqc.consumer.kafka.KafkaConsumerHelper;
import org.junit.Test;

/**
 * author: code.babe
 * date: 2017-09-28 16:25
 */
public class ConsumerTest {

    @Test
    public void testReceiveMessage() {
        // 生产者为ProducerTest, 请结合使用
        KafkaConsumerHelper helper = new KafkaConsumerHelper();
        helper.register(new ConsumerQueue(1, "test"), new DemoProcessor());
        helper.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            helper.shutdown();
            System.out.println("shutdown finish");
        }));
    }

}
