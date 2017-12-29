package org.hong.rabbitmq.consumer.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class FanoutConsumerListener2 implements MessageListener {
    Logger logger = LoggerFactory.getLogger(FanoutConsumerListener2.class);

    @Override
    public void onMessage(Message message) {
        logger.info("The third cosumer received message : " + message);
    }

}
