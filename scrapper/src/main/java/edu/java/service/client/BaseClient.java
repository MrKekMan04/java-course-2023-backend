package edu.java.service.client;

import org.springframework.web.reactive.function.client.WebClient;

public abstract class BaseClient {
    protected final WebClient client;

    public BaseClient(String baseUrl) {
        client = WebClient.create(baseUrl);
    }
}
