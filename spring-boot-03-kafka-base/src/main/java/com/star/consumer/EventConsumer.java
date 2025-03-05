package com.star.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname: EventConsumer
 * @Date: 2025/3/3 20:15
 * @Author: 聂建强
 * @Description: 消费者
 */
@Component
public class EventConsumer {

    @KafkaListener(topics = {"batchTopic"}, groupId = "batchGroup")
    public void onEvent(List<ConsumerRecord<String, String>> records) {
        System.out.println("批量消费，records.size() = " + records.size() + "，records = " + records);
    }
}
