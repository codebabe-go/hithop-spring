package me.codebabe.server.mqc.processor;

import me.codebabe.server.mqc.util.MessageDeserializer;

/**
 * author: code.babe
 * date: 2017-09-28 20:09
 */
public class PrintProcessor implements MessageProcessor {
    @Override
    public void process(byte[] meta) {
        String data = MessageDeserializer.deserialize2String(meta);
        System.out.println(data);
    }
}
