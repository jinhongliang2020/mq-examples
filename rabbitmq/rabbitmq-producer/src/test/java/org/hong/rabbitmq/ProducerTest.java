package org.hong.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerTest {
    //队列名称
    private final static String QUEUE_NAME = "hello";

    @Test
    public void testPush() throws IOException, TimeoutException {

            /**
             * 创建连接连接到RabbitMQ
             */
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ所在主机ip或者主机名
            factory.setHost("39.108.212.203");
            //端口
            factory.setPort(5672);
            factory.setUsername("admin");
            factory.setPassword("admin123");
            //创建一个连接
            Connection connection = factory.newConnection();
            //创建一个频道
            Channel channel = connection.createChannel();
            //指定一个队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //发送的消息
            String message = "hello world!";
            //往队列中发出一条消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            //关闭频道和连接
            channel.close();
            connection.close();
    }

}
