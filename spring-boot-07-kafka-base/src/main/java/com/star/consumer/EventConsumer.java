package com.star.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @KafkaListener(topics = {"osTopic"}, groupId = "osGroup")
    public void onEvent(ConsumerRecord<String, String> record, Acknowledgment ack) {
        System.out.println(Thread.currentThread().getId() + " -->消息消费，records = " + record);

        ack.acknowledge();
    }
}
