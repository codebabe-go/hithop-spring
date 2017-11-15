package me.codebabe.dao.hbase.utils;

import me.codebabe.common.utils.StringUtils;
import me.codebabe.dao.hbase.HBaseModel;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * author: code.babe
 * date: 2017-11-09 10:05
 */
public class HBaseHelper {

    public static HBaseModel parse(Result result, HBaseModel model) {
        Map<byte[], byte[]> fieldMap = result.getFamilyMap(Bytes.toBytes(model.columnFamily()));
        if (fieldMap != null) {
            Class<?> thisClz = model.getClass();
            // 线程安全
            Map<String, Method> methodMap = new HashMap<>();
            try {
                for (Field field: thisClz.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Method method = thisClz.getMethod("set" + StringUtils.reverseCaseByIndex(fieldName, 0), field.getType());
                    methodMap.put(fieldName, method);
                }
                for (Map.Entry<byte[], byte[]> entry : fieldMap.entrySet()) {
                    String qualifier = Bytes.toString(entry.getKey()); // qualifier: 对应字段名字
                    byte[] bValue = entry.getValue();
                    // 通过反射进行赋值
                    Method method = methodMap.get(qualifier);
                    Field field = thisClz.getDeclaredField(qualifier);
                    Class<?> fieldType = field.getType();
                    if (Long.class.equals(fieldType)) {
                        method.invoke(model, Bytes.toLong(bValue));
                    } else if (Integer.class.equals(fieldType)) {
                        method.invoke(model, Bytes.toInt(bValue));
                    } else if (Short.class.equals(fieldType)) {
                        method.invoke(model, Bytes.toShort(bValue));
                    } else if (Double.class.equals(fieldType)) {
                        method.invoke(model, Bytes.toDouble(bValue));
//                } else if (Timestamp.class.equals(fieldType)) {
                    } else { // 默认都是string类型了
                        method.invoke(model, Bytes.toString(bValue));
                    }
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return model;
    }

}
