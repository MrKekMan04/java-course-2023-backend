package edu.java.configuration;

import edu.java.service.client.GitHubClient;
import edu.java.service.client.StackOverflowClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {
    private final ApplicationConfig config;
    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient(config.client().gitHub());
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient(config.client().stackOverflow());
    }
}
