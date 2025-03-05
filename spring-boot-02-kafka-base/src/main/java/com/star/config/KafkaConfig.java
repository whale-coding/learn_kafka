package com.star.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic newTopic() {
        // 设置分区为5个，副本为1个
        return new NewTopic("helloTopic", 5,  (short)1);
    }
}
