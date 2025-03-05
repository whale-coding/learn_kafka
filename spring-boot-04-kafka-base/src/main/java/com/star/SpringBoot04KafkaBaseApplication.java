package com.star;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.Map;

@SpringBootApplication
public class SpringBoot04KafkaBaseApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringBoot04KafkaBaseApplication.class, args);

        Map<String, ConsumerFactory> beansOfType = context.getBeansOfType(ConsumerFactory.class);
        beansOfType.forEach((k, v) -> {
            System.out.println(k + " -- " + v); //DefaultKafkaConsumerFactory
        });

        System.out.println("----------------------------------------");

        Map<String, KafkaListenerContainerFactory> beansOfType2 = context.getBeansOfType(KafkaListenerContainerFactory.class);
        beansOfType2.forEach((k, v) -> {
            System.out.println(k + " -- " + v); // ConcurrentKafkaListenerContainerFactory
        });
    }

}
