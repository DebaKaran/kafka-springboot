package com.dk.kafka.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaBackoffConfig {

    @Bean
    public JitterExponentialBackOff customBackOff(KafkaRetryProperties properties) {
        return new JitterExponentialBackOff(properties.getInitialInterval(),
                properties.getMultiplier(),
                properties.getMaxInterval());
    }
}
