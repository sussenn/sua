package com.itc.sua.analysis.stream;

import com.itc.sua.common.constants.analysis.AnalysisConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @ClassName RocketProducer
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/29
 */
@Component
@Slf4j
public class RocketProducer {

    @Autowired
    private RocketMQTemplate mqTemplate;
    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer messageTimeOut;

    private final String TOPIC = AnalysisConstants.MqTopic.TOPIC;

    public void send(Object msg) {
        mqTemplate.send(TOPIC + ":tag1", MessageBuilder.withPayload(msg).build());
    }

    public void sendSync(Object msg) {
        mqTemplate.asyncSend(TOPIC + ":tag2", MessageBuilder.withPayload(msg).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("[sendSync] success >>>");
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("[sendSync] fail >>>");
            }
        });
    }

    public void sendDelay(Object msg, int lev) {
        mqTemplate.syncSend(TOPIC + ":tag3",
                MessageBuilder.withPayload(msg).build(),
                messageTimeOut,
                lev);
    }

}
