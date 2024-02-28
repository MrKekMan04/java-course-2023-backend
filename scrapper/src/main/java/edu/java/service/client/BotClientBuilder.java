package edu.java.service.client;

public class BotClientBuilder {
    private String baseUrl;

    public BotClientBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public BotClient build() {
        return new BotClient(baseUrl);
    }
}
