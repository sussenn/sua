package com.itc.sua.data;

import com.itc.sua.analysis.DataAnalysisMain;
import com.itc.sua.analysis.stream.RocketProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName MqTest
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/29
 */
@SpringBootTest(classes = DataAnalysisMain.class)
public class MqTest {

    @Autowired
    private RocketProducer producer;

    @Test
    public void test002() {
        producer.sendDelayMsg("延迟消息009");
    }

    @Test
    public void test001() {
        producer.send("zsc0007");

    }
}
