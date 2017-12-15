package org.hong.rabbitmq.controller;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping("/pushMsg")
    public void pushMsg(String msg) {
           rabbitTemplate.convertAndSend("queueKey",msg);
    }
}
