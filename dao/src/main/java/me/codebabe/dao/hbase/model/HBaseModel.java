package me.codebabe.dao.hbase.model;

import me.codebabe.dao.DAO;
import org.apache.hadoop.hbase.client.Result;

import java.io.Serializable;

/**
 * author: code.babe
 * date: 2017-10-31 20:29
 *
 * 每个rowkey对应的实体类, 相当于一条记录
 */
public interface HBaseModel extends Serializable, DAO {

    /**
     * @return 自定义一个rowkey
     */
    String rowkey();

    /**
     * @return 该rowkey下的记录
     */
    HBaseModel get();

    /**
     * @return 如果是update为旧值, 如果是insert为null
     */
    HBaseModel put();

    /**
     * @return 成功删除true, 否则false
     */
    boolean delete();

    /**
     * 获取hbase表名
     * @return
     */
    String table();

    String columnFamily();

    /**
     * 解析hbase获取的result为对象实例, 同时赋值该对象
     * @param result {@link #get()}获取的结果
     * @return
     * @deprecated 解析过程可以单独拎出来放在一个util中进行
     */
    @Deprecated
    HBaseModel parse(Result result);

}
