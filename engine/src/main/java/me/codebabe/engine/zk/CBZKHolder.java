package me.codebabe.engine.zk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(CBZKHolder.class);

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
                        Integer sessionTimeout = Integer.parseInt(props.getProperty("zk.session.timeout", "15000"));
                        // 连接创建时间
                        Integer connTimeout = Integer.parseInt(props.getProperty("zk.connection.timeout", "10000"));
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

                        holder.addConnectionStateListener(new ConnectionStateListener() {
                            @Override
                            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                                logger.warn("Zk connection state changed, new state:" + newState.name());
                            }
                        });
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

    public boolean isExist(String path) throws Exception {
        return client.checkExists().forPath(path) != null;
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

    public void addConnectionStateListener(ConnectionStateListener listener) {
        if (listener != null) {
            client.getConnectionStateListenable().addListener(listener);
        }
    }

}
