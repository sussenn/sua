package com.itc.sua.device.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


/**
 * @ClassName RocketConsumer
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/29
 */
@Component
@Slf4j
public class RocketConsumer {

    @Bean
    public Consumer<Message<Object>> consumer() {
        return msg -> log.info("[consumer] >>> consume msg: {}", msg.getPayload());
    }

    //@Bean
    //public Consumer<Message<Object>> delayConsumer() {
    //    return msg -> log.info("[delayConsumer] >>> consume msg: {}", msg.getPayload());
    //}
}
