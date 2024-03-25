package edu.java.configuration;

import edu.java.service.client.BotClient;
import edu.java.service.client.BotClientBuilder;
import edu.java.service.client.GitHubClient;
import edu.java.service.client.GitHubClientBuilder;
import edu.java.service.client.StackOverflowClient;
import edu.java.service.client.StackOverflowClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {
    private final ApplicationConfig config;

    @Bean
    public GitHubClient gitHubClient(RetryTemplate retryTemplate) {
        return new GitHubClientBuilder()
            .setBaseUrl(config.apiLink().gitHub())
            .setRetryTemplate(retryTemplate)
            .build();
    }

    @Bean
    public StackOverflowClient stackOverflowClient(RetryTemplate retryTemplate) {
        return new StackOverflowClientBuilder()
            .setBaseUrl(config.apiLink().stackOverflow())
            .setRetryTemplate(retryTemplate)
            .build();
    }

    @Bean
    public BotClient botClient(RetryTemplate retryTemplate) {
        return new BotClientBuilder()
            .setBaseUrl(config.apiLink().bot())
            .setRetryTemplate(retryTemplate)
            .build();
    }
}
