package org.hong.kafka.handler;


import org.apache.log4j.Logger;
import org.hong.kafka.event.KafkaTopicEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author hong
 * @version v1.1
 * @description: (kafka 消息订阅事件处理类.)
 * @date 2017/12/6
 */
@Component
public class CustomEventHandler {

    private static final Logger logger = Logger.getLogger(CustomEventHandler.class);

    /**
     * @param
     * @Description: 二维码同步处理方法.
     */
    @EventListener(condition = "#event.topic=='test'")
    public void testHandle(KafkaTopicEvent event) {
        String data = event.getRecord().value();
        logger.info("处理topic: test消息：" + data.concat("-----test"));
    }
}
