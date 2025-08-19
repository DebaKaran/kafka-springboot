package com.dk.kafka.services;

import com.dk.kafka.dtos.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class CustomerConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CustomerConsumer.class);

    @RetryableTopic(autoCreateTopics = "true", attempts = "4", backoff = @Backoff(delay = 1000L))
    @KafkaListener(
            topics = "#{@consumerDefinitions['customer'].topic}",
            groupId = "#{@consumerDefinitions['customer'].groupId}",
            containerFactory = "customerFactory"
    )
    public void consumeCustomer(Customer customer,
                                @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        try {
            // 1. Validate customer
            if (customer.getEmail() == null) {
                throw new IllegalArgumentException("Invalid customer data: email is null");
            }

            // 2. Save/update in database (simulate here)
            saveCustomer(customer);

            // 3. Trigger downstream services if needed
            triggerDownstream(customer);

            // 4. Acknowledge success
            //ack.acknowledge();

            logger.info("Consumed customer with id: {} from partition {}", customer.getId(), partition);

        } catch (Exception e) {
            logger.error("Failed to process customer {}. Will retry or send to DLT.", customer.getId(), e);
            throw new RuntimeException("Processing failed for customer ID: " + customer.getId(), e);
        }
    }

    @DltHandler
    public void listenDLT(Customer customer,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {
        logger.error("Moved to DLT - Customer ID: {}, from topic {}, offset {}",
                customer.getId(), topic, offset);
    }

    private void saveCustomer(Customer customer) {
        // Simulate DB save
        logger.debug("Saving customer: {}", customer);
    }

    private void triggerDownstream(Customer customer) {
        // Simulate downstream call
        logger.debug("Triggering downstream for customer: {}", customer);
    }
}
