package rabbitmq.test;

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
            // 注1：queueDeclare第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
            // 第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、
            // 第四个参数为当所有消费者客户端连接断开时是否自动删除队列、
            // 第五个参数为队列的其他参数
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //发送的消息
            String message = "hello world!";
            //往队列中发出一条消息
            // 注2：basicPublish第一个参数为交换机名称、
            // 第二个参数为队列映射的路由key、
            // 第三个参数为消息的其他属性、
            // 第四个参数为发送信息的主体
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            //关闭频道和连接
            channel.close();
            connection.close();
    }

}
