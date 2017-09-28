package test.codebabe.server.mqc.kafka;

import me.codebabe.server.mqc.processor.MessageProcessor;
import me.codebabe.server.mqc.util.MessageDeserializer;

/**
 * author: code.babe
 * date: 2017-09-28 10:13
 */
public class DemoProcessor implements MessageProcessor {
    @Override
    public void process(byte[] meta) {
        String data = MessageDeserializer.deserialize2String(meta);
        System.out.println(data);
    }
}
