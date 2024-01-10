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
    private RocketProducer rocketProducer;

    @Test
    public void test004() {
        Boolean suc = rocketProducer.sendSync("同步消息333");
        System.err.println(suc);
    }

    @Test
    public void test003() {
        rocketProducer.sendDelay("延迟消息222", 2);
    }

    @Test
    public void test002() {
        rocketProducer.sendAsync("异步消息111");
    }

    @Test
    public void test001() {
        rocketProducer.send("简单消息0001");
    }
}
