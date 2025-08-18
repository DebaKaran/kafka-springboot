package com.dk.kafka.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "kafka")
@Configuration
@Data
public class KafkaTopicsProperties {

    private List<Topic> topics;

    //Get the topic name based on alias
    public String getTopicByAlias(String alias) {
        return topics.stream()
                .filter(topic -> topic.alias.equalsIgnoreCase(alias))
                .map(Topic :: getName)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No topic found for alias " + alias));
    }

    @Data
    public static class Topic {
        private String alias; // add alias to identify purpose (e.g. customer)
        private String name;
        private int partitions;
        private short replicationFactor;
    }
}
