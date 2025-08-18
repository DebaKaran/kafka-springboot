package com.dk.kafkaconsumer.services;

import com.dk.kafkaconsumer.configs.KafkaConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListner {

    Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${app.kafka.group-id:test-group}")
    public void consume(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        logger.info("Consumer received: {} from partition {}", message, partition);
    }

}
