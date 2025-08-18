package com.dk.kafkaconsumer.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaConsumersProperties {

    private List<ConsumerDefinition> consumers;

    @Data
    public static class ConsumerDefinition {
        private String alias;
        private String topic;
        private String groupId;
    }

    public ConsumerDefinition getConsumerDefinationBasedOnAlias(final String alias) {
        return consumers.stream()
                .filter(def -> def.alias.equalsIgnoreCase(alias))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No consumer found for alias: " + alias));
    }
}
