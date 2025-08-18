package com.dk.kafka.services;

import com.dk.kafka.configs.KafkaTopicsProperties;
import com.dk.kafka.dtos.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerProducer {

    private static final Logger logger = LoggerFactory.getLogger(CustomerProducer.class);

    private final String topic;
    private final KafkaTemplate<String, Customer> kafkaTemplate;


    public CustomerProducer(KafkaTemplate<String, Customer> kafkaTemplate, KafkaTopicsProperties properties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = properties.getTopicByAlias("customer");
    }

    public void sendCustomer(Customer customer) {
        logger.info("Producing Customer: {}", customer);
        kafkaTemplate.send(topic, customer);
    }
}
