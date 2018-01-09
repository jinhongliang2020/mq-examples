package org.hong.rabbitmq.controller;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/fanout/pushMsg")
    public void pushMsg(String msg) {
        for (int i = 0; i < 20; i++) {
            System.out.println(msg + "-" + i);
            rabbitTemplate.send("test.fanout.exchange", "", new Message((msg + "-" + i).getBytes(), new MessageProperties()));
        }
    }
}
