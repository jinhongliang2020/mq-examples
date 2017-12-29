package rabbitmq.test;

import com.rabbitmq.client.*;
import org.hong.rabbitmq.utils.ConnectionUtil;

import java.io.IOException;

public class ConsumerWorker2 {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv)  throws Exception {
        /**
         * 获取RabbitMQ 连接.
         */
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println("Worker2 [*] Waiting for messages. To exit press CTRL+C");
        // 每次从队列中获取数量,保证一次只获取一个
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println("Worker2 [x] Received '" + message + "'");
                try {
                    doWork(message);
                } finally {
                    System.out.println("Worker2 [x] Done");
                    // 消息处理完成确认
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // 消息消费完成确认
        // 注：autoAck是否自动回复，如果为true的话，每次生产者只要发送信息就会从内存中删除，
        // 那么如果消费者程序异常退出，那么就无法获取数据.
        // 所以我们设置成收到手动回复，每当消费者收到并处理信息然后在通知生成者，最后从队列中删除这条信息。
        // 如果消费者异常退出，如果还有其他消费者，那么就会把队列中的消息发送给其他消费者，如果没有，等消费者启动时候再次发送。
        boolean autoAck=false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
    }

    private static void doWork(String task) {
        try {
            Thread.sleep(1000); // 暂停1秒钟
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
