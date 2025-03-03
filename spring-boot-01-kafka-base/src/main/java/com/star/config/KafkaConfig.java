package com.star.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KafkaConfig {

    // 自定义Topic，制定分区和副本的个数（单例模型，不会丢数据，只要存在，就不会重复创建）
    @Bean
    public NewTopic newTopic() {
        // String name, int numPartitions, short replicationFactor  自定义topic ，包括分区个数和副本个数
        return new NewTopic("heTopic", 5,  (short)1);
    }

    /*
    // 对topic进行更新（因为上面已经创建了这个Topic，再运行这个只会更新参数，不会重新创建，且不会丢失Topic里面的消息)
    // 分区只能增大，不能更新缩小  7 ->9 ok    9->7 no
    @Bean
    public NewTopic updateNewTopic() {
        return new NewTopic("heTopic", 9,  (short)1);
    }
     */

}
