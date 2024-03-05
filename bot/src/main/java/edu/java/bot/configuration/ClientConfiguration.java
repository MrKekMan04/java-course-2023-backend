package edu.java.bot.configuration;

import edu.java.bot.service.client.ScrapperClient;
import edu.java.bot.service.client.ScrapperClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {
    private final ApplicationConfig config;

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClientBuilder()
            .setBaseUrl(config.apiLink().scrapper())
            .build();
    }
}
