package com.dk.kafka.configs;

import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> testFactory(
            ConsumerFactory<String, String> consumerFactory,
            KafkaAdmin kafkaAdmin,
            KafkaConsumersProperties props,
            ExponentialBackOff testBackOff) {

        return createFactory("test", consumerFactory, kafkaAdmin, props, testBackOff);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> customerFactory(
            ConsumerFactory<String, String> consumerFactory,
            KafkaAdmin kafkaAdmin,
            KafkaConsumersProperties props,
            ExponentialBackOff customBackOff) {

        return createFactory("customer", consumerFactory, kafkaAdmin, props, customBackOff);
    }

    @Bean
    public Map<String, KafkaConsumersProperties.ConsumerDefinition> consumerDefinitions(KafkaConsumersProperties props) {
        return props.getConsumers().stream()
                .collect(Collectors.toMap(KafkaConsumersProperties.ConsumerDefinition::getAlias, c -> c));
    }

    private ConcurrentKafkaListenerContainerFactory<String, String> createFactory(
            String alias,
            ConsumerFactory<String, String> consumerFactory,
            KafkaAdmin kafkaAdmin,
            KafkaConsumersProperties props,
            BackOff backOff) {

        KafkaConsumersProperties.ConsumerDefinition def = props.getConsumerDefinationBasedOnAlias(alias);

        int partitions = 1;
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            partitions = adminClient.describeTopics(Collections.singletonList(def.getTopic()))
                    .all()
                    .get()
                    .get(def.getTopic())
                    .partitions()
                    .size();
        } catch (Exception e) {
            logger.warn("Could not fetch partition count for {}. Defaulting to 1", def.getTopic());
        }

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(partitions);

        // Add error handler with the injected backoff
        factory.setCommonErrorHandler(new DefaultErrorHandler(backOff));
        logger.info("Created listener factory for alias={} topic={} groupId={} partitions={}",
                def.getAlias(), def.getTopic(), def.getGroupId(), partitions);

        return factory;
    }
}
