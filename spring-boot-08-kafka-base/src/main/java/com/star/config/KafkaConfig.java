package com.star.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic newTopic() {
        // 设置副本个数不能为0，也不能大于节点个数，否则将不能创建Topic；
        return new NewTopic("clusterTopic2", 2,  (short)3);
    }
}
