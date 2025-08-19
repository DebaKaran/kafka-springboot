package com.dk.kafka.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.kafka.retry")
@Data
public class KafkaRetryProperties {
    private long initialInterval;
    private double multiplier;
    private long maxInterval;
    private int maxAttempts;
}
