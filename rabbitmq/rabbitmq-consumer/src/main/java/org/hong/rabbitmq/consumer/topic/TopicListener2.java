package org.hong.rabbitmq.consumer.topic;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.UnsupportedEncodingException;

public class TopicListener2 implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            String body=new String(message.getBody(),"UTF-8");
            System.out.println("TopicListener2:"+body);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
