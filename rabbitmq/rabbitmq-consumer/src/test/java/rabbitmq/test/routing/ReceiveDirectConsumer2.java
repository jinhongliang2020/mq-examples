package rabbitmq.test.routing;

import com.rabbitmq.client.*;
import org.hong.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @description: (采用路由的方式对不同的消息进行过滤)
 * @author hong
 * @date 2017/12/22
 * @version v1.1
 */
public class ReceiveDirectConsumer2 {

    // 交换器名称
    private static final String EXCHANGE_NAME = "direct_logs";
    // 路由关键字
    private static final String[] routingKeys = new String[]{"error"};

    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 创建连接连接到RabbitMQ
         */
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Channel channel = connection.createChannel();
        //声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //获取匿名队列名称
        String queueName=channel.queueDeclare().getQueue();

        //根据路由关键字进行绑定
        for (String routingKey:routingKeys){
            channel.queueBind(queueName,EXCHANGE_NAME,routingKey);
            System.out.println("ReceiveDirect2 exchange:"+EXCHANGE_NAME+"," +
                    " queue:"+queueName+", BindRoutingKey:" + routingKey);
        }
        System.out.println("ReceiveLogsDirect1  Waiting for messages");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("ReceiveLogsDirect1 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
