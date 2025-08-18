package com.dk.kafkaconsumer.services;

import com.dk.kafkaconsumer.configs.KafkaConsumerConfig;
import com.dk.kafkaconsumer.configs.KafkaConsumersProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListner {

    Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @Autowired
    private KafkaConsumersProperties kafkaConsumersProperties;

    @KafkaListener(
            topics = "#{@consumerDefinitions['test'].topic}",
            groupId = "#{@consumerDefinitions['test'].groupId}",
            containerFactory = "testFactory"
    )
    public void consume(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        logger.info("Consumer received: {} from partition {}", message, partition);
    }

    @KafkaListener(
            topics = "#{@consumerDefinitions['customer'].topic}",
            groupId = "#{@consumerDefinitions['customer'].groupId}",
            containerFactory = "customerFactory"
    )
    public void consumeCustomer(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        logger.info("[customer-consumer] Received: {} from partition {}", message, partition);
    }

}
