package org.hong.kafka.listen;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.hong.kafka.event.KafkaTopicEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.listener.MessageListener;

/**
 * @description: ( kafka 消息订阅.)
 * @author hong
 * @date 2017/12/6
 * @version v1.1
 */
public class KafkaConsumerListener implements MessageListener<String, String> {

    private static final Logger logger = Logger.getLogger(KafkaConsumerListener.class);

    /**
     * Spring中提供了ApplicationEventPublisher接口作为事件发布者,
     * 并且ApplicationContext实现了这个接口,担当起了事件发布者这一角色。
     */
    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * @Description: 收到订阅消息的处理方法.
     * @param
     */
    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        logger.info("收到topic: " + record.topic() + " 消息--> " + record.value());

        // 发布自定义事件,使用@EventListener(condition = "#event.topic=='xxx'") 去监听对应事件.
        // 1.使用事件发布与监听的方式，将kafka 消息订阅和处理逻辑分离.
        // 2.EventListener监听事件→执行异步操作.
        publisher.publishEvent(new KafkaTopicEvent(this,record));
    }
}
