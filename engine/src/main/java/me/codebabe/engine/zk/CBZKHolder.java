package me.codebabe.engine.zk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * author: code.babe
 * date: 2017-09-07 17:26
 *
 * code.babe的zk所有者, 利用curator提供的api对zk操作进行封装
 */
public class CBZKHolder {

    private static CBZKHolder holder = null;

    private static SerializerFeature[] features = {SerializerFeature.WriteNullStringAsEmpty};

    private CuratorFramework client;

    private CBZKHolder() {
    }

    /**
     * 单例提供zk的基本操作
     *
     * @return
     */
    public static CBZKHolder getInstance() {
        if (holder == null) {
            synchronized (CBZKHolder.class) {
                if (holder == null) {
                    holder = new CBZKHolder();
                    Properties props = new Properties();
                    ClassLoader loader = CBZKHolder.class.getClassLoader();
                    try {
                        props.load(new InputStreamReader(loader.getResourceAsStream("config/zookeeper.properties"), "UTF-8"));
                        String connectionString = props.getProperty("zk.address", "127.0.0.1:2881");
                        String namespace = props.getProperty("zk.namespace", "");
                        // 会话超时时间
                        Integer sessionTimeout = Integer.parseInt(props.getProperty("zk.session.timeout", "10000"));
                        // 连接创建时间
                        Integer connTimeout = Integer.parseInt(props.getProperty("zk.connection.timeout", "15000"));
                        Integer retryTimes = Integer.parseInt(props.getProperty("zk.retry.times", "10"));
                        Integer retrySleepTime = Integer.parseInt(props.getProperty("zk.retry.sleep.time", "800"));

                        holder.client = CuratorFrameworkFactory.builder()
                                .connectionTimeoutMs(connTimeout)
                                .connectString(connectionString)
                                .sessionTimeoutMs(sessionTimeout)
                                .namespace(namespace)
                                .retryPolicy(new RetryNTimes(retryTimes, retrySleepTime))
                                .build();

                        holder.client.blockUntilConnected(sessionTimeout, TimeUnit.MILLISECONDS);

                        holder.client.start();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return holder;
    }


    public void createPersistent(String path, Object data) throws Exception {
        create(path, CreateMode.PERSISTENT, data);
    }

    public void createEphemeral(String path, Object data) throws Exception {
        create(path, CreateMode.EPHEMERAL, data);
    }

    /**
     * 创建节点
     *
     * @param path
     * @param mode
     * @param data
     * @throws Exception
     */
    public void create(String path, CreateMode mode, Object data) throws Exception {
        client.create().creatingParentsIfNeeded().withMode(mode).forPath(path, JSON.toJSONBytes(data, features));
    }

    /**
     * 更新节点的值, 这个得在创建节点之后操作
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public void setData(String path, Object data) throws Exception {
        client.setData().forPath(path, JSON.toJSONBytes(data, features));
    }

    /**
     * 获取节点数据
     *
     * @param path
     * @param tClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T getData(String path, Class<T> tClass) throws Exception {
        byte[] data = client.getData().forPath(path);
        return JSON.parseObject(data, tClass);
    }

    /**
     * 删除节点
     *
     * @param path
     * @throws Exception
     */
    public void deleteNode(String path) throws Exception {
        client.delete().forPath(path);
    }

}
