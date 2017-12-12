package org.hong.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    @Test
    public void task() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ所在主机ip或者主机名
        factory.setHost("39.108.212.203");
        //端口
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
//      分发消息
        for(int i = 0 ; i < 5; i++){
            String message = "Hello World! " + i;
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();

    }
}
