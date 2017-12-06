package org.hong.kafka.event;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationEvent;

/**
 * @Description: (自定义一个事件对象，用于监听器来topic 变化.)
 * @author hong
 * @date 2017/12/6
 * @version v1.1
 */
public class KafkaTopicEvent extends ApplicationEvent {

    /**
     * kafka 订阅的消息对象.
     */
    private ConsumerRecord<String, String> record;

    /**
     * topic.
     */
    private String topic;

    public KafkaTopicEvent(Object source) {
        super(source);
    }

    public KafkaTopicEvent(Object source, ConsumerRecord<String, String> record) {
        super(source);
        this.record = record;
        this.topic = record.topic();
    }

    public ConsumerRecord<String, String> getRecord() {
        return record;
    }

    public void setRecord(ConsumerRecord<String, String> record) {
        this.record = record;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
