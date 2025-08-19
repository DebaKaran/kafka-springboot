package com.dk.kafka.services;

import com.dk.kafka.configs.KafkaConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListner {

    Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @KafkaListener(
            topics = "#{@consumerDefinitions['test'].topic}",
            groupId = "#{@consumerDefinitions['test'].groupId}",
            containerFactory = "testFactory"
    )
    public void consume(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        logger.info("Consumer received: {} from partition {}", message, partition);
    }

}
