package com.star.producer;

import jakarta.annotation.Resource;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

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


    public void sendEvent() {
        kafkaTemplate.send("hello-topic", "hello kafka");  // 发送消息
    }

    public void sendEvent2() {
        // 通过构建器模式创建Message对象
        Message<String> message = MessageBuilder.withPayload("hello kafka")  // 消息的data
                .setHeader(KafkaHeaders.TOPIC, "test-topic-02") // 在header中放置topic的名字
                .build();
        kafkaTemplate.send(message);
    }

    public void sendEvent3() {
        // Headers里面是放一些信息(信息是key-value键值对)，到时候消费者接收到该消息后，可以拿到这个Headers里面放的信息
        Headers headers = new RecordHeaders();
        headers.add("phone", "13709090909".getBytes(StandardCharsets.UTF_8));
        headers.add("orderId", "OD158932723742".getBytes(StandardCharsets.UTF_8));

        //  String topic, Integer partition, Long timestamp, K key, V value, Iterable<Header> headers
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "test-topic-02",
                0,
                System.currentTimeMillis(),
                "k1",
                "hello kafka",
                headers
        );
        kafkaTemplate.send(record);
    }

    public void sendEvent4() {
        // String topic, Integer partition, Long timestamp, K key, V data
        kafkaTemplate.send("test-topic-02", 0, System.currentTimeMillis(), "k2", "hello kafka");
    }

    public void sendEvent5() {
        // Integer partition, Long timestamp, K key, V data
        // 没有指定topic，需要在配置文件中配置default-topic 否则报错
        kafkaTemplate.sendDefault(0, System.currentTimeMillis(), "k3", "hello kafka");
    }
}
