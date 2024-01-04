package com.itc.sua.analysis.stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
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
    private StreamBridge streamBridge;

    public void send(Object msg) {
        streamBridge.send("producer-out-0",
                MessageBuilder.withPayload(msg).build());
        log.info("[send] >>> send msg: {}", msg);
    }

    public void sendDelayMsg(String msg) {
        // private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
        // 4级表示延迟30s

        Message<String> message = MessageBuilder
                .withPayload(msg)
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, 2)
                .build();
        streamBridge.send("producer-out-0", message);

        //Map<String, Object> headers = new HashMap<>();
        //headers.put(MessageConst.PROPERTY_DELAY_TIME_LEVEL, 2);
        //GenericMessage<Object> message = new GenericMessage<>(msg, headers);
        //streamBridge.send("producer-out-0", message);
        log.info("[sendDelayMsg] >>> send msg: {}", msg);
    }

}
