package org.hong.kafka.controller;


import org.hong.kafka.utils.KafkaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description: (测试kafka 发送消息.)
 * @author hong
 * @date 2017/12/5
 * @version v1.1
 */
@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping("/wirteKafka")
    public void wirteKafka(String msg){
        ListenableFuture<SendResult<String, String>> res =  kafkaTemplate.send("test",msg);
        KafkaUtil.checkProRecord(res);
    }

}
