package com.example.inventory.kafkaConfiguration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {
     
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
 
    @Value(value = "${message.topic.name}")
    private String topicName;
    
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        return new KafkaAdmin(configs);
    }
     
    @Bean
    public NewTopic topic1() {
         return new NewTopic("AddTopic", 1, (short) 1);
    }
    
    @Bean
    public KafkaAdmin kafkaAdmin2() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        return new KafkaAdmin(configs);
    }
     
    @Bean
    public NewTopic topic2() {
         return new NewTopic("CheckOutTopic", 1, (short) 1);
    }
}