package me.codebabe.dao.hbase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.codebabe.dao.hbase.model.HBaseModel;
import me.codebabe.dao.hbase.utils.HBaseHelper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * author: code.babe
 * date: 2017-11-01 14:45
 */
public class HBaseTool {

    private static final Logger logger = Logger.getLogger(HBaseTool.class);

    private HBaseTool() {
        reload();
        establishConn();
    }

    private static HBaseTool tool;
    private Configuration conf;
    private Connection conn;

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
            Table htable = getTable(model);
            Get get = new Get(Bytes.toBytes(model.rowkey()));
            Result result = htable.get(get);
            return HBaseHelper.parse(result, model);
        } catch (IOException e) {
            logger.error(String.format("hbase get a row error, rowkey = %s", model.rowkey()), e);
        }
        return null;
    }

    public HBaseModel put(HBaseModel model) {
        HBaseModel ret = null;
        try {
            Table htable = getTable(model);
            String rowkey = model.rowkey();
            Get get = new Get(Bytes.toBytes(rowkey));
            if (htable.exists(get)) {
//                ret = get(model);
            }
            Put put = new Put(Bytes.toBytes(rowkey));
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(model));
            Class<?> clz = model.getClass();
            for (String qualifier : jsonObject.keySet()) {
                try {
                    Field field = clz.getDeclaredField(qualifier);
                    Class<?> fieldType = field.getType();
                    if (Long.class.equals(fieldType)) {
                        Long value = jsonObject.getLong(qualifier);
                        put.addColumn(Bytes.toBytes(model.columnFamily()), Bytes.toBytes(qualifier), Bytes.toBytes(value));
                    } else if (Integer.class.equals(fieldType)) {
                        Integer value = jsonObject.getInteger(qualifier);
                        put.addColumn(Bytes.toBytes(model.columnFamily()), Bytes.toBytes(qualifier), Bytes.toBytes(value));
                    } else if (Short.class.equals(fieldType)) {
                        Short value = jsonObject.getShort(qualifier);
                        put.addColumn(Bytes.toBytes(model.columnFamily()), Bytes.toBytes(qualifier), Bytes.toBytes(value));
                    } else if (Double.class.equals(fieldType)) {
                        Double value = jsonObject.getDouble(qualifier);
                        put.addColumn(Bytes.toBytes(model.columnFamily()), Bytes.toBytes(qualifier), Bytes.toBytes(value));
                    } else { // 默认都是string类型了
                        String value = jsonObject.getString(qualifier);
                        put.addColumn(Bytes.toBytes(model.columnFamily()), Bytes.toBytes(qualifier), Bytes.toBytes(value));
                    }
                }  catch (NoSuchFieldException ignore) {
//                    logger.info(String.format("no such field, filed = %s", qualifier), e);
                }
            }
            htable.put(put);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(String.format("hbase put a row error, rowkey = %s", model.rowkey()), e);
        }
        return ret;
    }

    public boolean delete(HBaseModel model) {
        try {
            Table htable = getTable(model);
            Delete delete = new Delete(Bytes.toBytes(model.rowkey()));
            htable.delete(delete);
            return true;
        } catch (IOException e) {
            logger.error(String.format("hbase delete a row error, rowkey = %s", model.rowkey()), e);
        }
        return false;
    }

    public List<? extends HBaseModel> scan(Scan scan, String tableName, Class<? extends HBaseModel> clz) {
        List<HBaseModel> ret = new ArrayList<>();
        try {
            Table htable = getTable(tableName);
            ResultScanner results = htable.getScanner(scan);
            if (results != null) {
                for (Result result : results) {
                    HBaseModel model = clz.newInstance();
                    HBaseHelper.parse(result, model);
                    ret.add(model);
                }
            }
        } catch (IOException e) {
            logger.error(String.format("hbase scan result list error, table name = %s", tableName), e);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 批量操作, 包括put, get和delete
     *
     * @return 每个操作的返回结果
     * null: 通信失败
     * Delete/Put: EmptyResult
     * Get: Result
     */
    public Object[] batch(List<Row> rows, String tableName) {
        Table htable = getTable(tableName);
        try {
            Object[] results = new Object[rows.size()];
            htable.batch(rows, results);
            return results;
        } catch (IOException | InterruptedException e) {
            logger.error(String.format("hbase batch operate error, table name = %s", tableName), e);
        }
        return null;
    }

    public Table getTable(String tableName) {
        int retry = 0;
        // 获取表是核心, 这里添加重试机制
        while (retry <= 3) { // 重试三次, 这里可配置
            try {
                return conn.getTable(TableName.valueOf(tableName));
            } catch (IOException e) {
                retry++;
                e.printStackTrace();
                logger.error(String.format("get hbase table failed, table name = %s, retry times = %d", tableName, retry), e);
            }
        }
        disConn(); // 获取table失败就关闭好了
        return null;
    }

    public Table getTable(HBaseModel model) {
        return getTable(model.table());
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
    private Connection establishConn() {
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            logger.error("get conn error", e);
        }
        return conn;
    }

    /**
     * 默认为 classpath:hbase-default.xml/dev-hbase-site.xml
     */
    private void reload() {
        Configuration newConf = HBaseConfiguration.create();
        StringBuilder hbasePath = new StringBuilder("config/hbase/");
        String hbaseName = "hbase-site.xml";
        hbasePath.append(hbaseName);
        URL url = Thread.currentThread().getContextClassLoader().getResource(hbasePath.toString());
        if (url != null) {
            newConf.addResource(url);
        }
        if (conf != null) {
            HBaseConfiguration.merge(conf, newConf);
        } else {
            conf = newConf;
        }
    }
}
