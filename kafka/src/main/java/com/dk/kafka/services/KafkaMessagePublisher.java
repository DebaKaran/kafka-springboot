package com.dk.kafka.services;

import com.dk.kafka.configs.KafkaTopicsProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    private KafkaTemplate<String, Object> template;
    private final String topic;

    public KafkaMessagePublisher(KafkaTemplate<String, Object> template,
                                 KafkaTopicsProperties properties) {
        this.template = template;
        this.topic = properties.getTopicByAlias("test");
    }

    public void sendMessageToTopic(final String message) {

        CompletableFuture<SendResult<String, Object>> future = template.send(topic, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Send Message: [ "+ message+" ] with offset= [ "+result.getRecordMetadata().offset()+" ]");
            } else {
                System.out.println("Unable to send message = [ "+message+" ] due to : "+ex.getMessage());
            }
        });

    }
}
