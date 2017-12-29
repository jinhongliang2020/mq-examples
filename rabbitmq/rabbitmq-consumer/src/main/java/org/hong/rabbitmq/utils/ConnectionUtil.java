package org.hong.rabbitmq.utils;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author hong
 * @version v1.1
 * @description: (维护RabbitMQ连接工具类.)
 * @date 2017/12/28
 */
public class ConnectionUtil {

    /**
     * RabbitMQ 服务地址.
     */
    private static final String CONNECTION_HOST="39.108.212.203";

    /**
     * RabbitMQ AMQP 协议端口.
     */
    private static final int CONNECTION_PORT=5672;

    /**
     * 用户名.
     */
    private static final String CONNECTION_USERNAME="admin";

    /**
     * 密码.
     */
    private static final String CONNECTION_PASSWORD="admin123";

    /**
     * 获取RabbitMQ 连接.
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ所在主机ip或者主机名
        factory.setHost(CONNECTION_HOST);
        //端口
        factory.setPort(CONNECTION_PORT);
        //用户名
        factory.setUsername(CONNECTION_USERNAME);
        //密码
        factory.setPassword(CONNECTION_PASSWORD);
        //virtualHost
        factory.setVirtualHost("/");
        // 通过工厂获取连接.
        Connection connection = factory.newConnection();
        return connection;
    }


}
