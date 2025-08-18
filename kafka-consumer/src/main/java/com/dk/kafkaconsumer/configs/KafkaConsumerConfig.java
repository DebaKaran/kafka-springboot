package com.dk.kafkaconsumer.configs;

import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Collections;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @Value("${app.kafka.topic}")
    private String topic;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory,
            KafkaAdmin kafkaAdmin) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        int partitions = 1;

        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            partitions = adminClient.describeTopics(Collections.singletonList(topic))
                    .all()
                    .get()
                    .get(topic)
                    .partitions()
                    .size();

            factory.setConcurrency(partitions); // match dynamically
        } catch (Exception e) {
            partitions = 1; //fallback
        }
        logger.info("Kafka listener concurrency set to {} for topic {}", partitions, topic);

        return factory;
    }
}
