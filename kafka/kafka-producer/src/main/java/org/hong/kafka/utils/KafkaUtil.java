package org.hong.kafka.utils;

import org.apache.log4j.Logger;
import org.hong.kafka.constant.KafkaMesConstant;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * @Description: (kafka 辅助工具类.)
 * @author hong
 * @date 2017/11/23
 * @version v1.1
 */
public class KafkaUtil {

    private static final Logger logger = Logger.getLogger(KafkaUtil.class);

    /**
     * 检查发送返回结果record
     *
     * @param res
     * @return
     */
    //TODO 消息发送异常情况处理 后续调整.
    public static void checkProRecord(ListenableFuture<SendResult<String, String>> res) {
        if (res != null) {
            try {
                SendResult r = res.get();//检查result结果集
                /*检查recordMetadata的offset数据，不检查producerRecord*/
                Long offsetIndex = r.getRecordMetadata().offset();
                if (offsetIndex != null && offsetIndex >= 0) {
                    logger.debug(KafkaMesConstant.SUCCESS_MES);
                } else {
                    logger.debug(KafkaMesConstant.KAFKA_NO_OFFSET_MES);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(KafkaMesConstant.KAFKA_SEND_ERROR_MES);
            } catch (ExecutionException e) {
                e.printStackTrace();
                logger.error(KafkaMesConstant.KAFKA_SEND_ERROR_MES);
            }
        } else {
            logger.debug(KafkaMesConstant.KAFKA_NO_RESULT_MES);
        }
    }

}
