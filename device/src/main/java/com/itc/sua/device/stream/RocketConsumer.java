package com.itc.sua.device.stream;

import com.itc.sua.common.constants.analysis.AnalysisConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * @ClassName RocketConsumer
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/29
 */
@Component
@Slf4j
public class RocketConsumer {

    private final static String TOPIC = AnalysisConstants.MqTopic.TOPIC;

    @Service
    @RocketMQMessageListener(topic = TOPIC, selectorExpression = "tag1", consumerGroup = "conSum-one")
    public static class ConsumerSimp implements RocketMQListener<Object> {
        @Override
        public void onMessage(Object msg) {
            log.info("[ConsumerSimp] consumer msg = {}", msg);
        }
    }

    @Service
    @RocketMQMessageListener(topic = TOPIC, selectorExpression = "tag2", consumerGroup = "conSum-two")
    public static class ConsumerSync implements RocketMQListener<Object> {
        @Override
        public void onMessage(Object msg) {
            log.info("[ConsumerSync] consumer msg = {}", msg);
        }
    }

    @Service
    @RocketMQMessageListener(topic = TOPIC, selectorExpression = "tag3", consumerGroup = "conSum-three")
    public static class ConsumerDelay implements RocketMQListener<Object> {
        @Override
        public void onMessage(Object msg) {
            log.info("[ConsumerDelay] consumer msg = {}", msg);
        }
    }
}
