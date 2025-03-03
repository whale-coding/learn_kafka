package com.star.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Classname: EventConsumer
 * @Date: 2025/3/3 20:15
 * @Author: 聂建强
 * @Description: 消费者，以监听的方式
 */
@Component
public class EventConsumer {
    //采用监听的方式接收事件（消息、数据），这个进程会一直在后台监听消息，默认是只能读取最新的消息，之前的消息读取不到
    // 需要指定topics 以及 groupId
    @KafkaListener(topics = { "hello-topic"}, groupId = "hello-group")
    public void onEvent(String event) {
        System.out.println("读取到的事件：" + event);
    }
}
