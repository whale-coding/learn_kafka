package com.star.producer;

import com.star.domain.User;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

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

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate2;

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate3;


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

    public void sendEvent6() {
        // Integer partition, Long timestamp, K key, V data
        CompletableFuture<SendResult<String, String>> completableFuture
                = kafkaTemplate.sendDefault(0, System.currentTimeMillis(), "k3", "hello kafka");

        // 怎么拿到结果，通过CompletableFuture这个类拿结果，这个类里面有很多方法
        try {
            //1、阻塞等待的方式拿结果
            SendResult<String, String> sendResult = completableFuture.get();
            if (sendResult.getRecordMetadata() != null) {  // 源数据，只要有ack就不会为空
                // kafka服务器确认已经接收到了消息
                System.out.println("消息发送成功: " + sendResult.getRecordMetadata().toString());
            }
            System.out.println("producerRecord: " + sendResult.getProducerRecord());  // 消息本身的对象

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEvent7() {
        // Integer partition, Long timestamp, K key, V data
        CompletableFuture<SendResult<String, String>> completableFuture
                = kafkaTemplate.sendDefault(0, System.currentTimeMillis(), "k3", "hello kafka");

        // 怎么拿到结果，通过CompletableFuture这个类拿结果，这个类里面有很多方法
        try {
            // 2、非阻塞的方式拿结果
            completableFuture.thenAccept((sendResult) -> {
                if (sendResult.getRecordMetadata() != null) {
                    // kafka服务器确认已经接收到了消息
                    System.out.println("消息发送成功: " + sendResult.getRecordMetadata().toString());
                }
                System.out.println("producerRecord: " + sendResult.getProducerRecord());
            }).exceptionally((t) -> {
                t.printStackTrace();
                // 做失败的处理
                return null;  // 根据需求返回内容
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 发送对象消息
    public void sendEvent8() {
        User user = User.builder().id(1208).phone("13709090909").birthDay(new Date()).build();
        // 分区是null，让kafka自己去决定把消息发到哪个分区
        kafkaTemplate2.sendDefault(null, System.currentTimeMillis(), "k3", user);
    }

    // 往自定义Topic里面发消息
    public void sendEvent9() {
        User user = User.builder().id(1208).phone("13709090909").birthDay(new Date()).build();
        // 分区是null，让kafka自己去决定把消息发到哪个分区
        kafkaTemplate2.send("heTopic", null, System.currentTimeMillis(), "k9", user);
    }



}
