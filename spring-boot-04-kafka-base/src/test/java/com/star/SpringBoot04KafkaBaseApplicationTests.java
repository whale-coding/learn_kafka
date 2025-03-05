package com.star;

import com.star.producer.EventProducer;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBoot04KafkaBaseApplicationTests {

    @Resource
    private EventProducer eventProducer;

    @Test
    void testContext() {
        eventProducer.sendEvent();
    }

}
