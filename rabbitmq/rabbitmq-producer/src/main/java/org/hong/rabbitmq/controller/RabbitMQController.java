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

    private final String FANOUT_EXCHANGE="test.fanout.exchange";

    private final String DIRECT_EXCHANGE="rabbit.exchange.direct";

    private final String DIRECT_ROUTING_KEY="spring.test.queueKey";


    @RequestMapping("/fanout/pushMsg")
    public void pushFanoutMsg(String msg) {
        for (int i = 0; i < 20; i++) {
            System.out.println("fanout:"+msg + "-" + i);
            rabbitTemplateFanout.send(FANOUT_EXCHANGE, "", new Message((msg + "-" + i).getBytes(), new MessageProperties()));
        }
    }

    @RequestMapping("/direct/pushMsg")
    public void pushDirectMsg(String msg){
        for (int i = 0; i < 20; i++) {
            System.out.println("direct:"+msg + "-" + i);
            rabbitTemplateDirect.send(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY, new Message((msg + "-" + i).getBytes(), new MessageProperties()));
        }
    }
}
