package com.dk.kafka.configs;

import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.concurrent.ThreadLocalRandom;

public class JitterExponentialBackOff extends ExponentialBackOff {

    private final long initialInterval;
    private final double multiplier;
    private final long maxInterval;
    private long currentInterval;

    public JitterExponentialBackOff(long initialInterval, double multiplier, long maxInterval) {
        this.initialInterval = initialInterval;
        this.multiplier = multiplier;
        this.maxInterval = maxInterval;
        this.currentInterval = initialInterval;
    }

    @Override
    public BackOffExecution start() {
        currentInterval = initialInterval;
        return new BackOffExecution() {
            @Override
            public long nextBackOff() {
                if (currentInterval > maxInterval) {
                    return BackOffExecution.STOP;
                }
                // Apply jitter ±50%
                double jitterFactor = ThreadLocalRandom.current().nextDouble(0.5, 1.5);
                long next = (long) (currentInterval * jitterFactor);
                currentInterval = (long) (currentInterval * multiplier);
                return next;
            }
        };
    }
}
