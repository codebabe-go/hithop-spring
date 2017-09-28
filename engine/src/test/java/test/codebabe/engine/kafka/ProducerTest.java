package test.codebabe.engine.kafka;

import me.codebabe.engine.kafka.CBKafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * author: code.babe
 * date: 2017-09-22 13:21
 */
public class ProducerTest {

    @Test
    public void testSend() throws ExecutionException, InterruptedException {
        System.out.println(CBKafkaProducer.getInstance().send(new ProducerRecord<>("test", "key", "hello kafka".getBytes())).get().toString());
    }

}
