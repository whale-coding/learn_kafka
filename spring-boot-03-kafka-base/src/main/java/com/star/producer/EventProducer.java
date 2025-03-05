package com.star.producer;

import com.star.domain.User;
import com.star.util.JSONUtils;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Classname: EventProducer
 * @Date: 2025/3/3 19:36
 * @Author: 聂建强
 * @Description: 事件生产者
 */
@Component
public class EventProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent() {
        for (int i = 0; i < 125; i++) {
            User user = User.builder().id(i).phone("13709090909"+i).birthDay(new Date()).build();
            String userJSON = JSONUtils.toJSON(user);

            kafkaTemplate.send("batchTopic", "k"+i, userJSON);
        }
    }

}
