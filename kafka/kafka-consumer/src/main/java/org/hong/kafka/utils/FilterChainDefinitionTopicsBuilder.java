package org.hong.kafka.utils;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FilterChainDefinitionTopicsBuilder {

    private static final Logger logger = Logger.getLogger(FilterChainDefinitionTopicsBuilder.class);

    /**
     * 工厂bean 方法,用于获取Topic 数组数据,然后提供给 ContainerProperties 订阅..
     *
     * @return
     */
    public String[] builderFilterChainDefinitionTopics() {
        List<String> topics = new ArrayList<>();
        topics.add("test");
        logger.info("订阅的topic: " + topics);
        return topics.toArray(new String[topics.size()]);
    }
}
