package com.ecommerce.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    //Producer factory
    @Bean
    public ProducerFactory<String,String> producerFactory(){
        Map<String, Object> configMaps=new HashMap<>();
        configMaps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        configMaps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configMaps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configMaps);
    }
    //Kafka Template
    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
    //New topic
    @Bean
    public NewTopic orderTopic(){
        return  new NewTopic("order-topic",3,(short)1);
    }
}
