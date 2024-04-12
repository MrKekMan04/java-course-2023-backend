package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    ApiLink apiLink,
    Retry retry,
    KafkaConfigInfo kafkaConfigInfo,
    Micrometer micrometer
) {
    public record ApiLink(String scrapper) {
    }

    public record Retry(
        Integer maxAttempts,
        Set<Integer> retryStatusCodes,
        RetryType type,
        DelayConfig delayConfig
    ) {
        public enum RetryType {
            CONSTANT, LINEAR, EXPONENTIAL
        }

        public record DelayConfig(
            ConstantConfig constant,
            LinearConfig linear,
            ExponentialConfig exponential
        ) {
            public record ConstantConfig(
                Long backOffPeriodMillis
            ) {
            }

            public record LinearConfig(
                Long initialIntervalMillis,
                Long maxIntervalMillis
            ) {
            }

            public record ExponentialConfig(
                Long initialIntervalMillis,
                Double multiplier,
                Long maxIntervalMillis
            ) {
            }
        }
    }

    public record KafkaConfigInfo(
        List<String> bootstrapServers,
        UpdatesTopic updatesTopic
    ) {
        public record UpdatesTopic(
            String name,
            Integer partitions,
            Integer replicas
        ) {
        }
    }

    public record Micrometer(
        ProcessedMessagesCounter processedMessagesCounter
    ) {
        public record ProcessedMessagesCounter(
            String name,
            String description
        ) {
        }
    }
}
