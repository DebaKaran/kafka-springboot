package com.dk.kafkaconsumer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListner {

    Logger logger = LoggerFactory.getLogger(KafkaMessageListner.class);

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consume(String message) {
        logger.info("Consumer consumed the message: "+message);
    }
}
