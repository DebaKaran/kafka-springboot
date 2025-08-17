package com.dk.kafka.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "kafka")
@Configuration
public class KafkaTopicsProperties {

    private List<Topic> topics;

    public List<Topic> getTopics() {
        return topics;
    }
    
    @PostConstruct
    public void logProps() {
        System.out.println("Loaded topics: " + topics);
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public static class Topic {
        private String name;
        private int partitions;
        private short replicationFactor;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPartitions() {
            return partitions;
        }

        public void setPartitions(int partitions) {
            this.partitions = partitions;
        }

        public short getReplicationFactor() {
            return replicationFactor;
        }

        public void setReplicationFactor(short replicationFactor) {
            this.replicationFactor = replicationFactor;
        }
    }
}
