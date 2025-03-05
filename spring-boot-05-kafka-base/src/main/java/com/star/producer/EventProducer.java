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
        User user = User.builder().id(1028).phone("13709090901").birthDay(new Date()).build();
        String userJSON = JSONUtils.toJSON(user);
        kafkaTemplate.send("topicA", "k", userJSON);
    }
}
