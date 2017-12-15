package org.hong.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class QuenuConsumer implements MessageListener {
    Logger logger = LoggerFactory.getLogger(QuenuConsumer.class);

    @Override
    public void onMessage(Message message) {
        logger.info("The third cosumer received message : " + message);
    }

}
