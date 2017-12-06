package org.hong.kafka.listener;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;
import org.springframework.kafka.support.ProducerListener;


/**
 * @description: (kafkaProducer监听器，在producer配置文件中开启.)
 * @author hong
 * @date 2017/12/5
 * @version v1.1
 */
public class KafkaProducerListener implements ProducerListener {
    protected final Logger logger = Logger.getLogger(KafkaProducerListener.class);

    /**
     * 发送消息成功后调用
     */
    @Override
    public void onSuccess(String topic, Integer partition, Object key,
                          Object value, RecordMetadata recordMetadata) {
        logger.debug("==========kafka发送数据成功（日志开始）==========");
        logger.debug("topic:[" + topic+"]---partition:["+partition+"]"+"---key:[" + key+"]"+"---value:[" + value+"]");
        logger.debug("----------RecordMetadata:" + recordMetadata);
        logger.debug("~~~~~~~~~~kafka发送数据成功（日志结束）~~~~~~~~~~");
    }

    /**
     * 发送消息错误后调用
     */
    @Override
    public void onError(String topic, Integer partition, Object key,
                        Object value, Exception exception) {
        logger.error("==========kafka发送数据错误（日志开始）==========");
        logger.error("topic:[" + topic+"]---partition:["+partition+"]"+"---key:[" + key+"]"+"---value:[" + value+"]");
        logger.error("----------Exception:" + exception);
        logger.error("~~~~~~~~~~kafka发送数据错误（日志结束）~~~~~~~~~~");
    }

    /**
     * 方法返回值代表是否启动kafkaProducer监听器
     */
    @Override
    public boolean isInterestedInSuccess() {
        logger.debug("============= KafkaProducer监听器启动 ===============");
        return true;
    }

}
