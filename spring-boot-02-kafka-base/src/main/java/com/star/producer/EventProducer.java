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

    //加入了spring-kafka依赖 + .yml配置信息，springboot自动配置好了kafka，自动装配好了KafkaTemplate这个Bean
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    // 发送普通字符串
    public void sendEvent() {
        kafkaTemplate.send("helloTopic", "hello kafka");  // 发送消息
    }

    // 发送对象
    public void sendEvent2() {
        User user = User.builder().id(1209).phone("13709090909").birthDay(new Date()).build();
        String userJSON = JSONUtils.toJSON(user);
        kafkaTemplate.send("helloTopic", userJSON);
    }

}
