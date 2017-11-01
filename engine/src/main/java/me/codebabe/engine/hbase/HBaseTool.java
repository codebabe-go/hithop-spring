package me.codebabe.engine.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * author: code.babe
 * date: 2017-11-01 14:45
 */
public class HBaseTool {

    private static final Logger logger = LoggerFactory.getLogger(HBaseTool.class);

    private HBaseTool() {
        reload();
        establishConn();
    }

    private static HBaseTool tool;
    private Configuration conf;
    private HConnection conn;

    public static HBaseTool getTool() {
        if (tool == null) {
            synchronized (HBaseTool.class) {
                if (tool == null) {
                    tool = new HBaseTool();
                    Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tool.reload(); // 重载一下
//                        UserGroupInformation.getCurrentUser().reloginFromKeytab(); // 登录认证
                            } catch (Exception e) {
                                logger.error("auth failed, cannot login", e);
                            }
                        }
                    }, 5, 5, TimeUnit.HOURS);
                }
            }
        }
        return tool;
    }

    public HBaseModel get(HBaseModel model) {
        try {
            HTableInterface htable = getTable(model);
            Get get = new Get(Bytes.toBytes(model.rowkey()));
            Result result = htable.get(get);
            return model.parse(result);
        } catch (IOException e) {
            logger.error("hbase get a row error, rowkey = {}", model.rowkey(), e);
        }
        return null;
    }

    public HBaseModel put(HBaseModel model) {
        HBaseModel ret = null;
        try {
            HTableInterface htable = getTable(model);
            String rowkey = model.rowkey();
            Get get = new Get(Bytes.toBytes(rowkey));
            if (htable.exists(get)) {
                ret = get(model);
            }
            Put put = new Put(Bytes.toBytes(rowkey));
//            put.add();
            htable.put(put);
        } catch (IOException e) {
            logger.error("hbase put a row error, rowkey = {}", model.rowkey(), e);
        }
        return ret;
    }

    public boolean delete(HBaseModel model) {
        try {
            HTableInterface htable = getTable(model);
            Delete delete = new Delete(Bytes.toBytes(model.rowkey()));
            htable.delete(delete);
            return true;
        } catch (IOException e) {
            logger.error("hbase delete a row error, rowkey = {}", model.rowkey(), e);
        }
        return false;
    }

    public List<HBaseModel> scan(Scan scan, String tableName, Class<HBaseModel> clz) {
        List<HBaseModel> ret = new ArrayList<>();
        try {
            HTableInterface htable = getTable(tableName);
            ResultScanner results = htable.getScanner(scan);
            if (results != null) {
                for (Result result : results) {
                    HBaseModel model = clz.newInstance();
                    model.parse(result);
                    ret.add(model);
                }
            }
        } catch (IOException e) {
            logger.error("hbase scan result list error, table name = {}", tableName, e);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return ret;
    }

    // TODO: 01/11/2017 批量操作待补充
    public void batchGet() {

    }

    public void batchPut() {

    }

    public void batchDelete() {

    }

    public HTableInterface getTable(String tableName) {
        int retry = 0;
        // 获取表是核心, 这里添加重试机制
        while (retry <= 3) { // 重试三次, 这里可配置
            try {
                return conn.getTable(Bytes.toBytes(tableName));
            } catch (IOException e) {
                retry++;
                logger.error("get hbase table failed, table name = {}, retry times = {}", tableName, retry, e);
            }
        }
        disConn(); // 获取table失败就关闭好了
        return null;
    }

    public HTableInterface getTable(HBaseModel model) {
        return getTable(model.getTable());
    }

    /**
     * 关闭连接
     */
    private void disConn() {
        if (conn != null && !conn.isClosed()) {
            conn = null;
        }
    }

    /**
     * @return 获取hbase连接
     */
    private HConnection establishConn() {
        try {
            conn = HConnectionManager.createConnection(conf);
        } catch (IOException e) {
            logger.error("get conn error", e);
        }
        return conn;
    }

    /**
     * 默认为 classpath:hbase-default.xml/hbase-site.xml
     */
    private void reload() {
        Configuration newConf = HBaseConfiguration.create();
        if (conf != null) {
            HBaseConfiguration.merge(conf, newConf);
        } else {
            conf = newConf;
        }
    }
}
