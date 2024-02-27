package edu.java.configuration;

import edu.java.service.client.GitHubClient;
import edu.java.service.client.GitHubClientBuilder;
import edu.java.service.client.StackOverflowClient;
import edu.java.service.client.StackOverflowClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {
    private final ApplicationConfig config;

    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClientBuilder()
            .setBaseUrl(config.apiLink().gitHub())
            .build();
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClientBuilder()
            .setBaseUrl(config.apiLink().stackOverflow())
            .build();
    }
}
