package me.codebabe.common;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Map;

/**
 * author: code.babe
 * date: 2018-03-13 23:10
 */
public class ScalaUtil {

    public static ArrayList parse(String json) {
        Map map = JSON.parseObject(json, Map.class);
        return new ArrayList<>(map.values());
    }

}
