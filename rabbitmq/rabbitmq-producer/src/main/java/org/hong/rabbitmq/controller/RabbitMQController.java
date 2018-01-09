package org.hong.rabbitmq.controller;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

    @Autowired
    @Qualifier(value = "rabbitTemplateFanout")
    private RabbitTemplate rabbitTemplateFanout;

    @Autowired
    @Qualifier(value = "rabbitTemplateDirect")
    private RabbitTemplate rabbitTemplateDirect;

    @Autowired
    @Qualifier(value = "rabbitTemplateTopic")
    private RabbitTemplate rabbitTemplateTopic;

    private final String FANOUT_EXCHANGE = "test.fanout.exchange";

    private final String DIRECT_EXCHANGE = "test.direct.exchange";

    private final String TOPIC_EXCHANGE = "test.topic.exchange";

    private final String DIRECT_ROUTING_KEY = "test.direct.queueKey";

    private final String TOPIC_ROUTING_KEY_1 = "123.topic.456";
    private final String TOPIC_ROUTING_KEY_2 = "topic.abc.1";

    @RequestMapping("/fanout/pushMsg")
    public void pushFanoutMsg(String msg) {
        for (int i = 0; i < 20; i++) {
            System.out.println("fanout:" + msg + "-" + i);
            rabbitTemplateFanout.send(FANOUT_EXCHANGE, "", new Message((msg + "-" + i).getBytes(), new MessageProperties()));
        }
    }

    @RequestMapping("/direct/pushMsg")
    public void pushDirectMsg(String msg) {
        for (int i = 0; i < 20; i++) {
            System.out.println("direct:" + msg + "-" + i);
            rabbitTemplateDirect.send(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY, new Message((msg + "-" + i).getBytes(), new MessageProperties()));
        }
    }

    @RequestMapping("/topic/pushMsg")
    public void pushTopicMsg(String msg) {
        for (int i = 0; i < 10; i++) {
            System.out.println("topic:" + msg + "-" + i);
            rabbitTemplateTopic.send(TOPIC_EXCHANGE, TOPIC_ROUTING_KEY_1, new Message((msg + "-" + i).getBytes(), new MessageProperties()));
        }
        System.out.println("===================" + TOPIC_ROUTING_KEY_1 + "========================");

        for (int i = 0; i < 10; i++) {
            System.out.println("topic:" + msg + "-" + i);
            rabbitTemplateTopic.send(TOPIC_EXCHANGE, TOPIC_ROUTING_KEY_2, new Message((msg + "-" + i).getBytes(), new MessageProperties()));
        }

        System.out.println("===================" + TOPIC_ROUTING_KEY_2 + "========================");
    }
}
