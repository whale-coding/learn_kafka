package com.star.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @KafkaListener(topics = {"myTopic"}, groupId = "myGroup", concurrency = "3", containerFactory = "ourKafkaListenerContainerFactory")
    public void onEvent(ConsumerRecord<String, String> record) {
        System.out.println(Thread.currentThread().getId() + " -->消息消费，records = " + record);
    }
}
