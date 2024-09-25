package com.ecommerce.paymentservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    //ConsumerFactory
    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        Map<String, Object> configMap=new HashMap<>();
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-service");
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_DOC,StringDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(configMap);
    }
    //ConcurrentKafkaListenerContainerFactory

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> containerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory=new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        return containerFactory;
    }

}
