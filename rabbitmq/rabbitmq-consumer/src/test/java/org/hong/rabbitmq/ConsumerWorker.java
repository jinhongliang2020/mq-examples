package org.hong.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ConsumerWorker {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ所在主机ip或者主机名
        factory.setHost("39.108.212.203");
        //端口
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin123");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println("Worker1 [*] Waiting for messages. To exit press CTRL+C");
        // 每次从队列中获取数量
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println("Worker1 [x] Received '" + message + "'");
                try {
                    doWork(message);
                } finally {
                    System.out.println("Worker1 [x] Done");
                    // 消息处理完成确认
                    // 为了确保消息或者任务不会丢失，RabbitMQ支持消息确认–ACK。
                    // ACK机制是消费者端从RabbitMQ收到消息并处理完成后，反馈给RabbitMQ，RabbitMQ收到反馈后才将此消息从队列中删除。
                    // 如果一个消费者在处理消息时挂掉（网络不稳定、服务器异常、网站故障等原因导致频道、连接关闭或者TCP连接丢失等），
                    // 那么他就不会有ACK反馈，RabbitMQ会认为这个消息没有正常消费，会将此消息重新放入队列中。
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // 消息消费完成确认
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }

    private static void doWork(String task) {
        try {
            Thread.sleep(10000); // 暂停1秒钟
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
