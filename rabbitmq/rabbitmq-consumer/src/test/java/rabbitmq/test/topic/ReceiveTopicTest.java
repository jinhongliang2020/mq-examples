package rabbitmq.test.topic;

import com.rabbitmq.client.*;
import org.hong.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveTopicTest {

    private static final String EXCHANGE_NAME = "topic_test";

    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 获取RabbitMQ 连接.
         */
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Channel channel = connection.createChannel();

        //声明一个匹配模式的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();
        //路由关键字
        String[] routingKeys = new String[]{"*.orange.*"};
        //绑定路由
        for (String routingKey : routingKeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
            System.out.println("ReceiveLogsTopic1 exchange:" + EXCHANGE_NAME + ", queue:" + queueName + ", BindRoutingKey:" + routingKey);
        }
        System.out.println("ReceiveLogsTopic1 Waiting for messages");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("ReceiveLogsTopic1 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
