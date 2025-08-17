package com.dk.kafka.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class KafkaTopicConfig {
    private final KafkaTopicsProperties properties;

    public KafkaTopicConfig(KafkaTopicsProperties properties) {
        this.properties = properties;
    }

    @Bean
    public KafkaAdmin.NewTopics topics() {
        List<NewTopic> list =  properties.getTopics().stream()
                .map(topic -> new NewTopic(topic.getName(), topic.getPartitions(),
                        topic.getReplicationFactor()))
                .collect(Collectors.toList());
        return new KafkaAdmin.NewTopics(list.toArray(NewTopic[]::new));
    }

    // Ensures topic creation happens before KafkaTemplate or consumers are used
    @Bean
    public ApplicationRunner runner(KafkaAdmin kafkaAdmin) {
        return args -> {
            kafkaAdmin.initialize();
        };
    }
}
