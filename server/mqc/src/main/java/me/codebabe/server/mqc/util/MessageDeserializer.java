package me.codebabe.server.mqc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * author: code.babe
 * date: 2017-09-28 11:06
 */
public class MessageDeserializer {

    public static String deserialize2String(byte[] body) {
        return new String(body);
    }

    public static JSONObject deserialize2JSON(byte[] body) {
        String s = deserialize2String(body);
        try {
            return JSON.parseObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
