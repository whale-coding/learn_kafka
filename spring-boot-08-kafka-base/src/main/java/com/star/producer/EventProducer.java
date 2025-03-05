package com.star.producer;

import com.star.domain.User;
import com.star.util.JSONUtils;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EventProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent() {
        for (int i = 0; i < 2; i++) {
            User user = User.builder().id(1028+i).phone("1370909090"+i).birthDay(new Date()).build();
            String userJSON = JSONUtils.toJSON(user);
            kafkaTemplate.send("clusterTopic", "k" + i, userJSON);
        }
    }
}
