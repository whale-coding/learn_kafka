package com.star.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;


    /**
     * 生产者相关配置
     *
     * @return
     */
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);  // kafka服务器地址
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);  // key的序列化方式
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);  // value的序列化方式
        // props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);  // 指定分区策略为轮询的方式
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomerPartitioner.class.getName());

        // 添加自定义的生产者拦截器
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, CustomerProducerInterceptor.class.getName());

        return props;
    }

    /**
     * 生产者创建工厂
     *
     * @return
     */
    public ProducerFactory<String, ?> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * kafkaTemplate 覆盖默认配置类中的kafkaTemplate
     * @return
     */
    @Bean
    public KafkaTemplate<String, ?> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }



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
