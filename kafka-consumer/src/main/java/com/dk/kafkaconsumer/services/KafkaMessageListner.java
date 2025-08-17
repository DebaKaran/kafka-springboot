package com.dk.kafkaconsumer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListner {

    Logger logger = LoggerFactory.getLogger(KafkaMessageListner.class);

    // Consumer 1
    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consumeGroup1(String message) {
        logger.info("Consumer1 received: {}", message);
    }

    // Consumer 2
    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consumeGroup2(String message) {
        logger.info("Consumer2 received: {}", message);
    }

    // Consumer 3
    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consumeGroup3(String message) {
        logger.info("Consumer3 received: {}", message);
    }
}
