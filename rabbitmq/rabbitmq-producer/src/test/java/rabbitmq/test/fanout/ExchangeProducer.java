package rabbitmq.test.fanout;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: (发布/订阅.)
 * @author hong
 * @date 2017/12/22
 * @version v1.1
 */
public class ExchangeProducer {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args)throws IOException, TimeoutException {
        /**
         * 创建连接连接到RabbitMQ
         */
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建一个频道
        Channel channel = connection.createChannel();

        //fanout表示分发，所有的消费者得到同样的队列信息
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //分发信息
        for (int i=0;i<5;i++){
            String message="Hello World"+i;
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("EmitLog Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }
}
