package com.star.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @KafkaListener(topics = {"topicA"}, groupId = "aGroup")
    @SendTo(value = "topicB")  // 消费转发给B这个topic
    public String onEventA(ConsumerRecord<String, String> record) {
        System.out.println("消息A消费，records = " + record);
        return record.value() + "--forward message";
    }

    @KafkaListener(topics = {"topicB"}, groupId = "bGroup")
    public void onEventB(ConsumerRecord<String, String> record) {
        System.out.println("消息B消费，records = " + record);
    }
}
