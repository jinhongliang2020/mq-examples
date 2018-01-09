package org.hong.rabbitmq.consumer.fanout;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.UnsupportedEncodingException;

public class FanoutListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            String body=new String(message.getBody(),"UTF-8");
            System.out.println("FanoutListener1:"+body);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
