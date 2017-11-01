package test.codebabe.engine.hbase;

import com.alibaba.fastjson.JSON;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;

/**
 * author: code.babe
 * date: 2017-10-27 17:05
 */
public class HBaseTest {

    @Test
    public void testPut() throws IOException {
        // 分别开启hdfs, zk, hbase, 然后运行程序, 同时要有这张表
        Configuration configuration = HBaseConfiguration.create();
        HConnection conn = HConnectionManager.createConnection(configuration);
        HTableInterface hTable = conn.getTable("testtable");
        Put put = new Put(Bytes.toBytes("row1"));
        put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), Bytes.toBytes("val1"));
        put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual2"), Bytes.toBytes("val2"));
        hTable.put(put);
    }

    @Test
    public void testGet() throws IOException {
        // 分别开启hdfs, zk, hbase, 然后运行程序, 同时要有这张表
        Configuration configuration = HBaseConfiguration.create();
        HConnection conn = HConnectionManager.createConnection(configuration);
        HTableInterface hTable = conn.getTable("testtable");
        Get get = new Get(Bytes.toBytes("row1"));
        Result result = hTable.get(get);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testHBaseModel() {
        HBaseModelDemo demo = new HBaseModelDemo();
        System.out.println(JSON.toJSONString(demo.get()));
    }

}