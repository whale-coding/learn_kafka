package com.star.consumer;

import com.star.domain.User;
import com.star.util.JSONUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Classname: EventConsumer
 * @Date: 2025/3/3 20:15
 * @Author: 聂建强
 * @Description: 消费者
 * 1、@Payload注解用于标记该参数是消息体内容，可以不加
 * 2、@Header注解用于标记该参数是消息头内容
 *
 */
@Component
public class EventConsumer {
    //采用监听的方式接收事件（消息、数据），这个进程会一直在后台监听消息，默认是只能读取最新的消息，之前的消息读取不到
    // 需要指定topics 以及 groupId
    // @KafkaListener(topics = { "helloTopic"}, groupId = "helloGroup")
    public void onEvent(@Payload String event,
                        @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                        // @Header(value = KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(value = KafkaHeaders.RECEIVED_PARTITION) String partition
                        ) {
        System.out.println("读取到的事件1：" + event + ", topic : " + topic + ", partition : " + partition);
    }

    // 通过ConsumerRecord<String, String> record接收消息（data）更加的方便
    // @KafkaListener(topics = { "helloTopic"}, groupId = "helloGroup")
    public void onEvent1(ConsumerRecord<String, String> record) {
        System.out.println("读取到的事件1：" + record.toString());
    }

    // @KafkaListener(topics = { "helloTopic"}, groupId = "helloGroup")
    public void onEvent2(String userJSON,
                         @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(value = KafkaHeaders.RECEIVED_PARTITION) String partition,
                         @Payload ConsumerRecord<String, String> record) {
        User user = JSONUtils.toBean(userJSON, User.class);  // json转对象
        System.out.println("读取到的事件2：" + user + ", topic : " + topic + ", partition : " + partition);
        System.out.println("读取到的事件2：" + record.toString());
    }

    // 使用占位符读取自定义配置的topic名称等
    // @KafkaListener(topics = { "${kafka.topic.name}"}, groupId = "${kafka.consumer.group}")
    public void onEvent3(String userJSON,
                         @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(value = KafkaHeaders.RECEIVED_PARTITION) String partition,
                         @Payload ConsumerRecord<String, String> record) {
        User user = JSONUtils.toBean(userJSON, User.class);
        System.out.println("读取到的事件3：" + user + ", topic : " + topic + ", partition : " + partition);
        System.out.println("读取到的事件3：" + record.toString());
    }

    // Acknowledgment ack参数，开启手动消息确认模式
    @KafkaListener(topics = { "${kafka.topic.name}"}, groupId = "${kafka.consumer.group}")
    public void onEvent4(String userJSON,
                         @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(value = KafkaHeaders.RECEIVED_PARTITION) String partition,
                         @Payload ConsumerRecord<String, String> record,
                         Acknowledgment ack) {

        try {
            //收到消息后，处理业务
            User user = JSONUtils.toBean(userJSON, User.class);
            System.out.println("读取到的事件4：" + user + ", topic : " + topic + ", partition : " + partition);
            System.out.println("读取到的事件4：" + record.toString());

            // int a = 10 / 0;

            // 业务处理完成，给kafka服务器确认（手动确认，默认是kafka自动确认）
            ack.acknowledge(); // 手动确认消息，就是告诉kafka服务器，该消息我已经收到了，默认情况下kafka是自动确认
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
